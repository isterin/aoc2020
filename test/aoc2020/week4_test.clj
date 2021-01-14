(ns aoc2020.week4-test
  (:require [clojure.test :refer :all]
            [aoc2020.week4 :refer :all]))

(deftest calculate-test
  (testing "Week4 test"
    (let [[a b] (calculate "week4_input1.txt")]
      (do
        (is (= a 245))
        (is (= b 133))
        ))))
