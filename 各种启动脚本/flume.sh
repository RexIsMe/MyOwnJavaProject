#!/bin/bash


case $1 in
	start)
		echo "======== hadoop102 =========="
		ssh hadoop102 "source /etc/profile; nohup /opt/module/apache-flume-1.7.0-bin/bin/flume-ng agent -n a1 -c /opt/module/apache-flume-1.7.0-bin/conf/ -f $2 1>/dev/null 2>&1 & "
	;;
	stop)
		echo "======== hadoop102 =========="
		ssh hadoop102 "ps -ef | awk '/$2/ && !/awk/ {print \$2}' | xargs kill -9"
	;;
esac
