#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
insert into table ${db}.ads_back_count
select
    '$do_date',
    concat(date_add(next_day('$do_date','monday'),-7),'_',date_add(next_day('$do_date','monday'),-1)),
    count(*)
from
(
    select 
        current_wk_alive.mid_id
    from
    (
        select 
            mid_id
        from ${db}.dws_uv_detail_wk
        where wk_dt=concat(date_add(next_day('$do_date','monday'),-7),'_',date_add(next_day('$do_date','monday'),-1))
    )current_wk_alive
    left join
    (
        select 
            mid_id
        from ${db}.dws_uv_detail_wk
        where wk_dt=concat(date_add(next_day('$do_date','monday'),-2*7),'_',date_add(next_day('$do_date','monday'),-1-7))
    )last_wk_alive on current_wk_alive.mid_id=last_wk_alive.mid_id
    left join
    (
        select
            mid_id
        from ${db}.dws_new_mid_day
        where create_date>=date_add(next_day('$do_date','monday'),-7)
        and create_date<=date_add(next_day('$do_date','monday'),-1)
    )current_wk_new on current_wk_alive.mid_id=current_wk_new.mid_id
    where last_wk_alive.mid_id is null
    and current_wk_new.mid_id is null
)dws_user_back_wk
"

$hive -e "$sql"
