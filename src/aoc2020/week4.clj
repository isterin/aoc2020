(ns aoc2020.week4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc2020.core :as core]))

(declare create-cards create-card validate-card)

(def required-fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(defn calculate [input]
  (let [lines (core/read-resource-file input)
        cards (create-cards lines)
        valid-field-cards (filter #(every? %1 required-fields) cards)
        valid-cards (filter #(validate-card %) valid-field-cards)]
    [(count valid-field-cards) (count valid-cards)]))

(defn- create-cards [lines]
  (loop [lines lines
         card []
         cards []]
    (if (empty? lines)
      (conj cards (create-card (str/join " " card)))
      (let [l (first lines)
            new-card (if (empty? l)
                       []
                       (conj card l))
            new-cards (if (empty? l)
                        (conj cards (create-card (str/join " " card)))
                        cards)]
        (recur (rest lines) new-card new-cards)))))

(defn- create-card [card]
  (let [fields (str/split card #"\s")
        field-tuples (for [f fields] (str/split f #":"))
        card-map (into {} (map (fn [[k v]] [(keyword k) v]) field-tuples))]
    card-map))

(defn- validate-card [card]
  (let [byr (Integer/parseInt (card :byr))
        iyr (Integer/parseInt (card :iyr))
        eyr (Integer/parseInt (card :eyr))
        [_ _hgt hgtu] (re-matches #"(\d+)(\w+)" (card :hgt))
        hgt (Integer/parseInt _hgt)
        hcl (card :hcl)
        ecl (card :ecl)
        pid (card :pid)]
    (and
      (and (>= byr 1920) (<= byr 2002))
      (and (>= iyr 2010) (<= iyr 2020))
      (and (>= eyr 2020) (<= eyr 2030))
      (if (= hgtu "cm")
        (and (>= hgt 150) (<= hgt 193))
        (and (>= hgt 59) (<= hgt 76)))
      (re-matches #"#[0-9a-f]{6}" hcl)
      (some #{ecl} ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"])
      (re-matches #"\d{9}" pid))))