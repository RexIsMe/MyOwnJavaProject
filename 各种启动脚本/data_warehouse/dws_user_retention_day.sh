#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert overwrite table ${db}.dws_user_retention_day partition(dt='$do_date')
select
    nm.mid_id,
    nm.user_id , 
    nm.version_code , 
    nm.version_name , 
    nm.lang , 
    nm.source, 
    nm.os, 
    nm.area, 
    nm.model, 
    nm.brand, 
    nm.sdk_version, 
    nm.gmail, 
    nm.height_width,
    nm.app_time,
    nm.network,
    nm.lng,
    nm.lat,
    nm.create_date,
    3 retention_day
from ${db}.dws_new_mid_day nm
join ${db}.dws_uv_detail_day ud
on nm.mid_id=ud.mid_id
where nm.create_date=date_add('$do_date',-3)
and ud.dt='$do_date'
union all
select
    nm.mid_id,
    nm.user_id , 
    nm.version_code , 
    nm.version_name , 
    nm.lang , 
    nm.source, 
    nm.os, 
    nm.area, 
    nm.model, 
    nm.brand, 
    nm.sdk_version, 
    nm.gmail, 
    nm.height_width,
    nm.app_time,
    nm.network,
    nm.lng,
    nm.lat,
    nm.create_date,
    2 retention_day
from ${db}.dws_new_mid_day nm
join ${db}.dws_uv_detail_day ud
on nm.mid_id=ud.mid_id
where nm.create_date=date_add('$do_date',-2)
and ud.dt='$do_date'
union all
select
    nm.mid_id,
    nm.user_id , 
    nm.version_code , 
    nm.version_name , 
    nm.lang , 
    nm.source, 
    nm.os, 
    nm.area, 
    nm.model, 
    nm.brand, 
    nm.sdk_version, 
    nm.gmail, 
    nm.height_width,
    nm.app_time,
    nm.network,
    nm.lng,
    nm.lat,
    nm.create_date,
    1 retention_day
from ${db}.dws_new_mid_day nm
join ${db}.dws_uv_detail_day ud
on nm.mid_id=ud.mid_id
where nm.create_date=date_add('$do_date',-1)
and ud.dt='$do_date';
"
$hive -e "$sql"
