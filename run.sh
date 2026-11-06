#!/bin/bash
set -e

echo "=== Compiling Mini Trading Platform (CO2039 - Assignment 1) ==="
mkdir -p bin
javac -d bin src/*.java

echo "=== Running Demonstration Application ==="
java -cp bin Main
