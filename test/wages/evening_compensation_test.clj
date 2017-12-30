(ns wages.evening-compensation-test
    (:require [clojure.test :refer :all]
              [wages.domain.evening-compensation :refer :all]
              [clj-time.core :as t]))
  
(deftest evening-compensation-time-test
    (testing "Should replace evening compensation start-time's y/m/d date with given date"
        (is (= (evening-compensation-time (t/date-time 1986 10 2 13 30) :start-time) (t/date-time 1986 10 2 19)))))

  
(deftest evening-compensation-end-time-test
    (testing "Should replace evening compensation end-time's y/m/d date with given date"
        (is (= (evening-compensation-end-time (t/date-time 1986 10 2 13 30)) (t/date-time 1986 10 3 6)))))

(deftest evening-compensation-hours-zero-test
    (testing "No evening compensation if shift is only in the range of regular worktimes"
        (is (= (evening-compensation-hours (t/date-time 1986 10 2 8) (t/date-time 1986 10 2 16)) 0))))

(deftest evening-compensation-hours-test
    (testing "Calculates evening compensation hours correctly"
        (is (= (evening-compensation-hours (t/date-time 1986 10 2 13 30) (t/date-time 1986 10 3 6)) 11))))

(deftest calculate-evening-compensation-wage-test
    (testing "Calculates evening compensation wage correctly"
        (is (= (calculate-evening-compensation-wage (t/date-time 1986 10 2 15) (t/date-time 1986 10 2 20)) 1.25M))))

(deftest calculate-evening-compensation-wage-zero-test
    (testing "No evening compensation wage if shift is only in the range of regular worktimes"
        (is (= (calculate-evening-compensation-wage (t/date-time 1986 10 2 8) (t/date-time 1986 10 2 16)) 0M))))