(ns wages.core
  (:require [wages.ui.ui :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (show-wages))
