(ns aoc2020.day13-test
  (:require [clojure.test :refer :all]
            [aoc2020.day13 :refer :all]))

(deftest calculate-test
  (testing "Day 13 test"
    (let [[a b] (calculate "day13_input1.txt")]
      (do
        (is (= a 1))
        (is (= b 2))
        ))))