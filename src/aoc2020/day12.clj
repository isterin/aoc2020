(ns aoc2020.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]
            [loom.alg :as alg]
            [loom.alg-generic :as lag]))

(declare calculate follow move move-forward turn move-to)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        instructions (map
                       #(->> % (re-matches #"(\w)(\d+)") (drop 1))
                       lines)
        instructions' (map (fn [[dir unit]] [dir (Integer/parseInt unit)]) instructions)
        position (follow instructions')]
    [(+ (Math/abs (position :east)) (Math/abs (position :north))) 2]))

(defn- follow [instructions]
  (loop [instructions instructions
         position {:north 0, :east 0, :current-dir :east}]
    (if (first instructions)
      (recur (rest instructions) (move (first instructions) position))
      position)))

(defn- move [[dir unit] position]
  ;(println position)
  (let [new-pos (case dir
                  "F" (move-forward unit position)
                  ("L" "R") (turn dir unit position)
                  ("E" "W" "S" "N") (move-to dir unit position))]
    ;(println new-pos)
    new-pos))

(defn- move-forward [unit position]
  ;(println "move-forward" unit)
  (case (position :current-dir)
    :north (update position :north + unit)
    :south (update position :north - unit)
    :east (update position :east + unit)
    :west (update position :east - unit)))

(defn- turn [dir unit position]
  ;(println "turn" dir unit)
  (let [circle (take 8 (cycle [:east :south :west :north]))
        circle' (if (= dir "R") circle (reverse circle))
        circle'' (drop-while #(not= % (position :current-dir)) circle')]
    (assoc position :current-dir (first (drop (/ unit 90) circle'')))))

(defn- move-to [dir unit position]
  ;(println "move-to" dir unit)
  (case dir
    "N" (update position :north + unit)
    "S" (update position :north - unit)
    "E" (update position :north + unit)
    "W" (update position :north - unit)))

