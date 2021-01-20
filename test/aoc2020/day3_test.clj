(ns aoc2020.day3-test
  (:require [clojure.test :refer :all]
            [aoc2020.day3 :refer :all]))

(deftest calculate-test
  (testing "Day 3 test"
    (let [[a b] (calculate "day3_input1.txt")]
      (do
        (is (= a 153))
        (is (= b 2421944712))))))
