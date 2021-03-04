(ns aoc2020.day14
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]))

(declare calculate process num-to-bin accumulate apply-mask to-mask)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        mem-vec (process lines)]
    [(apply + (vals mem-vec))
     0]))

(defn- process [lines]
  (loop [lines lines
         mask ""
         acc {}]
    (if (not-empty lines)
      (if (str/starts-with? (first lines) "mask =")
        (recur (rest lines) (to-mask (first lines)) acc)
        (recur (rest lines) mask (accumulate acc (first lines) mask)))
      acc)))


(defn- accumulate [acc line mask]
  (let [[_ location num] (re-matches #"mem\[(\d+)\] = (\d+)" line)
        bin (num-to-bin (Long/parseLong num))
        masked (apply-mask bin mask)
        masked' (Long/parseLong masked 2)]
    (assoc acc (Integer/parseInt location) masked')))

(defn- apply-mask [bin mask]
  (apply str (map
               (fn [a b] (if (= b \X) (str a) (str b)))
               bin mask)))

(defn- to-mask [str]
  (let [[_ mask] (re-matches #"mask = (\w+)" str)]
    mask))

(defn- num-to-bin [num]
  (let [bin (Long/toBinaryString num)
        bin (format "%36s" bin)
        bin (str/replace bin #"\s" "0")]
    bin))