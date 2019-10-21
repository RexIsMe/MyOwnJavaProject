#!/bin/bash

for host in hadoop102 hadoop103
do
	echo "========== $host =========="
	ssh $host "java -jar /opt/software/logCollector-1.0-SNAPSHOT-jar-with-dependencies.jar $1 $2 1>/dev/null 2>&1"
done
