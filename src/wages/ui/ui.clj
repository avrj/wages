(ns wages.ui.ui
    (:require [wages.monthly-wages :refer :all]
              [wages.data.csv :refer :all]))
  
(def default-filename "HourList.csv")

(def default-year 2014)

(def default-month 3)

(defn filename-or-default [filename]
    (if (= filename "")
        default-filename
        filename))

(defn year-or-default []
    (println "Year (ENTER for default: 2014):")
    (let [year (read-line)]
        (try (Integer/parseInt year)
            (Integer/parseInt year)
            (catch Exception _
                default-year))))

(defn month-or-default []
    (println "Month (ENTER for default: 3):")
    (let [month (read-line)]
        (try (Integer/parseInt month)
            (Integer/parseInt month)
            (catch Exception _
                default-month))))

(defn get-input-with-error [error]
    (when (some? error) (println error))
    (println (str "File to open (ENTER for default: " default-filename "):"))
    (read-line))

(defn file-exists? [filename]
    (.exists (clojure.java.io/as-file filename)))

(defn show-wages []
    (loop [filename (get-input-with-error nil)]
        (if (file-exists? (filename-or-default filename))
        (print-monthly-wages (parse-csv-to-workshifts (filename-or-default filename)) (year-or-default) (month-or-default))
        (recur (get-input-with-error "File not found.")))))
