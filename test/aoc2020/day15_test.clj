(ns aoc2020.day15-test
  (:require [clojure.test :refer :all]
            [aoc2020.day15 :refer :all]))

(deftest calculate-test
  (testing "Day 14 test"
    (let [[a b] (calculate [8 0 17 4 1 12])]
      (do
        (is (= a 981))
        (is (= b 0))
        ))))