#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_uv_count
select
    daycount.do_dt,
    daycount.day_ct,
    wkcount.wk_ct,
    mncount.mn_ct,
    if('$do_date'=date_add(next_day('$do_date','monday'),-1),'Y','N'),
    if('$do_date'=last_day('$do_date'),'Y','N')
from
(
    select 
        '$do_date' do_dt,
        count(*) day_ct
    from ${db}.dws_uv_detail_day
    where dt='$do_date'
)daycount join
(
    select 
        '$do_date' do_dt,
        count(*) wk_ct
    from ${db}.dws_uv_detail_wk
    where wk_dt=concat(date_add(next_day('$do_date','monday'),-7),'_',date_add(next_day('$do_date','monday'),-1))
)wkcount on daycount.do_dt=wkcount.do_dt
join
(
    select 
        '$do_date' do_dt,
        count(*) mn_ct
    from ${db}.dws_uv_detail_mn
    where mn=date_format('$do_date','yyyy-MM')
)mncount on daycount.do_dt=mncount.do_dt;
"

$hive -e "$sql"
