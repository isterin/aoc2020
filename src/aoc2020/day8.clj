(ns aoc2020.day8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [loom.graph :as graph]
            [loom.attr :as attr]))

(declare calculate parse-source-into-commands create-command-from run)

(defrecord Cmd [line cmd val])

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        program (parse-source-into-commands lines)
        a (run (vec program))]
    (println "PROGRAM" program)
    [a]))

(defn- parse-source-into-commands [lines]
  (for [[idx line] (map-indexed vector lines)
        :let [command (create-command-from line idx)]]
    command))

(defn- create-command-from [line idx]
  (let [[_ cmdstr cmdnum] (re-matches #"(\w+) ([-+]\d+)" line)
        cmd (->Cmd idx cmdstr (Integer/parseInt cmdnum))]
    cmd))

(defn- run [program]
  (loop [position 0
         acc 0
         already_run #{}]
    (let [cmd (program position)
          [new_acc new_position] (case (:cmd cmd)
                                   "jmp" [acc (+ position (:val cmd))]
                                   "acc" [(+ acc (:val cmd)) (inc position)]
                                   [acc (inc position)])
          ]
      (if (contains? already_run cmd)
        acc
        (recur new_position new_acc (conj already_run cmd))))))
