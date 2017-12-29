(ns wages.core
  (:require [clj-time.core :as t]
            [wages.rate :refer :all]
            [wages.date :refer :all]
            [wages.wages :refer :all]
            [wages.csv :refer :all])
  (:gen-class))
  

(def ^:const ^:private workshifts
  (into [] (parse-csv)))

(defn replace-time-from-workshift [workshift key date]
  (update workshift key #(replace-time-in-date % date)))

(defn shift-end-date [workshift]
  (cond-> workshift
    (> (t/hour (get workshift :Start)) (t/hour (get workshift :End))) (update :End #(t/plus % (t/days 1)))))
  

(defn assoc-total-daily-pay [workshift]
  (let [start (get workshift :Start)
        end (get workshift :End)
        total-daily-pay (calculate-total-daily-pay start end)]
    (assoc workshift :total-daily-pay total-daily-pay)))

(defn construct-monthly-wage-map-from-workshift [[id workshift]]
  {:id    id
   :name  (get (first workshift) (keyword "Person Name")) 
   :monthly-wage (apply + (map :total-daily-pay workshift))})

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

(defn filter-by-year-and-month [date year month]
  "Returns true if date is within the given year and month"
  (let [first-day-of-the-month (t/first-day-of-the-month year month)
        last-day-of-the-month (t/last-day-of-the-month year month)
        date-range (t/interval first-day-of-the-month last-day-of-the-month)]
   (t/within? date-range date)))

(defn parse-date-from-workshift [workshift]
  "Parses string representation of date to DateTime"
  (-> workshift
    (update :Date #(parse-date %))))     

(def wages
  (println (apply str (interpose "\n" (map #(str (get % :id) ", " (get % :name) ", $" (get % :monthly-wage)) (transform-to-monthly-wages (mapv parse-times-from-workshift (filter #(filter-by-year-and-month (get % :Date) 2014 3) (mapv parse-date-from-workshift workshifts)))))))))

(defn -main
  [& args]
  (println wages))

