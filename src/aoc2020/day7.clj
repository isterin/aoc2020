(ns aoc2020.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [loom.graph :as graph]
            [loom.attr :as attr]
            [loom.alg :as alg]))

(declare parse-nodes-into-dag add-nodes-to predecessors successors repeat-successors)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        dag (parse-nodes-into-dag lines)
        all-parents (set (predecessors dag "shiny gold"))
        successor-count (successors dag "shiny gold")]
    (println successor-count)
    ;(print (apply + (flatten successor-count)))
    (count all-parents)))


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
    ;(println "NODE:" (first nodes) "REST: " (count nodes) "PARENTS: " parents "VISITED: " visited)
    (if (first nodes)
      (if (visited (first n))
        (recur (rest nodes) parents visited)
        (let [n (first nodes)
              pred (graph/predecessors g n)
              new-nodes (vec (concat (rest nodes) pred))
              new-parents (vec (concat parents pred))
              new-visited (conj #{} n)]
          ;(println (attr/attr g [(first pred) n] :num))
          (recur new-nodes new-parents new-visited)))
      parents)))


(defn- __successors [g n]
  (for [path (alg/bf-span g "shiny gold")
        :let [[root children] path
              ct (map #(or (attr/attr g [root %] :num) 0) children)]]
    ct))

(defn- successors [g n]
  (loop [nodes [n]
         child-count 0]
    (if (first nodes)
      (let [n (first nodes)
            succ (graph/successors g n)
            new-nodes (vec (concat (rest nodes) (flatten (repeat-successors g n succ))))]
        ;(println n)
        (println n (flatten (repeat-successors g n succ)))
        (recur new-nodes (+ child-count 1)))
      child-count)))

(defn- repeat-successors [g n succ]
  (for [s succ
        :let [successors (vec (repeat (or (attr/attr g [n s] :num) 1) s))]]
    successors))