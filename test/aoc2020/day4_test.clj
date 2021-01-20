(ns aoc2020.day4-test
  (:require [clojure.test :refer :all]
            [aoc2020.day4 :refer :all]))

(deftest calculate-test
  (testing "Day 4 test"
    (let [[a b] (calculate "day4_input1.txt")]
      (do
        (is (= a 245))
        (is (= b 133))
        ))))
