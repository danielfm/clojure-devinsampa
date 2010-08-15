(ns number-generator
  (:use util)
  (:import [java.util Random]))

; Shared reference
(def counter (atom 0))

; The following function might get called several times if two or more threads
; try to swap the atom value at the same time

(defn slow-inc
  "Slowly increments n."
  [n]
  (thread-println "Current number:" n)
  (thread-println "Let's sleep for a while, and then increment the counter...")
  (Thread/sleep (* 1000 (.nextInt (Random.) 3)))
  (thread-println "Incrementing counter...")
  (inc n))

(defn get-next []
  "Function to be called by our threads."
  (swap! counter slow-inc)
  (thread-println "Counter incremented:" @counter))

; Starts 3 threads to fight for that shared resource
(dotimes [_ 3]
  (.start (Thread. get-next)))
