(ns wages.csv
    (:require [clojure.java.io :as io]
              [clojure-csv.core :as csv]
              [semantic-csv.core :as sc]))

(defn parse-csv []
    (with-open [in-file (io/reader "HourList201403.csv")]
        (->>
        (csv/parse-csv in-file)
        (sc/remove-comments)
        (sc/mappify)
        (sc/cast-with {:Date #(str %)})
        doall)))