#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_new_mid_count
select 
    create_date,
    count(*)
from ${db}.dws_new_mid_day
where create_date='$do_date'
group by create_date;
"

$hive -e "$sql"
