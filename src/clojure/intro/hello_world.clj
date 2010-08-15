(println (* (+ 2 4) 2))

(defn avg [coll]
  (if (empty? coll)
    0
    (/ (apply + coll) (count coll))))

(println (avg []))

(println (avg [5 4 3 2 1]))
