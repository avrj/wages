(ns wages.util.currency)

(defn round-to-nearest-cents [amount]
    (with-precision 4 (/ amount 1)))
