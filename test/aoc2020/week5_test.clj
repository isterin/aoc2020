(ns aoc2020.week5-test
  (:require [clojure.test :refer :all]
            [aoc2020.week5 :refer :all]))

(deftest calculate-test
  (testing "Week5 test"
    (let [[a b] (calculate "week5_input1.txt")]
      (do
        (is (= a (map->Seat {:spec "BBBBFBFLRL" :row 122 :col 2 :seat-num 978})))
        (is (= b 727))
        ))))
