(set-env!
 :source-paths
 #{"src"}
 
 :dependencies
 '[[org.clojure/clojurescript "0.0-2814"]
   [pandeiro/boot-http        "0.6.3-SNAPSHOT"]
   [adzerk/boot-cljs          "0.0-2814-3"]
   [adzerk/boot-cljs-repl     "0.1.10-SNAPSHOT"]
   [adzerk/boot-reload        "0.2.6"]
   [thi.ng/geom               "0.0-725"]])

(require '[pandeiro.boot-http    :refer :all]
         '[adzerk.boot-cljs      :refer :all]
         '[adzerk.boot-cljs-repl :refer :all]
         '[adzerk.boot-reload    :refer :all])
