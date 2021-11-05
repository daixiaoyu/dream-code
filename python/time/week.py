import datetime


today = datetime.date.today()
yesterday = today - datetime.timedelta(days=1)

this_monday = yesterday - datetime.timedelta(yesterday.weekday())

this_monday_str = this_monday.strftime('%Y-%m-%d')

#print(this_monday,type(this_monday))
#print(this_monday_str,type(this_monday_str))

print(this_monday)
print(this_monday_str)

