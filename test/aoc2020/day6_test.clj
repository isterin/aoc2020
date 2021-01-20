(ns aoc2020.day6-test
  (:require [clojure.test :refer :all]
            [aoc2020.day6 :refer :all]))

(deftest calculate-test
  (testing "Day 6 test"
    (let [[a b] (calculate "day6_input1.txt")]
      (do
        (is (= a 6297))
        (is (= b 3158))
        ))))
