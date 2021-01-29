(ns aoc2020.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))

(declare calculate find-bad-number-from has-sum-combo)

(def window-size 25)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        input-numbers (map #(Long/parseLong %) lines)
        answer1 (doall (find-bad-number-from input-numbers window-size))]
    answer1))

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

