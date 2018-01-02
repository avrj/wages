(ns wages.domain.overtime
    (:require [wages.rates.rate :refer :all]))

(defn- overtime-calculation [overtime-compensation overtime-hours]
 (if (>= overtime-hours (+ regular-working-hours (get overtime-compensation :overtime-compensation-start)))
    (if (> (+ regular-working-hours (get overtime-compensation :overtime-compensation-end)) overtime-hours)
        (* hourly-wage (get overtime-compensation :overtime-compensation-percent) (- (- overtime-hours 8) (get overtime-compensation :overtime-compensation-start)))
        (* hourly-wage (get overtime-compensation :overtime-compensation-percent) (- (get overtime-compensation :overtime-compensation-end) (get overtime-compensation :overtime-compensation-start))))
    0))

(defn- overtime? [working-hours]
    (> working-hours regular-working-hours))

(defn calculate-overtime-wage-based-on-overtime-hours [overtime-hours]
    (if (overtime? overtime-hours)
        (bigdec (apply + (map (fn [overtime-compensation] (overtime-calculation overtime-compensation overtime-hours)) overtime-compensation-rates)))
        0M))
