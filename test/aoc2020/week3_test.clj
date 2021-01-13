(ns aoc2020.week3-test
  (:require [clojure.test :refer :all]
            [aoc2020.week3 :refer :all]))

(deftest calculate-test
  (testing "Week3 test"
    (let [[a b] (calculate "week3_input1.txt")]
      (do
        (is (= a 153))
        (is (= b 2421944712))))))
