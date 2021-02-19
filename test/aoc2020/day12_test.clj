(ns aoc2020.day12-test
  (:require [clojure.test :refer :all]
            [aoc2020.day12 :refer :all]))

(deftest calculate-test
  (testing "Day 12 test"
    (let [[a b] (calculate "day12_input1.txt")]
      (do
        (is (= a 1496))
        (is (= b 63843))
        ))))


(deftest calculate-sample-test
  (testing "Day 12 test"
    (let [[a b] (calculate "day12_input1_sample.txt")]
      (do
        (is (= a 25))
        (is (= b 286))
        ))))