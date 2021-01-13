(ns aoc2020.core
  (:require [clojure.java.io :as io]))

(defn read-resource-file [input]
  (with-open [rdr (io/reader (io/resource input))]
    (doall (line-seq rdr))))