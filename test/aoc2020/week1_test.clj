(ns aoc2020.week1-test
  (:require [clojure.test :refer :all]
            [aoc2020.week1 :refer :all]))

;(deftest a-test
;  (testing "FIXME, I fail."
;    (is (= 0 1))))


(deftest calculate-test
  (testing "Calculate test"
    (let [[a b] (calculate "week1_input1.txt")]
      (do
        (is (= a 618144))
        (is (= b 173538720))))))
