(ns wages.util.date
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

(defn shift-date-by-day [date]
    (t/plus date (t/days 1)))


(defn date-within-year-and-month? [date year month]
    "Returns true if date is within the given year and month"
    (let [first-day-of-the-month (t/first-day-of-the-month year month)
            last-day-of-the-month (t/last-day-of-the-month year month)
            date-range (t/interval first-day-of-the-month last-day-of-the-month)]
        (t/within? date-range date)))

