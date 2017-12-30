(ns wages.core
  (:require [wages.monthly-wages :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (print-monthly-wages 2014 3))