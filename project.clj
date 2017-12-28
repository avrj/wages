(defproject wages "0.1.0-SNAPSHOT"
  :description "Wages"
  :url "https://github.com/avrj/wages"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.14.2"]
                 [semantic-csv "0.2.1-alpha1"]]
  :main ^:skip-aot wages.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
