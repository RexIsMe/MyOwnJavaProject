#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_wastage_count
select 
    '$do_date',
    count(*)
from
(
    select
        mid_id
    from ${db}.dws_uv_detail_day
    group by mid_id
    having max(dt)<=date_add('$do_date',-7)
)dws_user_wastage_day;
"
$hive -e "$sql"
