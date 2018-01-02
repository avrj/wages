(ns wages.data.transformation
    (:require [clj-time.core :as t]
              [wages.util.date :refer :all]
              [wages.util.currency :refer :all]
              [wages.domain.wage :refer :all]
              [wages.domain.workshift :refer :all]
              [wages.domain.overtime :refer :all]))
    
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

(defn construct-monthly-wage-map-from-workshift [workshift]
    (let [monthly-wage (round-to-nearest-cents (apply + (map :monthly-wage workshift)))]
        {:id (get (first workshift) :id) 
        :name  (get (first workshift) :name) 
        :monthly-wage monthly-wage}))

(defn overtime-for-day [[_ workshift]]
    (let [monthly-wage (apply + (map :total-daily-pay workshift))
          daily-overtime-wage (calculate-overtime-wage-based-on-overtime-hours (apply + (map (fn [x] (shift-duration-in-hours (get x :Start) (get x :End))) workshift)))]
    {:id (get (first workshift) (keyword "Person ID")) 
     :name  (get (first workshift) (keyword "Person Name")) 
     :monthly-wage (+ monthly-wage daily-overtime-wage)}))

(defn apply-overtime-to-monthly-wages [workshifts]
    (map (fn [[id workshift]] (mapv overtime-for-day (group-by (fn [workshift] (t/date-time (t/year (get workshift :Start)) (t/month (get workshift :Start)) (t/day (get workshift :Start)))) workshift))) workshifts))

(defn transform-to-monthly-wages [workshifts]
    (->> workshifts
        (group-by (keyword "Person ID")) 
        (apply-overtime-to-monthly-wages)
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

