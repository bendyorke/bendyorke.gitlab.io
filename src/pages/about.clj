(ns pages.about
  [:require [pages.templates :as t]])

(def boot-render "about.html")

(defn render []
  (t/default [:div "About"]))
