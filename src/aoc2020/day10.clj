(ns aoc2020.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]
            [loom.alg :as alg]
            [loom.alg-generic :as lag]))

(declare calculate count-jolt-diffs generate-combinatorial-graph add-nodes-for arrangements arrangements-memoized)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        jolts (map #(Integer/parseInt %) lines)
        ordered-jolts (sort jolts)
        ordered-jolts-full-chain (-> ordered-jolts (conj 0) (concat `(~(+ (last ordered-jolts) 3))))
        jolt-counts (count-jolt-diffs ordered-jolts-full-chain)
        jolt-frequencies (frequencies jolt-counts)]
    [(* (jolt-frequencies 1) (jolt-frequencies 3))
     (arrangements-memoized (seq ordered-jolts) 0)]))

(defn- count-jolt-diffs [ord-nums]
  (for [[a b] (partition 2 1 ord-nums)
        :let [jolt (- b a)]]
    jolt))

(defn- arrangements [jolts prev]
  (let [jolt (first jolts)
        remaining (rest jolts)]
    (cond
      (> (- jolt prev) 3) 0
      (empty? remaining) 1
      :else (+ (arrangements-memoized remaining jolt)
               (arrangements-memoized remaining prev)))))

(def arrangements-memoized (memoize arrangements))