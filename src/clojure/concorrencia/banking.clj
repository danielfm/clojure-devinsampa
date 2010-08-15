(ns banking
  (:use util)
  (:import [java.util Random]))

; Account struct
(defstruct account :id :active :balance)

; Two bank accounts
(def ac1 (ref (struct account "c1" true 100)))
(def ac2 (ref (struct account "c2" true 100)))

; Banking operations
(defn withdraw
  "Withdraw amount from account."
  [account-ref amount]
  (let [balance (:balance @account-ref)]
    (Thread/yield)
    (alter account-ref assoc :balance (- balance amount))))

(defn deposit
  "Deposit amount to account."
  [account-ref amount]
  (let [balance (:balance @account-ref)]
    (Thread/yield)
    (alter account-ref assoc :balance (+ balance amount))))

(defn transfer
  "Transfer amount from one account to another."
  [from-ref to-ref amount]
  (thread-println "Transfering" amount "from" @from-ref "to" @to-ref)
  (withdraw from-ref amount)
  (Thread/yield)
  (thread-println "Withdrawed" amount "from account. Result:" @from-ref)
  (deposit to-ref amount)
  (Thread/yield)
  (thread-println "Deposited" amount "to account. Result:" @to-ref))

; The following dosync block might get called several times if two or more
; threads try to alter th refs' values at the same time

(defn transfer-thread []
  (try
   (dosync
    (thread-println "Sleeping for a while...")
    (thread-println "Starting transaction...")
    (thread-println "Before:" @ac1 @ac2)
    (transfer ac1 ac2 10)
    (thread-println "Commiting transaction..."))
   (catch Exception e
     (do
       (thread-println "Thrown exception:" (.getMessage e)))))
  (thread-println "After:" @ac1 @ac2))
 
(dotimes [_ 3]
  (.start (Thread. transfer-thread)))

(dotimes [_ 10]
  (thread-println "Spying accounts from a different thread:" @ac1 @ac2)
  (Thread/yield))
