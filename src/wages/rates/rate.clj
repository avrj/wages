(ns wages.rates.rate)

(def ^:const regular-working-hours      8)
(def ^:const hourly-wage                4.5M)

(def ^:const overtime-compensation-rates
    [{:overtime-compensation-percent    0.25
     :overtime-compensation-start       0
     :overtime-compensation-end         3}

     {:overtime-compensation-percent   0.5
     :overtime-compensation-start      3
     :overtime-compensation-end        4}

     {:overtime-compensation-percent   1
     :overtime-compensation-start      4
     :overtime-compensation-end        24}])
  
(def ^:const evening-compensation
    {:start-time                       19
     :end-time                         06
     :wage                             1.25M})