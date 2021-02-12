(ns aoc2020.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]
            [loom.alg :as alg]
            [loom.alg-generic :as lag]))

(declare calculate take-turn adj-seats print-matrix go-until-done adj-seats-far taken-in-direction)

(def ^:dynamic *threshold-seats* 4)
(def ^:dynamic *adjustment-func* nil)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        seats (vec (map vec lines))
        occupied-after-run (binding [*threshold-seats* 4
                                     *adjustment-func* adj-seats]
                             (->> seats go-until-done flatten (filter #(= \# %)) count))
        occupied-after-run-2 (binding [*threshold-seats* 5
                                       *adjustment-func* adj-seats-far]
                               (->> seats go-until-done flatten (filter #(= \# %)) count))]
    [occupied-after-run occupied-after-run-2]))

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
                          adj (*adjustment-func* seats i j)
                          new-seat (cond
                                     (= curr \.) \.
                                     (and (= curr \L) (= adj 0)) \#
                                     (and (= curr \#) (>= adj *threshold-seats*)) \L
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

(defn- adj-seats-far [seats i j]
  (count (filter #(= % \#) [(taken-in-direction seats (- i 1) j dec identity)
                            (taken-in-direction seats (+ i 1) j inc identity)
                            (taken-in-direction seats i (- j 1) identity dec)
                            (taken-in-direction seats i (+ j 1) identity inc)
                            (taken-in-direction seats (- i 1) (- j 1) dec dec)
                            (taken-in-direction seats (+ i 1) (+ j 1) inc inc)
                            (taken-in-direction seats (- i 1) (+ j 1) dec inc)
                            (taken-in-direction seats (+ i 1) (- j 1) inc dec)
                            ])))

(defn- taken-in-direction [seats i j i-func j-func]
  (loop [i i
         j j]
    (let [curr-seat (get-in seats [i j])]
      (if (some {curr-seat true} [\# \L nil])
        curr-seat
        (recur (i-func i) (j-func j))))))

(defn- print-matrix [m]
  (dorun (for [x m]
           (println x)))
  m)