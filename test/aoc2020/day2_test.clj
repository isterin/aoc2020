(ns aoc2020.day2-test
  (:require [clojure.test :refer :all]
            [aoc2020.day2 :refer :all]))

(deftest calculate-test
  (testing "Day 2 test"
    (let [[a b] (calculate "day2_input1.txt")]
      (do
        (is (= a 515))
        (is (= b 711))
        ))))
