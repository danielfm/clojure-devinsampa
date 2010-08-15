(ns cover-art-downloader
  (:use util)
  (:require [clojure.contrib.duck-streams :as duck-streams])
  (:import [java.net URL URLConnection]
           [java.io File FileOutputStream InputStream]))

; Struct that represents a playlist entry
(defstruct entry :artist :album :title :cover-art)

; Sample playlist
(def my-playlist
     [(struct entry "AC/DC", "Back In Black", "Hells Bells")
      (struct entry "AC/DC", "Ballbreaker", "The Furor")
      (struct entry "AC/DC", "Black Ice", "Black Ice")
      (struct entry "AC/DC", "Dirty Deeds Done Dirt Cheap", "Problem Child")
      (struct entry "AC/DC", "For Those About To Rock", "C.O.D.")
      (struct entry "AC/DC", "High Voltage", "The Jack")
      (struct entry "AC/DC", "Highway To Hell", "Night Prowler")
      (struct entry "AC/DC", "Let There Be Rock", "Whole Lotta Rosie")
      (struct entry "AC/DC", "Stiff Upper Lip", "Damned")
      (struct entry "AC/DC", "TNT", "TNT")])

(defn write-to-file [stream filename]
  "Saves the stream to disk and returns it."
  (let [file (File. (str "build/covers/" filename))]
    (duck-streams/make-parents file)
    (duck-streams/copy stream file)
    file))

(defn fetch-cover-art [track]
  "Downloads the cover art for track."
  (thread-println "Downloading cover art for" track)
  (let [filename (str (.replaceAll (:album track) " " "") ".jpg")
        url (str "http://destaquenet.com/media/devinsampa/" filename)
        conn (doto (.openConnection (URL. url))
               (.setConnectTimeout 5000)
               (.setReadTimeout 5000)
               (.connect))]
    (write-to-file (.getInputStream conn) filename)))

(defn update-cover-art [track]
  "Updates the :cover-art key with the album cover art."
  (thread-println "Updating cover art for" track)
  (assoc track :cover-art (fetch-cover-art track)))

(defn handle-error [track-agent exception]
  "Restarts the agent in case of errors so sends are allowed again."
  (thread-println "Agent function raised an exception" exception)
  (restart-agent track-agent @track-agent))

(defn cover-art-agent [track]
  "Creates an agent for track and sends update-cover-art fn to be executed."
  (let [track-agent (agent track :error-handler handle-error)]
    (send-off track-agent update-cover-art)))

; Creates an agent for each track in playlist
; (doall ..) is required since (map ..) returns a lazy sequence
(thread-println "Starting agents...")
(def track-agents
     (doall (map cover-art-agent my-playlist)))

; Blocks this thread until all sends have occurred
(apply await track-agents)
(thread-println "Download finished")

; Results
(println (map deref track-agents))
(shutdown-agents)
