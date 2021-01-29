(ns aoc2020.day8-test
  (:require [clojure.test :refer :all]
            [aoc2020.day8 :refer :all]))

(deftest calculate-test
  (testing "Day 8 test"
    (let [[a b] (calculate "day8_input1.txt")]
      (do
        (is (= a 1749))
        (is (= b 515))
        ))))