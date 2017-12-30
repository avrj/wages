
(ns wages.monthly-wages
    (:require [clj-time.core :as t]
              [wages.data.transformation :refer :all]
              [wages.data.csv :refer :all]
              [wages.util.date :refer :all]))

(defn format-workshift [workshift]
    "Formats workshift to readable format"
    (let [id (get workshift :id)
            name (get workshift :name)
            monthly-wage (get workshift :monthly-wage)]
        (str id ", " name ", $" monthly-wage)))

(defn get-monthly-wages [workshifts year month]
    "Applies transformations to workshifts and filters them by year and month"
    (->> workshifts
        (mapv parse-date-from-workshift)
        (filter #(date-within-year-and-month? (get % :Date) year month))
        (mapv parse-times-from-workshift)
        (transform-to-monthly-wages)
        (map format-workshift)
        (interpose "\n")
        (apply str)))

(def ^:const ^:private workshifts
    (parse-csv-to-workshifts))

(defn print-monthly-wages [year month]
    "Pretty prints monthly wages with title"
    (println (str "Monthly Wages " month "/" year ":"))
    (println (get-monthly-wages workshifts 2014 3)))