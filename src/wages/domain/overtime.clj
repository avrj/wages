(ns wages.domain.overtime
    (:require [clj-time.core :as t]
              [wages.rates.rate :refer :all]
              [wages.util.date :refer :all]
              [wages.domain.workshift :refer :all]))

(defn overtime-wage [overtime-compensation-rate]
  (let [overtime-compensation-percent (get overtime-compensation-rate :overtime-compensation-percent)]
    (* hourly-wage overtime-compensation-percent)))
  
(defn overtime-date [overtime-hours workshift-start-date]
    "Returns the date of overtime start/end"
  (let [full-hours     (+ regular-working-hours overtime-hours)
        hours          (t/hours full-hours)]
    (t/plus workshift-start-date hours)))

(defn- overtime-start-time [overtime-compensation-rate workshift-start-date]
  (overtime-date (get overtime-compensation-rate :overtime-compensation-start) workshift-start-date))
  
(defn- overtime-end-time [overtime-compensation-rate workshift-start-date]
  (overtime-date (get overtime-compensation-rate :overtime-compensation-end) workshift-start-date))

(defn overlap-between-shift-and-overtime [workshift-start-date workshift-end-date overtime-compensation-rate]
    (let [calculated-overtime-start-time (overtime-start-time overtime-compensation-rate workshift-start-date)
          calculated-overtime-end-time   (overtime-end-time overtime-compensation-rate workshift-start-date)
          workshift-interval (t/interval workshift-start-date workshift-end-date)
          overtime-interval (t/interval calculated-overtime-start-time calculated-overtime-end-time)]
    (t/overlap workshift-interval overtime-interval)))

(defn overtime? [working-hours]
    (> working-hours regular-working-hours))

(defn overtime-hours [workshift-start-date workshift-end-date overtime-compensation-rate]
    (let [calculated-shift-duration-in-hours (shift-duration-in-hours workshift-start-date workshift-end-date)]
        (if (overtime? calculated-shift-duration-in-hours)
        (if (nil? (overlap-between-shift-and-overtime workshift-start-date workshift-end-date overtime-compensation-rate))
            0
            (interval->hours (overlap-between-shift-and-overtime workshift-start-date workshift-end-date overtime-compensation-rate)))
        0)))

(defn calculate-single-overtime-wage [workshift-start-date workshift-end-date overtime-compensation-rate] 
    (calculate-period-wage (overtime-hours workshift-start-date workshift-end-date overtime-compensation-rate) (overtime-wage overtime-compensation-rate)))

(defn calculate-overtime-wage [workshift-start-date workshift-end-date]
    (bigdec (apply + (map #(calculate-single-overtime-wage workshift-start-date workshift-end-date %) overtime-compensation-rates))))

(defn calculate-overtime-wage-based-on-overtime-hours [overtime-hours]
    (bigdec (apply + (map (fn [overtime-compensation] (if (<= (+ regular-working-hours (get overtime-compensation :overtime-compensation-start)) overtime-hours) (* hourly-wage (get overtime-compensation :overtime-compensation-percent) (- (if (> (get overtime-compensation :overtime-compensation-end) overtime-hours) (- overtime-hours regular-working-hours) (get overtime-compensation :overtime-compensation-end)) (get overtime-compensation :overtime-compensation-start))) 0) ) overtime-compensation-rates))))