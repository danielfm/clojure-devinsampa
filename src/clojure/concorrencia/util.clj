(ns util)

(def output-lock (Object.))

(defn thread-name
  "Returns the current thread name."
  []
  (str "[" (.getName (Thread/currentThread)) "] - "))

(defn thread-println
  "Prints the thread name along with args."
  [& args]
  (let [x (cons (thread-name) args)]
    (locking output-lock
      (apply println x))))
