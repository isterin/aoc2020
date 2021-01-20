(ns aoc2020.day5-test
  (:require [clojure.test :refer :all]
            [aoc2020.day5 :refer :all]))

(deftest calculate-test
  (testing "Day 5 test"
    (let [[a b] (calculate "day5_input1.txt")]
      (do
        (is (= a (map->Seat {:spec "BBBBFBFLRL" :row 122 :col 2 :seat-num 978})))
        (is (= b 727))
        ))))
