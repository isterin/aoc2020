(ns aoc2020.day1-test
  (:require [clojure.test :refer :all]
            [aoc2020.day1 :refer :all]))

(deftest calculate-test
  (testing "Day 1 test"
    (let [[a b] (calculate "day1_input1.txt")]
      (do
        (is (= a 618144))
        (is (= b 173538720))))))
