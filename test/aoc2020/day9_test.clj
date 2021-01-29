(ns aoc2020.day9-test
  (:require [clojure.test :refer :all]
            [aoc2020.day9 :refer :all]))

(deftest calculate-test
  (testing "Day 9 test"
    (let [a (calculate "day9_input1.txt")]
      (do
        (is (= a 1749))
        ;(is (= b 515))
        ))))