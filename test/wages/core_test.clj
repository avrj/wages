(ns wages.core-test
  (:require [clojure.test :refer :all]
            [wages.workshift :refer :all]
            [clj-time.core :as t]))

(deftest negative-duration-test
  (testing "Shift duration in hours should be zero if shift ends before it starts"
    (is (= (shift-duration-in-hours (t/date-time 1986 10 2 13 30) (t/date-time 1986 10 2 13 00)) 0))))

(deftest correct-duration-test
  (testing "Shift duration in hours should be counted correctly"
    (is (= (shift-duration-in-hours (t/date-time 1986 10 2 13 30) (t/date-time 1986 10 2 14 00)) 1/2))))

(deftest zero-duration-test
  (testing "Shift duration in hours should be zero if shift ends at the same time as it starts"
    (is (= (shift-duration-in-hours (t/date-time 1986 10 2 13 30) (t/date-time 1986 10 2 13 30)) 0))))