(ns wages.evening-compensation
    (:require [clj-time.core :as t]
              [wages.rate :refer :all]
              [wages.date :refer :all]
              [wages.workshift :refer :all]))

(def ^:const ^:private evening-compensation-wage
    (get evening-compensation :wage))

(defn evening-compensation-time [workshift-start-date key]
    "Constructs DateTime by setting start-date's hour from evening-compensation key"
    (let [year  (t/year workshift-start-date)
          month (t/month workshift-start-date)
          day   (t/day workshift-start-date)
          hour  (get evening-compensation key)]
      (t/date-time year month day hour)))
    
(defn- evening-compensation-start-time [workshift-start-date]
    (evening-compensation-time workshift-start-date :start-time))
    
(defn evening-compensation-end-time [workshift-start-date]
 (let [evening-compensation-end-time (evening-compensation-time workshift-start-date :end-time)]
    (if (> (t/hour (evening-compensation-start-time workshift-start-date)) (t/hour evening-compensation-end-time)) 
     (t/plus evening-compensation-end-time (t/days 1))
     evening-compensation-end-time)))


(defn overlap-between-shift-and-evening-compensation [workshift-start-date workshift-end-date]
  (let [calculated-evening-compensation-start-time (evening-compensation-start-time workshift-start-date)
        calculated-evening-compensation-end-time (evening-compensation-end-time workshift-start-date)
        workshift-interval (t/interval workshift-start-date workshift-end-date)
        evening-compensation-interval (t/interval calculated-evening-compensation-start-time calculated-evening-compensation-end-time)]
    (t/overlap workshift-interval evening-compensation-interval)))
  

(defn evening-compensation-hours [workshift-start-date workshift-end-date]
  (let [overlap (overlap-between-shift-and-evening-compensation workshift-start-date workshift-end-date)]
    (if (nil? overlap) 
     0 
     (interval->hours overlap))))
  
(defn calculate-evening-compensation-wage [workshift-start-date workshift-end-date]
  (let [calculated-evening-compensation-hours (evening-compensation-hours workshift-start-date workshift-end-date)]
    (calculate-period-wage calculated-evening-compensation-hours evening-compensation-wage)))