(ns autogram.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [reagent.core :as r]
            [cljs.core.async :refer [<!]]
            [clojure.set :refer [map-invert]]
            [autogram.interval :as intv]
            [autogram.associate :refer [associate]]))

(enable-console-print!)

;; CONSTANTS ;; Change the flow of the component
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def REL_ID "autogram-position")
(def interval 5000)
(def delay-out 1000)
(def delay-slide 1000)
(def delay-in 2200)
(def delay-offset 20)
(def anim-speed 1000)
(def anim-transition (str "all " anim-speed "ms ease"))

(defonce app-state (r/atom {:words (list)
                            :index 0
                            :matches-with-next {}    ; {:0->1 {0 1 2 2} ...}
                            :letter-positions {} ; {0 [nil nil nil nil ...]}
                            :interval (intv/start interval)}))

;; MUTATIONS ;; Update the state
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- transition-to-index [index]
  (swap! app-state assoc-in [:index] index))

(defn- associate-pair [from to index]
  (swap! app-state update-in [:letter-positions index] #(vec (take (count from) (concat % (repeat nil)))))
  (swap! app-state assoc-in [:matches-with-next index] (associate from to)))

(defn- associate-position [word letter position]
  (swap! app-state assoc-in [:letter-positions word letter] (str position "%")))

;; DISCOVERY ;; Calculate positions & relations
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- calc-position
  "Calculate the position of a letter in a given word relative to REL_ID"
  [letter-index word-index node]
  ;; Firefox's implementation of offsetLeft cannot take into account
  ;; auto margins, so we have to calculate the bounding rects manually
  (let [rel           (.getBoundingClientRect (.getElementById js/document REL_ID))
        node          (.getBoundingClientRect node)
        position      (-> node
                          (aget "left")
                          (- (aget rel "left"))
                          (/ (aget rel "width"))
                          (* 100))]
    (associate-position word-index letter-index position)))

(defn- position-letter-
  "Render a letter inline, triggering calc-position"
  [wi li letter]
  (r/create-class
    {:component-did-mount #(calc-position li wi (r/dom-node %))
     :reagent-render
       (fn [wi li letter]
         [:span letter])}))

(defn- position-letter
  "Wraps position-letter- for composibility"
  [wi li letter]
  ^{:key (str wi li letter)}
  [position-letter- wi li letter])

(defn- word-pairs-indexed
  "Given a list, return a list of (word next-word index) elements"
  [words]
  (partition 3
    (interleave
      words                ; Word
      (rest (cycle words)) ; Next word
      (range))))           ; Index

(defn- position-word
  "Given a word, render a new container for which the letters will be inlined in"
  [[from to index]]
  (associate-pair from to index)
  ^{:key (str from "/" index)}
  [:span
   (map-indexed (partial position-letter index) (seq from))])

(defn- position-words []
  [:span {:style {:display :inline-block
                  :max-height 1
                  :text-align :center
                  :visibility :hidden
                  :overflow-y :hidden}}
   (interleave
    (map position-word (-> @app-state :words word-pairs-indexed))
    (repeatedly (fn [] [:br {:key (rand-int 10000)}])))])

;; ANIMATE ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- apply-anim [node delay prop value]
  (js/setTimeout #(aset (.-style node) prop value) delay))

(defn- animated-letter-
  "Create a component positioned in the initial state, then upon mounting,
  will trigger an animation (style update)."
  [delay prop to _ _ _]
  (r/create-class
    {:component-did-mount #(apply-anim (r/dom-node %) delay prop to)
     :reagent-render
       (fn [_ _ _ letter left opacity]
         [:div {:style {:position    :absolute
                        :top         0
                        :opacity     opacity
                        :left        left
                        :transition  anim-transition
                        :-webkit-transition anim-transition}}
          letter])}))

(defn- animated-letter
  "Wraps animated-letter- for composability"
  [anim-delay anim-prop anim-to opacity left letter]
  (when left
    ^{:key (str letter left anim-to)}
    [animated-letter- anim-delay anim-prop anim-to letter left opacity]))

(defn- fade-in [li & args]
  (apply animated-letter (+ (* delay-offset li) delay-in) "opacity" 1 0 args))
(defn- fade-out [li & args]
  (apply animated-letter (+ (* delay-offset li) delay-out) "opacity" 0 1 args))
(defn- slide-to [left li & args]
  (apply animated-letter (+ (* delay-offset li) delay-slide) "left" left 1 args))

(defn- animate-letter
  "Render a letter with the appropriate animation, in the correct spot.  If given
  two sets of positions, it is a 'before' word.  If given one set, it is an 'after'"
  ([matches pos1 li letter]
   (when-not (some #(= li %) (vals matches))
     (fade-in (/ li 2) (nth pos1 li) letter li)))
  ([matches pos1 pos2 li letter]
   (if-let [next-li (get matches li)]
     (slide-to (nth pos2 next-li) li (nth pos1 li) letter)
     (fade-out li (nth pos1 li) letter))))

(defn- animate-words
  "Pull data out of the state to animate the words"
  []
  (let [words       (@app-state :words)
        wi          (@app-state :index)
        next-wi     (mod (inc wi) (count words))
        word        (nth words wi)
        next-word   (nth words next-wi)
        pos         (get-in @app-state [:letter-positions wi])
        next-pos    (get-in @app-state [:letter-positions next-wi])
        matches     (get-in @app-state [:matches-with-next wi])]
   [:span
    (map-indexed (partial animate-letter matches pos next-pos) (seq word))
    (map-indexed (partial animate-letter matches next-pos) (seq next-word))]))


;; INITIALIZE ;; Component & interval
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn autogram
  "Accepts an arbitrary amount of words and autograms between them"
  [& words]
  (swap! app-state assoc-in [:words] words)
  [:span {:style {:position :relative
                  :text-align :center}
          :id REL_ID}
   (position-words)
   (animate-words)])

(def transition-chan
  (let [chan (intv/listen)]
    (go-loop [n (<! chan)]
      (if n
        (do (transition-to-index (mod n (count (@app-state :words))))
            (recur (<! chan)))))
    chan))

(intv/silence transition-chan)
(intv/restart interval)
(intv/listen transition-chan)
