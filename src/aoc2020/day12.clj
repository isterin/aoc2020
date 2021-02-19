(ns aoc2020.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]
            [loom.graph :as graph]
            [loom.alg :as alg]
            [loom.alg-generic :as lag]))

(declare calculate follow move forward turn move-to ->DefaultMovement ->WaypointMovement)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        instructions (map
                       #(->> % (re-matches #"(\w)(\d+)") (drop 1))
                       lines)
        instructions' (map (fn [[dir unit]] [dir (Integer/parseInt unit)]) instructions)
        position (follow instructions' (->DefaultMovement {:north 0, :east 0, :current-dir :east}))
        position2 (follow instructions' (->WaypointMovement {:north 0, :east 0,
                                                             :waypoint-north 1, :waypoint-east 10}))]
    (println position2)
    (println (+ (Math/abs (position2 :east)) (Math/abs (position2 :north))))
    [(+ (Math/abs (position :east)) (Math/abs (position :north)))
     (+ (Math/abs (position2 :east)) (Math/abs (position2 :north)))]))


(defprotocol Movement
  (forward [this unit])
  (turn [this dir unit])
  (move-to [this dir unit]))

(defn- follow [instructions movement-impl]
  (loop [instructions instructions
         movement-impl movement-impl]
    (if (first instructions)
      (recur (rest instructions) (move (first instructions) movement-impl))
      (:position movement-impl))))

(defn- move [[dir unit] movement-impl]
  (let [new-pos (case dir
                  "F" (forward movement-impl unit)
                  ("L" "R") (turn movement-impl dir unit)
                  ("E" "W" "S" "N") (move-to movement-impl dir unit))]
    new-pos))

; Default Movement implementation

(defrecord DefaultMovement [position]
  Movement
  (forward [this unit]
    ;(println "move-forward" unit)
    (case (position :current-dir)
      :north (update-in this [:position :north] + unit)
      :south (update-in this [:position :north] - unit)
      :east (update-in this [:position :east] + unit)
      :west (update-in this [:position :east] - unit)))
  (turn [this dir unit]
    (let [circle (take 8 (cycle [:east :south :west :north]))
          circle' (if (= dir "R") circle (reverse circle))
          circle'' (drop-while #(not= % (position :current-dir)) circle')]
      (assoc-in this [:position :current-dir] (first (drop (/ unit 90) circle'')))))
  (move-to [this dir unit]
    (case dir
      "N" (update-in this [:position :north] + unit)
      "S" (update-in this [:position :north] - unit)
      "E" (update-in this [:position :east] + unit)
      "W" (update-in this [:position :east] - unit))))


; Waypoint Movement Implementation
; position -> {:north :east :waypoint-north :waypoint-east}
(defrecord WaypointMovement [position]
  Movement
  (forward [this unit]
    ;(println "move-forward" unit)
    (let [this' (update-in this [:position :north] + (* (position :waypoint-north) unit))
          this'' (update-in this' [:position :east] + (* (position :waypoint-east) unit))]
      this''))
  (turn [this dir unit]
    (let [radians (Math/toRadians (if (= dir "R") (- unit) unit))
          x (-
              (* (Math/cos radians) (position :waypoint-east))
              (* (Math/sin radians) (position :waypoint-north)))
          y (+
              (* (Math/sin radians) (position :waypoint-east))
              (* (Math/cos radians) (position :waypoint-north)))]
      (-> this
          (assoc-in [:position :waypoint-east] (Math/round x))
          (assoc-in [:position :waypoint-north] (Math/round y)))))
  (move-to [this dir unit]
    (case dir
      "N" (update-in this [:position :waypoint-north] + unit)
      "S" (update-in this [:position :waypoint-north] - unit)
      "E" (update-in this [:position :waypoint-east] + unit)
      "W" (update-in this [:position :waypoint-east] - unit))))

