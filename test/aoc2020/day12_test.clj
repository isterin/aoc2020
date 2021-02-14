(ns aoc2020.day12-test
  (:require [clojure.test :refer :all]
            [aoc2020.day12 :refer :all]))

(deftest calculate-test
  (testing "Day 11 test"
    (let [[a b] (calculate "day12_input1.txt")]
      (do
        (is (= a 1496))
        (is (= b 2))
        ))))