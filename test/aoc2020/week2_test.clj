(ns aoc2020.week2-test
  (:require [clojure.test :refer :all]
            [aoc2020.week2 :refer :all]))

(deftest calculate-test
  (testing "Week2 test"
    (let [[a b] (calculate "week2_input1.txt")]
      (do
        (is (= a 515))
        (is (= b 711))
        ))))
