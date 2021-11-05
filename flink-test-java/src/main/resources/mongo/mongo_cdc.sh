

！！！！ 基于mongo的测试数据

-- 添加一条 10块钱
db.order.insert({"_id":1,"create_time":"2021-01-01 00:00:01","amount":10,"user_id":1,"vender_id":1})

-- 添加一条  15块钱     总共    10+15 = 25
db.order.insert({"_id":2,"create_time":"2021-01-01 00:00:01","amount":15,"user_id":1,"vender_id":1})

-- 更新第一条   10块减少为 5元   总共   5+15 = 20
db.order.update({'_id':1},{$set:{'amount':'5'}})

-- 删除第二条  则 20 - 15 = 5 元  大屏最终结果 5元！

db.order.remove({'_id':2})

-- 最终结果，完全一致！

-- CDC 牛逼！！！