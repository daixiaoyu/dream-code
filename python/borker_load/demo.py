#!/usr/bin/python3
import time,pymysql,sys
def broker_load(host_param,user_param,passwd_param,db_param,data_infile,sql_with,biz_key,check_interval):
    datetime=time.strftime('%Y%m%d%H%M%S',time.localtime())
    label = db_param + "_" + biz_key + "_" + str(datetime)
    sql_load = "LOAD LABEL " + label
    sql_execute=sql_load +" ( "+ data_infile +" ) "+ sql_with
    print('本次导数SQL:------>   '+sql_execute)
    conn=pymysql.connect(host=host_param,user=user_param,passwd=passwd_param,db=db_param,port=9030,charset="utf8",autocommit=True)
    cursor=conn.cursor(pymysql.cursors.DictCursor)
    cursor.execute("use "+db_param+"")
    cursor.execute(sql_execute)
    check_times=0
    sql_check="show load where label=\'"+label+"\'; "
    print('开始check结果: ------>  '+ sql_check)
    while True:
            rs=check(datetime,host_param,user_param,passwd_param,db_param,sql_check)
            if rs == 'FINISHED':
                print('导入成功')
                break
            elif rs == 'PENDING' or rs == 'LOADING':
                check_times=check_times+1
                print("数据加载中,当前check次数:"+ str(check_times))
                time.sleep(check_interval)
                continue
            elif rs == 'CANCELLED':
                print('作业失败，脚本以失败方式退出')
                sys.exit(1)
                break
            else:
                pass
    cursor.close()
    conn.close()
def check(datetime,host1,user1,passwd1,db_param,sql_check):
    rs=""
    conn=pymysql.connect(host=host1,user=user1,passwd=passwd1,db=db_param,port=9030,charset="utf8",autocommit=True)
    cursor=conn.cursor(pymysql.cursors.DictCursor)
    cursor.execute(sql_check)
    result=cursor.fetchall()
    for i in result:
            rs=i['State']
    cursor.close()
    conn.close()
    return rs
def main():
    host='prod-dc-bd-doris-es-sh-4-2'
    user='doris'
    passwd='Y2TNXgxCcS6GP6mrm397'
    db='dws'
    biz_key='material_month'
    check_interval=10

    data_infile = "  DATA INFILE('hdfs://analyse/data/user/hive/warehouse/dws.db/dws_mk_customer_month_v2_i_d/month=${dt}/*') INTO TABLE dws_mk_customer_month_v2 FORMAT AS 'parquet' , DATA INFILE('hdfs://analyse/data/user/hive/warehouse/dws.db/dws_mk_material_month_v2_i_d/month=${dt}/*') INTO TABLE dws_mk_material_month_v2 FORMAT AS 'parquet' , DATA INFILE('hdfs://analyse/data/user/hive/warehouse/dws.db/dws_mk_material_task_month_v2_i_d/month=${dt}/*') INTO TABLE dws_mk_material_task_month_v2 FORMAT AS 'parquet' , DATA INFILE('hdfs://analyse/data/user/hive/warehouse/dws.db/dws_mk_material_customer_month_v2_i_d/month=${dt}/*') INTO TABLE dws_mk_material_customer_month_v2 FORMAT AS 'parquet' "
    sql_with = " WITH BROKER 'broker_name'  (  'username' = 'DATA',  'password' = ''  )  PROPERTIES  (  'timeout' = '3600'  );"

    broker_load(host,user,passwd,db,data_infile,sql_with,biz_key,check_interval)
if __name__ == "__main__":
    main()