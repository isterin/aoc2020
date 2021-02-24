(ns aoc2020.day13
  (:require [clojure.string :as str]
            [aoc2020.core :as core]
            [clojure.set :as set]
            [clojure.math.numeric-tower :as math]))

(declare calculate next-earliest wait min-time calc-time create-schedule)

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        time (Integer/parseInt (first lines))
        buses (str/split (second lines) #",")
        buses' (->> buses (filter #(not= % "x")) (map #(Integer/parseInt %)))
        earliest (apply min-key :next-time (next-earliest time buses'))
        schedule (create-schedule buses)]
    [(* (:bus earliest) (- (:next-time earliest) time))
     (min-time schedule)]))

(defn- next-earliest [time buses]
  (for [bus buses
        :let [last-time (- time (mod time bus))
              next-time (+ last-time bus)]]
    {:bus bus :next-time next-time}))

(defn- create-schedule [buses]
  (let [buses-with-rem (->> buses
                            (map-indexed (fn [idx b] [b idx]))
                            (filter (fn [[b idx]] (not= b "x")))
                            (map (fn [[b idx]] {:bus (Integer/parseInt b) :time idx})))]
    buses-with-rem))

(defn- min-time [schedule]
  "Schedule: [[time-offset bus-id] [time-offset bus-id]]"
  (loop [time 0
         step 1
         schedule schedule]
    (println "TIME" time "STEP" step "SCHEDULE" schedule)
    (if (empty? schedule)
      time
      (recur (calc-time (first schedule) time step) (* step (->> schedule first :bus)) (rest schedule)))))

(defn- calc-time [{bus :bus time-offset :time} time step]
  (loop [time time]
    (let [w (wait bus (+ time time-offset))]
      (if (= w 0)
        time
        (recur (+ time step))))))

(defn- wait [bus t]
  (let [rem (mod t bus)]
    (if (= rem 0)
      0
      (- bus rem))))