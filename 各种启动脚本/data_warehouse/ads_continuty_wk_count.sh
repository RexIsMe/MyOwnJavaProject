#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_continuity_wk_count
select 
    '$do_date',
    concat(date_add(next_day('$do_date','monday'),-21),'_',date_add(next_day('$do_date','monday'),-1)),
    count(*)
from
(
    select 
        mid_id
    from ${db}.dws_uv_detail_wk
    where wk_dt>=concat(date_add(next_day('$do_date','monday'),-21),'_',date_add(next_day('$do_date','monday'),-15))
    and wk_dt<=concat(date_add(next_day('$do_date','monday'),-7),'_',date_add(next_day('$do_date','monday'),-1))
    group by mid_id
    having count(*)=3
)dws_continuity_wk
"

$hive -e "$sql"
