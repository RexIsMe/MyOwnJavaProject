#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert overwrite table ${db}.dwd_base_event_log partition(dt='$do_date')
select
    ${db}.base_analizer(line,'mid') as mid_id,
    ${db}.base_analizer(line,'uid') as user_id,
    ${db}.base_analizer(line,'vc') as version_code,
    ${db}.base_analizer(line,'vn') as version_name,
    ${db}.base_analizer(line,'l') as lang,
    ${db}.base_analizer(line,'sr') as source,
    ${db}.base_analizer(line,'os') as os,
    ${db}.base_analizer(line,'ar') as area,
    ${db}.base_analizer(line,'md') as model,
    ${db}.base_analizer(line,'ba') as brand,
    ${db}.base_analizer(line,'sv') as sdk_version,
    ${db}.base_analizer(line,'g') as gmail,
    ${db}.base_analizer(line,'hw') as height_width,
    ${db}.base_analizer(line,'t') as app_time,
    ${db}.base_analizer(line,'nw') as network,
    ${db}.base_analizer(line,'ln') as lng,
    ${db}.base_analizer(line,'la') as lat,
    event_name,
    event_json,
    ${db}.base_analizer(line,'st') as server_time
from ${db}.ods_event_log lateral view ${db}.flat_analizer(${db}.base_analizer(line,'et')) tmp_tbl as event_name,event_json
where dt='$do_date';
"

$hive -e "$sql"

