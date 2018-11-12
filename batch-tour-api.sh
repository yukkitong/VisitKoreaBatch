#!/bin/bash

today=$(date +%Y-%m-%d)
yesterday=$(date +%Y%m%d -d '1 day ago')
filename="fetch-data_${today}.json"

java -jar VisitKoreaBatch-1.0-SNAPSHOT.jar update --dates=${yesterday} --output=${filename} &
