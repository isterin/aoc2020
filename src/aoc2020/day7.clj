(ns aoc2020.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [loom.graph :as graph]
            [loom.attr :as attr]))

(declare parse-nodes-into-dag add-nodes-to predecessors successors repeat-successors)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        dag (parse-nodes-into-dag lines)
        all-parents (set (predecessors dag "shiny gold"))
        successor-count (successors dag "shiny gold")]
    [(count all-parents) successor-count]))


(defn- parse-nodes-into-dag [lines]
  (loop [lines lines
         dag (graph/digraph)]
    (if (first lines)
      (recur (rest lines) (add-nodes-to dag (first lines)))
      dag)))

(defn- add-nodes-to [dag l]
  (let [root (re-find #"^\w+ \w+" l)
        parsed-children (re-seq #"(\d+) (.+?) bags?" l)
        children (map (fn [c]
                        (let [n (c 1)
                              color (c 2)]
                          {:name color :num (Integer/parseInt n)})) parsed-children)]
    (loop [dag dag
           children children]
      (if (first children)
        (let [node (first children)
              g (graph/add-edges dag [root (node :name)])
              g2 (attr/add-attr g [root (node :name)] :num (node :num))]
          (recur g2 (rest children)))
        dag))))

(defn- predecessors [g n]
  (loop [nodes [n]
         parents []
         visited #{}]
    (if (first nodes)
      (if (visited (first n))
        (recur (rest nodes) parents visited)
        (let [node (first nodes)
              predecessor (graph/predecessors g node)
              new-nodes (vec (concat (rest nodes) predecessor))
              new-parents (vec (concat parents predecessor))
              new-visited (conj #{} node)]
          (recur new-nodes new-parents new-visited)))
      parents)))


(defn- successors [graph node]
  (loop [nodes [node]
         child-count -1]
    (if (first nodes)
      (let [node (first nodes)
            succ (graph/successors graph node)
            new-nodes (vec (concat (rest nodes) (flatten (repeat-successors graph node succ))))]
        (recur new-nodes (+ child-count 1)))
      child-count)))

(defn- repeat-successors [graph node succ]
  (for [s succ
        :let [successors (vec (repeat (attr/attr graph [node s] :num) s))]]
    successors))