(ns wages.overtime-test
    (:require [clojure.test :refer :all]
              [wages.rates.rate :refer :all]
              [wages.domain.overtime :refer :all]
              [clj-time.core :as t]))
  
(deftest overtime-wage-test
    (testing "Overtime wage is calculated correctly when 7 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 7) 0M))))

(deftest overtime-wage-test-1
    (testing "Overtime wage is calculated correctly when 8 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 8) 0M))))

(deftest overtime-wage-test-2
    (testing "Overtime wage is calculated correctly when 9 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 9) 1.125M))))

(deftest overtime-wage-test-3
    (testing "Overtime wage is calculated correctly when 11 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 11) 3.375M))))

(deftest overtime-wage-test-4
    (testing "Overtime wage is calculated correctly when 12 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 12) 5.625M))))

(deftest overtime-wage-test-5
    (testing "Overtime wage is calculated correctly when 13 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 13) 10.125M))))

(deftest overtime-wage-test-6
    (testing "Overtime wage is calculated correctly when 15 overtime hours"
        (is (= (calculate-overtime-wage-based-on-overtime-hours 15) 19.125M))))