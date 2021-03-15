(ns aoc2020.day15
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]
            [clojure.math.combinatorics :as combo]
            [flatland.ordered.set :refer [ordered-set]]))

(declare calculate)

(defn calculate [input n]
  ;(println input)
  (loop [lst input
         idx (dec (count input))
         cache (into {} (map-indexed (fn [idx itm] [itm idx]) (drop-last input)))]
    ;(println (take-last 5 lst))
    ;(println lst)
    ;(println idx n (count input))
    (when (= (mod idx 10000) 0)
      (do
        (println idx (type lst) (type cache))f
        (time (get cache (lst idx)))
        (time (assoc cache (last lst) idx))
        (time (conj lst 1))))
    (if (< idx (dec n))
      (let [i (get cache (lst idx))
            curr (if i (- idx i) 0)
            cache (assoc cache (last lst) idx)]
        (recur (conj lst curr) (inc idx) cache))
      (last lst))))