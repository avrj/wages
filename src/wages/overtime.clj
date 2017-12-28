(ns wages.overtime
    (:require [clj-time.core :as t]
              [wages.rate :refer :all]
              [wages.date :refer :all]
              [wages.workshift :refer :all]))

(defn overtime-wage [overtime-compensation-rate]
  (let [overtime-compensation-percent (get overtime-compensation-rate :overtime-compensation-percent)]
    (* hourly-wage overtime-compensation-percent)))
  
(defn overtime-addition [overtime-compensation-rate workshift-start-date key]
  (let [overtime-hours (get overtime-compensation-rate key)
        full-hours     (+ regular-working-hours overtime-hours)
        hours          (t/hours full-hours)]
    (t/plus workshift-start-date hours)))

(defn- overtime-start-time [overtime-compensation-rate workshift-start-date]
  (overtime-addition overtime-compensation-rate workshift-start-date :overtime-compensation-start))
  
(defn- overtime-end-time [overtime-compensation-rate workshift-start-date]
  (overtime-addition overtime-compensation-rate workshift-start-date :overtime-compensation-end))

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