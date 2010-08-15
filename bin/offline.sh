#!/bin/sh

# Creates an offline version of this presentation

rm -rf build/offline
pydozeoff -b build/offline
