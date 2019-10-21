#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
set hive.exec.dynamic.partition.mode=nonstrict;
insert overwrite table ${db}.dws_uv_detail_wk partition(wk_dt)
select
    mid_id,
    concat_ws('|', collect_set(user_id)) user_id,
    concat_ws('|', collect_set(version_code)) version_code,
    concat_ws('|', collect_set(version_name)) version_name,
    concat_ws('|', collect_set(lang)) lang,
    concat_ws('|', collect_set(source)) source,
    concat_ws('|', collect_set(os)) os,
    concat_ws('|', collect_set(area)) area, 
    concat_ws('|', collect_set(model)) model,
    concat_ws('|', collect_set(brand)) brand,
    concat_ws('|', collect_set(sdk_version)) sdk_version,
    concat_ws('|', collect_set(gmail)) gmail,
    concat_ws('|', collect_set(height_width)) height_width,
    concat_ws('|', collect_set(app_time)) app_time,
    concat_ws('|', collect_set(network)) network,
    concat_ws('|', collect_set(lng)) lng,
    concat_ws('|', collect_set(lat)) lat,
    date_add(next_day('$do_date','monday'),-7),
    date_add(next_day('$do_date','monday'),-1),
    concat(date_add(next_day('$do_date','monday'),-7),'_',date_add(next_day('$do_date','monday'),-1))
from ${db}.dws_uv_detail_day
where dt>=date_add(next_day('$do_date','monday'),-7)
and dt<=date_add(next_day('$do_date','monday'),-1)
group by mid_id;
"

$hive -e "$sql"
