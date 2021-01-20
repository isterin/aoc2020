(ns aoc2020.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]))

(declare create-group-answers)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        group-answers-any (create-group-answers lines (partial apply conj))
        group-answers-all (create-group-answers lines set/intersection)]
    [(apply + (map count group-answers-any))
     (apply + (map count group-answers-all))]))

(defn- create-group-answers [lines apply-set-func]
  (loop [lines lines
         groups [nil]]
    (if (empty? lines)
      groups
      (let [line (str/trim (first lines))
            current-group (last groups)
            line-set (set (seq line))
            current-group-set (if (nil? current-group)
                                line-set
                                current-group)]
        (if (empty? line)
          (recur (rest lines) (conj groups nil))
          (recur (rest lines) (assoc groups (- (count groups) 1) (apply-set-func current-group-set line-set))))))))