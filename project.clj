(defproject xkcdpwgen "0.1.0-SNAPSHOT"
   :main ^:skip-aot xkcdpwgen.core
   :profiles {:uberjar {:aot :all}}
   :dependencies
   [  [org.clojure/algo.generic "0.1.2"]
      [org.clojure/clojure      "1.5.1"]
      [org.clojure/tools.cli    "0.3.1"]  ]  )
