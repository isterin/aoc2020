(ns aoc2020.week1-test
  (:require [clojure.test :refer :all]
            [aoc2020.week1 :refer :all]))

(deftest calculate-test
  (testing "Week1 test"
    (let [[a b] (calculate "week1_input1.txt")]
      (do
        (is (= a 618144))
        (is (= b 173538720))))))
