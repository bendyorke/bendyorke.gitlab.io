(ns pages.projects
  [:require [pages.templates :as t]])

(def boot-render "projects.html")

(defn render []
  (t/default
    [:section
     [:a {:href "/"} "Home"]
     [:h1 "Projects"]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/bradfield"}
              "Bradfield Algorithms Course"]]
     [:sub "Algorithms, JavaScript, ClojureScript, Planck"]
     [:p "Algorithms written in JavaScript and ClojureScript, including lazy transducable graph traversal, JavaScript CSP implementation, HTTP Parser, and more."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/react-simple-autocomplete"}
              "React Simple Autocomplete"]]
     [:sub "React, Webpack, Ava"]
     [:p "React Autocomplete Component that works out of the box, but provides a simple
				 API for infinite customizability."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/boot-postcss"}
              "PostCSS Boot Task"]]
     [:sub "Clojure, Boot, Nashorn, PostCSS, CSS Modules"]
     [:p "Boot Task for Postcss that works out of the box and runs on Java 8's Nashorn engine.
				 Supports CSS modules in Clojure(Script)."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/autogram"}
              "Autogram"]]
     [:sub "ClojureScript, Reagent, Leinengen"]
     [:p "Reagent component that provides a pleasing transition between words.  Used on "
         [:a {:href "/"} "bendyorke.com"] "."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/job-creator"}
              "Job Creator"]]
     [:sub "React, Redux, React Router, Webpack, CSS Modules"]
     [:p "Timed assignment to build out web app from mocks, treated as a batteries-include
					 front-end application."]
     [:hr.large]

     [:h4 [:a {:href "https://github.com/bendyorke/shrugkeyboard"}
              "Shrug Keyboard"]]
     [:sub "Swift, iOS Keyboard Extension"]
     [:p "Keyboard extension written in iOS to provide the ultimate suite of ascii characters."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/facebook-calendar"}
              "Facebook Calendar Challenge"]]
     [:sub "React, ES6, babel"]
     [:p "4 hour timed coding assignment, traditional facebook application question."]
     [:hr.large]

     [:h4 [:a {:href "https://www.github.com/bendyorke/dinosaurus-rex"}
              "Dinosaurus Rex"]]
     [:sub "Om.next, ClojureScript, Figwheel"]
     [:p "Summon a fire breathing t-rex to burn web pages to the ground.
         It was my first foray into ClojureScript."]
     [:hr.large]]))


