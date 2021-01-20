(ns aoc2020.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [aoc2020.core :as core]))

(declare create-seat-from find-needle divide is-missing-seat?)

(defrecord Seat [spec row col seat-num])

(def max-seats (+ (* 127 8) 7))

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        seats (map create-seat-from lines)
        existing-seat-numbers (set (map #(:seat-num %) seats))
        allowable-seats (set (range 8 (- max-seats 7)))
        missing-seats (set/difference allowable-seats existing-seat-numbers)
        your-missing-seat (filter #(is-missing-seat? existing-seat-numbers %) missing-seats)]
    [(apply max-key :seat-num seats)
     (first your-missing-seat)]))

(defn- is-missing-seat? [existing-seat-numbers seat]
  (let [exists-lower (contains? existing-seat-numbers (- seat 1))
        exists-higher (contains? existing-seat-numbers (+ seat 1))]
    (and exists-lower exists-higher)))

(defn- create-seat-from [spec]
  (let [row-specifier (subs spec 0 7)
        col-specifier (subs spec 7)
        row (find-needle row-specifier (vec (range 0 128)) \B)
        col (find-needle col-specifier (vec (range 0 8)) \R)]
    (map->Seat {:spec spec :row row :col col :seat-num (+ (* row 8) col)})))

(defn- find-needle [specifiers haystack upper-half]
  (if (> (count haystack) 1)
    (find-needle (rest specifiers) (divide haystack (first specifiers) upper-half) upper-half)
    (first haystack)))

(defn- divide [haystack specifier upper-half]
  (let [middle (/ (count haystack) 2)]
    (if (= specifier upper-half)
      (subvec haystack middle)
      (subvec haystack 0 middle))))
