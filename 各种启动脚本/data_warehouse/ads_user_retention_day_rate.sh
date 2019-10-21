#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_user_retention_day_rate
select
    '$do_date',
    ur.create_date,
    ur.retention_day,
    ur.retention_count,
    nm.new_mid_count,
    ur.retention_count/nm.new_mid_count*100
from ${db}.ads_new_mid_count nm
join ${db}.ads_user_retention_day_count ur
on nm.create_date=ur.create_date
where date_add(ur.create_date,ur.retention_day)='$do_date'
"

$hive -e "$sql"
