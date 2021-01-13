(ns aoc2020.week3
  (:require [clojure.java.io :as io]
            [aoc2020.core :as core]))

(declare navigate count-trees-amongst create-slope-rule calc-counts-for)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        area (map #(cycle %) lines)
        count-func (partial calc-counts-for area)
        tree-count (first (count-func (create-slope-rule 3 1)))
        slope-rules [(create-slope-rule 1 1)
                     (create-slope-rule 3 1)
                     (create-slope-rule 5 1)
                     (create-slope-rule 7 1)
                     (create-slope-rule 1 2)]
        tree-counts (apply count-func slope-rules)]
    [tree-count (apply * tree-counts)]))

(defn- calc-counts-for [area rule & other-rules]
  (let [rules (conj other-rules rule)]
    (for [rule rules
          :let [tree-count (count-trees-amongst (navigate area rule))]
          ]
      tree-count)))

(defn- create-slope-rule [x-move y-move]
  (fn [{x :x y :y}] {:x (+ x x-move) :y (+ y y-move)}))

(defn- count-trees-amongst [things-encountered]
  (count
    (filter #(= % \#) things-encountered)))

(defn- navigate [area advance-func]
  (loop [position {:x 0 :y 0}
         things-along-the-way []]
    (let [slope (nth area (position :y) nil)
          thing (nth slope (position :x))]
      (if slope
        (recur (advance-func position) (conj things-along-the-way thing))
        things-along-the-way))))