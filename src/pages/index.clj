(ns pages.index
  [:require [pages.templates :as t]
            [hiccup.page :as h]
            [clojure.string :as s]])

(def boot-render "index.html")

(defn hyphenate [string]
  (s/replace (s/lower-case string) " " "-"))

(defn link
  ([name] (link name (str "/" (hyphenate name) ".html")))
  ([name href]
   [:a.menu-link {:href href} name]))

(defn render []
  (t/default
    [:section
     [:h1 "Ben Yorke"]
     [:div#autogram "Keyboard Clicker"]
     (link "Projects")
     (link "Resume")
     (link "Linked In" "https://linkedin.com/in/bendyorke")
     (link "GitHub" "https://github.com/bendyorke")
     (link "Twitter" "https://twitter.com/bendyorke")
     (link "Instagram" "https://instagram.com/bendyorke")]
    (h/include-js (str "main.js?" @t/bust))))
