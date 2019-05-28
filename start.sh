#!/bin/sh

sudo docker build . --tag rtone && sudo docker run rtone
