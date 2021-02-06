(ns aoc2020.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]
            [loom.alg :as alg]
            [loom.alg-generic :as lag]))

(declare calculate take-turn adj-seats print-matrix go-until-done)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        seats (vec (map vec lines))
        occupied-after-run (->> seats go-until-done flatten (filter #(= \# %)) count)]
    [occupied-after-run 0]))

(defn- go-until-done [seats]
  (loop [seats seats
         prev []]
    (if (= seats prev)
      seats
      (recur (take-turn seats) seats))))

(defn- take-turn [seats]
  (vec (for [i (range (count seats))]
         (vec (for [j (range (count ((vec seats) i)))
                    :let [curr (get-in seats [i j])
                          adj (adj-seats seats i j)
                          new-seat (cond
                                     (= curr \.) \.
                                     (and (= curr \L) (= adj 0)) \#
                                     (and (= curr \#) (>= adj 4)) \L
                                     :else curr)]]
                new-seat)))))

(defn- adj-seats [seats i j]
  (count (filter #(= % \#) [(get-in seats [(- i 1) j])
                            (get-in seats [(+ i 1) j])
                            (get-in seats [i (- j 1)])
                            (get-in seats [i (+ j 1)])
                            (get-in seats [(- i 1) (- j 1)])
                            (get-in seats [(+ i 1) (+ j 1)])
                            (get-in seats [(- i 1) (+ j 1)])
                            (get-in seats [(+ i 1) (- j 1)])
                            ])))

(defn- print-matrix [m]
  (dorun (for [x m]
           (println x)))
  m)