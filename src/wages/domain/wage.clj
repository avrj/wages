(ns wages.domain.wage
  (:require [clj-time.core :as t]
            [wages.domain.evening-compensation :refer :all]
            [wages.domain.overtime :refer :all]
            [wages.domain.workshift :refer :all]))
  
(defn calculate-total-daily-pay [workshift-start-time workshift-end-time]
    (let [working-hours (shift-duration-in-hours workshift-start-time workshift-end-time)
            regular-daily-wage (calculate-regular-daily-wage working-hours)
            evening-compensation-wage  (calculate-evening-compensation-wage workshift-start-time workshift-end-time)
            total-daily-pay (+ regular-daily-wage evening-compensation-wage)]
         total-daily-pay))
