#!/bin/bash

case $1 in
"start"){
    for i in hadoop102 hadoop103 hadoop104
    do
        ssh $i "/opt/module/solr-5.2.1/bin/solr start"
    done
};;
"stop"){
    for i in hadoop102 hadoop103 hadoop104
    do
        ssh $i "/opt/module/solr-5.2.1/bin/solr stop"
    done
};;
esac
