(ns wages.currency-test
    (:require [clojure.test :refer :all]
              [wages.util.currency :refer :all]))
  
(deftest round-to-nearest-cents-test
    (testing "round-to-nearest-cents should round 72.525M to 72.63M"
        (is (= (round-to-nearest-cents 72.625M) 72.63M))))

(deftest round-to-nearest-cents-test-1
    (testing "round-to-nearest-cents should round 1M to 1.00M"
        (is (= (round-to-nearest-cents 1M) 1.00M))))

(deftest round-to-nearest-cents-test-2
    (testing "round-to-nearest-cents should round 72.624M to 72.62M"
        (is (= (round-to-nearest-cents 72.624M) 72.62M))))

(deftest round-to-nearest-cents-test-3
    (testing "round-to-nearest-cents should round 720.525M to 720.63M"
        (is (= (round-to-nearest-cents 720.625M) 720.63M))))

(deftest round-to-nearest-cents-test-4
    (testing "round-to-nearest-cents should round 10M to 10.00M"
        (is (= (round-to-nearest-cents 10M) 10.00M))))

(deftest round-to-nearest-cents-test-5
    (testing "round-to-nearest-cents should round 720.624M to 720.62M"
        (is (= (round-to-nearest-cents 720.624M) 720.62M))))

