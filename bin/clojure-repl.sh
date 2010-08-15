#!/bin/sh

# Starts a Clojure REPL

java -cp lib/clojure-1.2.0-RC3.jar:lib/clojure-contrib-1.2.0-RC3.jar:lib/jline-0.9.94.jar jline.ConsoleRunner clojure.main
