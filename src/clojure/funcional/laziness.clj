(defn echo [n]
  (println n) n)

(def my-numbers
  (map echo (range 3)))

(println (doall my-numbers))

(println (doall my-numbers))
