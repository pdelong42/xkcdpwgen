(defproject xkcdpwgen "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot xkcdpwgen.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})