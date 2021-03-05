(ns aoc2020.day14
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]
            [clojure.math.combinatorics :as combo]))

(declare calculate process num-to-bin accumulate
         apply-mask to-mask combinations update-mask
         accumulate2 apply-location-mask)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        mem-vec (process lines accumulate)
        mem-vec2 (process lines accumulate2)]
    [(apply + (vals mem-vec))
     (apply + (vals mem-vec2))]))

(defn- process [lines f-acc]
  (loop [lines lines
         mask ""
         acc {}]
    (if (not-empty lines)
      (if (str/starts-with? (first lines) "mask =")
        (recur (rest lines) (to-mask (first lines)) acc)
        (recur (rest lines) mask (f-acc acc (first lines) mask)))
      acc)))

(defn- to-mask [str]
  (let [[_ mask] (re-matches #"mask = (\w+)" str)]
    mask))

(defn- num-to-bin [num]
  (let [bin (Long/toBinaryString num)
        bin (format "%36s" bin)
        bin (str/replace bin #"\s" "0")]
    bin))


;;  Part 1
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


;; Part 2
(defn- accumulate2 [acc line mask]
  (let [[_ location num] (re-matches #"mem\[(\d+)\] = (\d+)" line)
        location' (apply-location-mask (num-to-bin (Long/parseLong location)) mask)]
    (loop [masks (vec (combinations location'))
           acc acc]
      (if (nil? (first masks))
        acc
        (let [m (first masks)
              masked-loc (apply-mask location' m)
              masked-loc' (Long/parseLong masked-loc 2)]
          (recur (rest masks) (assoc acc masked-loc' (Long/parseLong num))))))))


(defn- apply-location-mask [bin mask]
  (apply str (map
               (fn [a b] (if (= b \0) (str a) (str b)))
               bin mask)))

(defn- combinations [mask]
  (let [xs (filter #(= \X (second %)) (map-indexed (fn [idx item] [idx item]) mask))
        xs' (map (fn [[idx item]] [[idx 0] [idx 1]]) xs)
        combos (apply combo/cartesian-product xs')]
    (map #(update-mask (vec mask) %) combos)))

(defn- update-mask [mask combos]
  (let [mask-vec (reduce (fn [a c] (assoc a (first c) (Character/forDigit (second c) 10)))
                         (vec mask)
                         combos)]
    (apply str mask-vec)))