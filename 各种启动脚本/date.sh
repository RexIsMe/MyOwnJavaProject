#!/bin/bash

var1=$1
case $var1 in
	"update")
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "============ update $host date ============="
			ssh -t  $host "source /etc/profile;sudo date -s $2"
		done
	;;
	*)
		for host in hadoop102 hadoop103 hadoop104
		do
			echo "============ sync $host date ============="
			ssh -t $host "source /etc/profile;sudo ntpdate -u ntp.api.bz"
		done
	;;
esac
