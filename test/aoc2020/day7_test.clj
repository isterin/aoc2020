(ns aoc2020.day7-test
  (:require [clojure.test :refer :all]
            [aoc2020.day7 :refer :all]))

(deftest calculate-test
  (testing "Day 7 test"
    (let [[a b] (calculate "day7_input1.txt")]
      (do
        (is (= a 378))
        (is (= b 27526))
        ))))