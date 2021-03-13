(ns aoc2020.day15
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]
            [clojure.math.combinatorics :as combo]
            [flatland.ordered.set :refer [ordered-set]]))

(declare calculate process)

(defn calculate [input]
  [(process input 2020)
   (process input 30000000)])

(defn- process [input n]
  (loop [lst input
         idx (count input)]
    (if (<= n idx)
      (last lst)
      (let [i (.lastIndexOf (drop-last lst) (lst (dec idx)))
            curr (if (= i -1) 0 (- (dec idx) i))]
        ;(println curr idx)
        (recur (conj lst curr) (inc idx))))))