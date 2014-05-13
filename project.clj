(defproject xkcdpwgen "0.1.0-SNAPSHOT"
   :dependencies
   [  [org.clojure/algo.generic "0.1.2"]
      [org.clojure/clojure      "1.5.1"]
      [org.clojure/tools.cli    "0.3.1"]  ]
   :main ^:skip-aot xkcdpwgen.core
   :target-path "target/%s"
   :profiles {:uberjar {:aot :all}})
