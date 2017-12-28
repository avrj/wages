(ns wages.wages
  (:require [clj-time.core :as t]
            [wages.evening-compensation :refer :all]
            [wages.overtime :refer :all]
            [wages.workshift :refer :all]))
  
(defn calculate-total-daily-pay [workshift-start-time workshift-end-time]
    (let [working-hours (shift-duration-in-hours workshift-start-time workshift-end-time)
            regular-daily-wage (calculate-regular-daily-wage working-hours)
            evening-compensation-wage  (calculate-evening-compensation-wage workshift-start-time workshift-end-time)
            overtime-wage  (calculate-overtime-wage workshift-start-time workshift-end-time)
            total-daily-pay (+ regular-daily-wage evening-compensation-wage overtime-wage)]
         total-daily-pay))
