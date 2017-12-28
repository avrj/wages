(ns wages.workshift
  (:require [clj-time.core :as t]
            [wages.rate :refer :all]
            [wages.date :refer :all]))
  
(defn shift-duration-in-hours [workshift-start-time workshift-end-time]
  "Given start-time and end-time, calculates shift duration in hours"
    (if (t/after? workshift-start-time workshift-end-time)
     0
     (interval->hours (t/interval workshift-start-time workshift-end-time))))
     
(defn calculate-period-wage [shift-duration-in-hours wage]
  (* shift-duration-in-hours wage))
  
(defn calculate-regular-daily-wage [working-hours]
  (calculate-period-wage working-hours hourly-wage))