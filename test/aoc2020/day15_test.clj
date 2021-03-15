(ns aoc2020.day15-test
  (:require [clojure.test :refer :all]
            [aoc2020.day15 :refer :all]))

(deftest calculate-test
  (testing "Day 14 test"
    (let [a (calculate [8 0 17 4 1 12] 2020)
          b (calculate [8 0 17 4 1 12] 30000000)]
      (do
        (is (= a 981))
        (is (= b 0))
        ))))