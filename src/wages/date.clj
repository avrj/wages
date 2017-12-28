(ns wages.date
    (:require [clj-time.core :as t]
              [clj-time.format :as f]))

(def ^:private time-formatter (f/formatters :hour-minute))

(def ^:private date-formatter (f/formatter "dd.MM.YYYY"))

(defn parse-time [time]
    (f/parse time-formatter time))

(defn parse-date [date]
    (f/parse date-formatter date))

(defn to-hours [minutes]
    (/ minutes 60))

(defn replace-time-in-date 
    [time date]
    (t/date-time (t/year date) (t/month date) (t/day date) (t/hour time) (t/minute time)))

(defn interval->hours 
    [interval]
    (to-hours (t/in-minutes interval)))
