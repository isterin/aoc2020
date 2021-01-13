(ns aoc2020.week2
  (:require [clojure.java.io :as io]
            [aoc2020.core :as core]))

(declare valid-lines valid-range? valid-position?)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        vr (valid-lines lines valid-range?)
        vp (valid-lines lines valid-position?)]
    [(count vr) (count vp)]))

(defn- valid-lines [lines validation-func]
  (for [l lines
        :when (validation-func l)]
    l))

(defn- valid-range? [line]
  (let [[_ from to c pwd] (re-matches #"(\d+)-(\d+) (\w): (.+)" line)
        lower (Integer/parseInt from)
        upper (Integer/parseInt to)
        ct (count (re-seq (re-pattern c) pwd))]
    (and (>= ct lower) (<= ct upper))))

(defn- valid-position? [line]
  (let [[_ from to c pwd] (re-matches #"(\d+)-(\d+) (\w): (.+)" line)
        first-c (str (get pwd (- (Integer/parseInt from) 1)))
        second-c (str (get pwd (- (Integer/parseInt to) 1)))
        exactly-one? (count (filter identity [(= first-c c) (= second-c c)]))]
    (= 1 exactly-one?)))