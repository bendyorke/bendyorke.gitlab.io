(ns pages.templates
  [:require [hiccup.page :as h]])

(defonce bust (atom (quot (System/currentTimeMillis) 1000)))

(defn default [& forms]
  (h/html5
    [:head
      [:title "bendy"]
      (h/include-css "https://code.cdn.mozilla.net/fonts/fira.css")
      (h/include-css (str "main.css?" @bust))
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]]
    [:body forms]))

