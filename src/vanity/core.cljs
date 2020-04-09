(ns vanity.core
  (:require [reagent.core :as r]
            [autogram.core :refer [autogram]]))

(enable-console-print!)

(defn rolling-title []
  [autogram
   "Keyboard Clicker"
   "Product Engineer"
   "Front End Developer"
   "Amateur Traveler"
   "JavaScript Ninja"
   "Clojure Enthusiast"
   "Aspiring Linguist"])

(defn render []
  (when-let [e (.getElementById js/document "autogram")]
    (r/render-component [rolling-title] e)))

(render)
