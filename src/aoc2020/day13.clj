(ns aoc2020.day13
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]))

(declare calculate next-earliest)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        time (Integer/parseInt (first lines))
        buses (str/split (second lines) #",")
        buses' (->> buses (filter #(not= % "x")) (map #(Integer/parseInt %)))
        earliest (apply min-key :next-time (next-earliest time buses'))]
    [(* (:bus earliest) (- (:next-time earliest) time))
     2]))

(defn- next-earliest [time buses]
  (for [bus buses
        :let [last-time (- time (mod time bus))
              next-time (+ last-time bus)]]
    {:bus bus :next-time next-time}))