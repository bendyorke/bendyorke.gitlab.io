(ns pages.resume
  [:require [pages.templates :as t]])

(def boot-render "resume.html")

(defn render []
  (t/default
    [:section.resume
     [:h1 "Ben Yorke"]
     [:div [:a {:href "tel:9252862729"} "925.286.2729"]
           [:span " | "]
           [:a {:href "mailto:bendyorke@gmail.com"} "bendyorke@gmail.com"]
           [:span " | "]
           [:a {:href "http://www.bendyorke.com"} "bendyorke.com"]]

     ;; About
     [:h3 "Software Engineer"]
     [:hr]
     [:p "I bleed JavaScript.  I use it everyday to build intuitive, highly performant
         client side applications.  I'm passionate about creating quality user experiences,
         and writing well documented, modular code.  I have experience using the major JavaScript
         frameworks, including over 2 years professional experience writing and maintaining large
         React applications."]
     [:p "While my domain of expertise is front-end engineering, I can work anywhere
				 along the stack as needed.  I have experience with Clojure, Ruby, Python, Swift,
			   Obj-C, and C++ - although I'm happy to work with any modern language."]

     ;; Professional
     [:h3 "Professional Experience"]
     [:hr]
     [:div.block
       [:h4 "Under Armour"]
       [:sub "Software Engineer, 2015 - present"]
       [:p "Created internal tools with React & Redux to replace daily tasks,
           reducing turnaround time from 10 days to 10 minutes.  Improving our build
           pipeline allowed us to do this while dramatically increasing performance
           and reliability."]]

     [:div.block
       [:h4 "Capital Factory"]
       [:sub "Software Engineer, 2014 - 2015"]
       [:p "Worked on internal tools for the co-working space and incubator
           using Ruby on Rails and React. I also had the pleasure to split
           time with Pingboard, a fantastic modern employee directory, and work
           primarily on their API."]]

     [:div.block
       [:h4 "Blue Otter"]
       [:sub "Software Engineer, 2013 - 2014"]
       [:p "Worked remotely as a consultant building applications using Ruby on Rails,
           Node, Meteor, Ember, and Backbone. Worked with a lot
           of great startups and corporations alike!"]]

     ;; Projects
     [:h3 "Personal Projects"]
     [:hr]

     [:div.block
       [:h4 [:a {:href "https://www.github.com/bendyorke/react-simple-autocomplete"}
                "React Simple Autocomplete"]]
       [:sub "React, Webpack, Ava"]
       [:p "React Autocomplete Component that works out of the box, but provides a simple
           API for infinite customizability."]]

     [:div.block
       [:h4 [:a {:href "https://www.github.com/bendyorke/boot-postcss"}
                "PostCSS Boot Task"]]
       [:sub "Clojure, Nashorn, PostCSS, CSS Modules"]
       [:p "Boot Task for Postcss that works out of the box and runs on Java 8's Nashorn engine.
           Supports CSS modules in Clojure(Script)."]]

     [:div.block
       [:h4 [:a {:href "https://www.github.com/bendyorke/job-creator"}
                "Job Creator"]]
       [:sub "React, Redux, React Router, Webpack, CSS Modules"]
       [:p "Timed assignment to build out web app from mocks, treated as a batteries-included
             front-end application."]]

     [:h5
      [:sub "If you're interested in seeing more of my work, visit "
            [:a {:href "http://www.bendyorke.com/projects.html"} "bendyorke.com/projects.html"]]]
     ;; Personal
     [:h3 "Personal Interests"]
     [:hr]

     [:div.block
       [:h4 "What's on my bookshelf?"]
       [:p [:em "Rationality: From A.I. to Zombies"] ", "
           [:em "Structure and Interpretation of Computer Programs"] ", "
           [:em "How Language Works"] ", and "
           [:em "De Schippers van de Kameleon."]]]

     [:div.block
       [:h4 "What language am I learning?"]
       [:p "I'm always studying some random foreign language - at the time of writing this
           I'm learning Dutch. Hoera!"]]

     [:div.block
       [:h4 "What is Ender up to?"]
       [:p "I have a husky, named Ender, who is pretty much the coolest dog ever.  Currently, I would assume he is running laps around the house."]]]))

