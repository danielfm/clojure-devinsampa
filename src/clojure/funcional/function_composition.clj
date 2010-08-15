(defn divisible? [n d]
  (zero? (rem n d)))

(defn is-even? [n]
  (divisible? n 2))

(println (filter is-even? (range 10)))

(println (filter (complement is-even?) (range 10)))
