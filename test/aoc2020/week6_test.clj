(ns aoc2020.week6-test
  (:require [clojure.test :refer :all]
            [aoc2020.week6 :refer :all]))

(deftest calculate-test
  (testing "Week6 test"
    (let [[a b] (calculate "week6_input1.txt")]
      (do
        (is (= a 6297))
        (is (= b 3158))
        ))))
