(set-env!
  :source-paths #{"src"}
  :asset-paths #{"assets"}
  :dependencies '[;; Clojure
                  [org.clojure/clojure "1.7.0"]
                  [org.clojure/clojurescript "1.7.228"]
                  [org.clojure/core.async "0.2.374"]
                  ;; Server
                  [hiccup "1.0.5"]
                  ;; Client
                  [reagent "0.6.0-alpha"]
                  ;; Boot commands
                  [pandeiro/boot-http "0.7.3" :scope "test"]
                  [adzerk/boot-reload "0.4.5" :scope "test"]
                  [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                  ;; boot-cljs-repl & deps
                  [adzerk/boot-cljs-repl "0.3.0" :scope "test"]
                  [com.cemerick/piggieback "0.2.1" :scope "test"]
                  [weasel "0.7.0" :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[pandeiro.boot-http :refer [serve]]
         '[boot.core :as boot]
         '[clojure.java.io :as io]
         '[clojure.string :as s])

(defn by-ns [namespaces fileset]
  (let [all-namespaces (boot/fileset-namespaces fileset)
        is-specified   (fn [n] (some #(.startsWith (str n) (str % ".")) namespaces))]
    (filter is-specified all-namespaces)))

(deftask render
  [n namespaces NAMESPACES #{str} "Namespaces containing pages to render"]
  (let [dir (tmp-dir!)]
    (boot/with-pre-wrap [fileset]
      (println "Rendering static files...")
      (doseq [n (by-ns namespaces fileset)]
        (require n :reload-all)
        (let [filename (ns-resolve n 'boot-render)
              renderfn (ns-resolve n 'render)]
          (when (and filename renderfn)
            (spit (io/file dir @filename) (@renderfn)))))
      (-> fileset (boot/add-resource dir) boot/commit!))))

(def target-dir "public")

(task-options!
  render {:namespaces #{"pages"}}
  target {:dir #{target-dir}}
  serve  {:port 5000
          :dir target-dir})

(deftask c []
  (repl :client true
        :eval '(start-repl)))

(deftask dev []
  (comp
    (serve)
    (watch)
    (render)
    (reload)
    (cljs-repl)
    (cljs :optimizations :none)
    (target)))

(deftask build []
  (comp
    (cljs :optimizations :advanced)
    (render)
    (target)))
