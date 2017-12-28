(ns wages.overtime-test
    (:require [clojure.test :refer :all]
              [wages.rate :refer :all]
              [wages.overtime :refer :all]
              [clj-time.core :as t]))
  
(deftest overtime-addition-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-addition (first overtime-compensation-rates) (t/date-time 1986 10 2 13 30) :overtime-compensation-start) (t/date-time 1986 10 2 21 30)))))

(deftest overtime-addition-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-addition (first overtime-compensation-rates) (t/date-time 1986 10 2 23 30) :overtime-compensation-start) (t/date-time 1986 10 3 7 30)))))

(deftest overtime-hours-zero-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (first overtime-compensation-rates)) 0))))

(deftest overtime-hours-x-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 16) (first overtime-compensation-rates)) 1))))

(deftest overtime-hours-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 8) (first overtime-compensation-rates)) 1))))

(deftest overtime-hours-zero-test-1
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (second overtime-compensation-rates)) 0))))

(deftest overtime-hours-x-test-1
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 19) (second overtime-compensation-rates)) 1))))

(deftest overtime-hours-test-1
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 11) (second overtime-compensation-rates)) 1))))


(deftest overtime-hours-zero-test-2
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (nth overtime-compensation-rates 2)) 0))))

(deftest overtime-hours-x-test-2
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 20) (nth overtime-compensation-rates 2)) 1))))

(deftest overtime-hours-test-2
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 12) (nth overtime-compensation-rates 2)) 1))))

(deftest overtime-wage-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (calculate-overtime-wage (t/date-time 2017 12 28 8)  (t/date-time 2017 12 28 17)) 1.125M))))

(deftest overtime-wage-zero-test
    (testing "Shift duration in hours should be zero if shift ends before it starts"
        (is (= (calculate-overtime-wage (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16)) 0M))))