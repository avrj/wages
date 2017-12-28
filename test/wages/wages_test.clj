(ns wages.wages-test
    (:require [clojure.test :refer :all]
              [wages.wages :refer :all]
              [clj-time.core :as t]))
  
(deftest calculate-total-daily-pay-in-regular-worktime-test
(testing "Calculates total daily pay correctly when shift is in regular worktime"
    (is (= (calculate-total-daily-pay (t/date-time 1986 10 2 8) (t/date-time 1986 10 2 16)) 36M))))

(deftest calculate-total-daily-pay-with-overtime-test
(testing "Calculates total daily pay correctly when shift goes overtime"
    (is (= (calculate-total-daily-pay (t/date-time 1986 10 2 8) (t/date-time 1986 10 2 17)) 41.625M))))


(deftest calculate-total-daily-pay-with-evening-compensation-test
(testing "Calculates total daily pay correctly when shift includes evening hours"
    (is (= (calculate-total-daily-pay (t/date-time 1986 10 2 15) (t/date-time 1986 10 2 20)) 23.75M))))
    
(deftest calculate-total-daily-pay-in-overnight-shift-test
    (testing "Calculates total daily pay corretcly when shift lasts overnight"
        (is (= (calculate-total-daily-pay (t/date-time 1986 10 2 18) (t/date-time 1986 10 3 07)) 82.375M))))

(deftest calculate-total-daily-pay-with-overtime-test-2
    (testing "Calculates total daily pay correctly when shift goes overtime (2)"
        (is (= (calculate-total-daily-pay (t/date-time 1986 10 2 6) (t/date-time 1986 10 2 18)) 59.625M))))