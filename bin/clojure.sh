#!/bin/sh

# Starts a Clojure

export SRCPATH=.:src/java:src/clojure:src/clojure/concorrencia
export LIBPATH=lib/clojure-1.2.0-RC3.jar:lib/clojure-contrib-1.2.0-RC3.jar:lib/jline-0.9.94.jar

java -cp $SRCPATH:$LIBPATH jline.ConsoleRunner clojure.main $@
