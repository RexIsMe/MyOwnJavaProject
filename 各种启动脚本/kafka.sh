#!/bin/bash
case $1 in
	start)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "=========== $host ============"
			ssh $host "source /etc/profile; /opt/module/kafka_2.11-0.11.0.2/bin/kafka-server-start.sh  -daemon /opt/module/kafka_2.11-0.11.0.2/config/server.properties"
		done
	;;
	stop)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "=========== $host ============"
			ssh $host "source /etc/profile; /opt/module/kafka_2.11-0.11.0.2/bin/kafka-server-stop.sh"
		done
	;;
esac
