#!/bin/bash

echo "converting $1 to $2"

sox "$1" -r 8000 -c 1 -s "$2"

