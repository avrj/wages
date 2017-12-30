(ns wages.overtime-test
    (:require [clojure.test :refer :all]
              [wages.rates.rate :refer :all]
              [wages.domain.overtime :refer :all]
              [clj-time.core :as t]))
  
(deftest overtime-date-test
    (testing "Overtime date is constructed correctly when overtime starts 0 hours after regular-working-hours"
        (is (= (overtime-date 0 (t/date-time 1986 10 2 13 30)) (t/date-time 1986 10 2 21 30)))))

(deftest overtime-date-test
    (testing "Overtime date is constructed correctly when overtime starts 3 hours after regular-working-hours"
        (is (= (overtime-date 3 (t/date-time 1986 10 2 23 30)) (t/date-time 1986 10 3 10 30)))))

(deftest overtime-hours-zero-test
    (testing "Should be no overtime hours if workshift is in regular work hours"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (first overtime-compensation-rates)) 0))))

(deftest overtime-hours-one-test
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 16) (first overtime-compensation-rates)) 1))))

(deftest overtime-hours-overnight-test
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours and workshift lasts overnight"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 8) (first overtime-compensation-rates)) 1))))

(deftest overtime-hours-zero-test-1
    (testing "Should be no overtime hours if workshift is in regular work hours (2)"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (second overtime-compensation-rates)) 0))))

(deftest overtime-hours-one-test-1
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours (2)"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 19) (second overtime-compensation-rates)) 1))))

(deftest overtime-hours-overnight-test-1
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours and workshift lasts overnight (2)"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 11) (second overtime-compensation-rates)) 1))))

(deftest overtime-hours-zero-test-2
    (testing "Should be no overtime hours if workshift is in regular work hours (3)"
        (is (= (overtime-hours (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16) (nth overtime-compensation-rates 2)) 0))))

(deftest overtime-hours-one-test-2
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours (3)"
        (is (= (overtime-hours (t/date-time 2017 12 28 7) (t/date-time 2017 12 28 20) (nth overtime-compensation-rates 2)) 1))))

(deftest overtime-hours-overnight-test-2
    (testing "Should be one overtime hour if workshift is one hour longer than regular work hours and workshift lasts overnight (3)"
        (is (= (overtime-hours (t/date-time 2017 12 28 23) (t/date-time 2017 12 29 12) (nth overtime-compensation-rates 2)) 1))))

(deftest overtime-wage-test
    (testing "Overtime wage is calculated correctly"
        (is (= (calculate-overtime-wage (t/date-time 2017 12 28 8)  (t/date-time 2017 12 28 17)) 1.125M))))

(deftest overtime-wage-zero-test
    (testing "Should be no overtime wage if workshift is in regular work hours"
        (is (= (calculate-overtime-wage (t/date-time 2017 12 28 8) (t/date-time 2017 12 28 16)) 0M))))