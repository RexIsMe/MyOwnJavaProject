#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_user_retention_day_count
select
    create_date,
    retention_day,
    count(*)
from ${db}.dws_user_retention_day
where dt='$do_date'
group by create_date,retention_day
"

$hive -e "$sql"
