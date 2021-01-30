(ns aoc2020.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]))

(declare calculate count-jolt-diffs)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        input-numbers (map #(Integer/parseInt %) lines)
        ord-input-numbers (vec (sort input-numbers))
        device-jolts (+ (last ord-input-numbers) 3)
        jolt-counts (count-jolt-diffs (cons 0 (conj ord-input-numbers device-jolts)))
        jolt-frequencies (frequencies jolt-counts)]
    (* (jolt-frequencies 1) (jolt-frequencies 3))))

(defn- count-jolt-diffs [ord-nums]
  (for [[a b] (partition 2 1 ord-nums)
        :let [jolt (- b a)]]
    jolt))