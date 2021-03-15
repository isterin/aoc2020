(ns aoc2020.day15)

(defn calculate [input n]
  ; We initialize the vector, since appending to it becomes a performance bottleneck for large n
  (loop [lst (apply vector-of :int (concat input (repeat (- n (count input)) 0)))
         idx (dec (count input))
         cache (into {} (map-indexed (fn [idx itm] [itm idx]) (drop-last input)))]
    (if (< idx (dec n))
      (let [i (get cache (lst idx))
            curr (if i (- idx i) 0)
            cache (assoc cache (lst idx) idx)]
        (recur (assoc lst (inc idx) curr) (inc idx) cache))
      (last lst))))