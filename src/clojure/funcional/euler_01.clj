(defn divisible? [n coll]
  (some #(zero? (rem n %)) coll))

(println (divisible? 8 [3 5]))

(println (divisible? 10 [3 5]))

(println
  (reduce +
    (filter #(divisible? % [3 5]) (range 1000))))
