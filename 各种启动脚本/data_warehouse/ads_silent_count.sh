#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into ${db}.ads_silent_count
select 
    '$do_date',
    count(*)
from
(
    select
        mid_id
    from ${db}.dws_uv_detail_day
    group by mid_id
    having count(*)=1
    and max(dt)<=date_add('$do_date',-7)
)dws_user_silent_day
"
$hive -e "$sql"
