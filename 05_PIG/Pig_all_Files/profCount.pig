cust = load 'cust.txt' using PigStorage(',') as (custid:chararray, firstname:chararray, lastname:chararray,age:int,profession:chararray);

groupbyprofession = group cust by profession;

countbyprofession = foreach groupbyprofession generate group, COUNT(cust);

sorted_result = ORDER countbyprofession by $1 desc;

dump sorted_result;