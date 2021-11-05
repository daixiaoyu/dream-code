import time
import datetime

today = datetime.date.today()


# 周开始时间
week_start_dt = today - datetime.timedelta(days=today.weekday()+7)
# 结束日期
week_end_dt = today - datetime.timedelta(days=today.weekday()+1)

print(today)

print(week_start_dt)

print(week_end_dt)

oneday=datetime.timedelta(days=1)

yesterday=today-oneday

print(yesterday)


def getYesterday():
    yesterday = datetime.date.today() + datetime.timedelta(-1)
    return yesterday
# 输出
print(getYesterday())



def get_date_of_last_week():
    """
    获取上周开始结束日期
    :return: str，date tuple
    """
    today = datetime.date.today()
    begin_of_last_week = (today - datetime.timedelta(days=today.isoweekday() + 7)).strftime('%Y-%m-%d')
    end_of_last_week = (today - datetime.timedelta(days=today.isoweekday() + 1)).strftime('%Y-%m-%d')
    return begin_of_last_week, end_of_last_week
print(get_date_of_last_week())