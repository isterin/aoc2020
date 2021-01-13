(ns aoc2020.week1
  (:require [clojure.java.io :as io]
            [aoc2020.core :as core]))

(declare pair-for triple-for)

(defn calculate [input]
  (let [l1 (core/read-resource-file input)
        l2 (rest l1)
        l3 (rest l2)
        [a b] (first (pair-for l1 l2 2020))
        [d e f] (first (triple-for l1 l2 l3 2020))]
    (do
      [(* a b) (* d e f)])))

(defn- pair-for [l1 l2 sumto]
  (for [x l1
        y l2
        :let [z (+ (Integer/parseInt x) (Integer/parseInt y))]
        :when (= z sumto)
        ]
    (do
      [(Integer/parseInt x) (Integer/parseInt y)])))

(defn- triple-for [l1 l2 l3 sumto]
  (for [a l1
        b l2
        c l3
        :let [z (+ (Integer/parseInt a) (Integer/parseInt b) (Integer/parseInt c))]
        :when (= z sumto)
        ]
    (do
      [(Integer/parseInt a) (Integer/parseInt b) (Integer/parseInt c)])))