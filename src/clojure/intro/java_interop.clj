(import '[java.util Calendar])

(def cal (doto (Calendar/getInstance)
           (.set Calendar/YEAR 1985)
           (.set Calendar/MONTH Calendar/MAY)
           (.set Calendar/DAY_OF_MONTH 1)))
           
(println (.getTime cal))
