#!/bin/bash

function print_usage(){
cat << EOF
	zk.sh USAGE
	start : start ZK
	stop : stop ZK
	status : view ZK status
EOF
}

case $1 in
	start)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "============ $host ============="
			ssh $host "source /etc/profile; /opt/module/zookeeper-3.4.10/bin/zkServer.sh start"
		done
	;;
	stop)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "============ $host ============="
			ssh $host "source /etc/profile; /opt/module/zookeeper-3.4.10/bin/zkServer.sh stop"
		done
	;;
	status)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "============ $host ============="
			ssh $host "source /etc/profile; /opt/module/zookeeper-3.4.10/bin/zkServer.sh status"
		done
	;;
	*)
	print_usage
	;;
esac
