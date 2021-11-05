select * from flink.order_result;

-- 1000000
-- 1000000

select sum(order_count) from flink.order_result;

delete from flink.order_result where 1>0;



drop table flink.order_result;
create table flink.order_result(
                                   vender_id  int unsigned not null primary key,
                                   order_count bigint not null default 0,
                                   order_total_amount int not null default 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8;