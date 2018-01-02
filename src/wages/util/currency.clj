(ns wages.util.currency)

(defn- round [s]
    (fn [n]
      (assert (decimal? n))
      (.setScale n s java.math.RoundingMode/HALF_UP)))

(def ^:private round2
    (round 2))
 
(defn round-to-nearest-cents [amount]
    (round2 amount))
