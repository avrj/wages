(ns wages.data.transformation
    (:require [clj-time.core :as t]
              [wages.util.date :refer :all]
              [wages.util.currency :refer :all]
              [wages.domain.wage :refer :all]
              [wages.domain.workshift :refer :all]))
    
(defn replace-time-from-workshift [workshift key date]
    (update workshift key #(replace-time-in-date % date)))

(defn shift-end-date [workshift]
    (cond-> workshift
        (workshift-lasts-overnight? (get workshift :Start) (get workshift :End)) (update :End #(shift-date-by-day %))))

(defn assoc-total-daily-pay [workshift]
    (let [start (get workshift :Start)
            end (get workshift :End)
            total-daily-pay (calculate-total-daily-pay start end)]
        (assoc workshift :total-daily-pay total-daily-pay)))

(defn construct-monthly-wage-map-from-workshift [[id workshift]]
    {:id    id
     :name  (get (first workshift) (keyword "Person Name")) 
     :monthly-wage (round-to-nearest-cents (apply + (map :total-daily-pay workshift)))})

(defn transform-to-monthly-wages [workshifts]
    (->> workshifts
        (group-by (keyword "Person ID")) 
        (map construct-monthly-wage-map-from-workshift)))

(defn parse-time-from-workshift [workshift key]
    (update workshift key #(parse-time %)))

(defn parse-times-from-workshift [workshift]
    (-> workshift
        (parse-time-from-workshift :Start)
        (parse-time-from-workshift :End)
        (replace-time-from-workshift :Start (get workshift :Date))
        (replace-time-from-workshift :End (get workshift :Date))
        (shift-end-date)
        (dissoc :Date)
        (assoc-total-daily-pay)))

(defn parse-date-from-workshift [workshift]
    "Parses string representation of date to DateTime"
    (-> workshift
        (update :Date #(parse-date %))))     

