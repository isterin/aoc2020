(ns aoc2020.day14-test
  (:require [clojure.test :refer :all]
            [aoc2020.day14 :refer :all]))

(deftest calculate-test
  (testing "Day 14 test"
    (let [[a b] (calculate "day14_input1.txt")]
      (do
        (is (= a 7440382076205))
        (is (= b 4200656704538))
        ))))