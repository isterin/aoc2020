(ns aoc2020.day10-test
  (:require [clojure.test :refer :all]
            [aoc2020.day10 :refer :all]))

(deftest calculate-test
  (testing "Day 9 test"
    (let [[a b] (calculate "day10_input1.txt")]
      (do
        (is (= a 2277))
        (is (= b 37024595836928))
        ))))