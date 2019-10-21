#! /bin/bash
do_date=`date -d '-1 day' +%F`

if [ -n "$1" ] ;then
    do_date=$1
fi

db=gmall

hive=/opt/module/apache-hive-1.2.1-bin/bin/hive

sql="
load data inpath '/origin_data/gmall/db/order_info/$do_date' overwrite into table ${db}.ods_order_info partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/order_detail/$do_date' OVERWRITE into table ${db}.ods_order_detail partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/sku_info/$do_date' OVERWRITE into table ${db}.ods_sku_info partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/user_info/$do_date' OVERWRITE into table ${db}.ods_user_info partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/payment_info/$do_date' OVERWRITE into table ${db}.ods_payment_info partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/base_category1/$do_date' OVERWRITE into table ${db}.ods_base_category1 partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/base_category2/$do_date' OVERWRITE into table ${db}.ods_base_category2 partition(dt='$do_date');

load data inpath '/origin_data/gmall/db/base_category3/$do_date' OVERWRITE into table ${db}.ods_base_category3 partition(dt='$do_date');
"

$hive -e "$sql"
