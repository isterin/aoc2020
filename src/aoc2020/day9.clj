(ns aoc2020.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))

(declare calculate find-bad-number-from has-sum-combo find-sum-to find-contiguous-nums)

(def window-size 25)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        input-numbers (map #(Long/parseLong %) lines)
        answer1 (first (find-bad-number-from input-numbers window-size))
        contiguous-nums (vec (find-sum-to input-numbers answer1))
        min-num (apply min contiguous-nums)
        max-num (apply max contiguous-nums)]
    [answer1 (+ min-num max-num)]))

(defn- find-bad-number-from [input-numbers window-size]
  (for [numbers (partition (inc window-size) 1 input-numbers)
        :let [lst (drop-last numbers)
              num (last numbers)]
        :when (empty? (has-sum-combo lst num))]
    num))

(defn- has-sum-combo [lst num]
  (for [[a b] (combo/combinations lst 2)
        :let [x (+ a b)]
        :when (= x num)]
    x))

(defn- find-sum-to [numbers sum-to]
  (first (for [idx (range (count numbers))
               :let [contiguous-nums (find-contiguous-nums (drop idx numbers) sum-to)]
               :when contiguous-nums]
           contiguous-nums)))

(defn- find-contiguous-nums [numbers sum-to]
  (loop [idx 0
         sum 0]
    (cond
      (> sum sum-to) nil
      (= sum sum-to) (take (+ idx 1) numbers)
      :default (recur (+ idx 1) (+ sum (nth numbers idx))))))

