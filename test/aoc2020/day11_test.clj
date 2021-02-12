(ns aoc2020.day11-test
  (:require [clojure.test :refer :all]
            [aoc2020.day11 :refer :all]))

(deftest calculate-test
  (testing "Day 11 test"
    (let [[a b] (calculate "day11_input1.txt")]
      (do
        (is (= a 2368))
        (is (= b 2124))
        ))))