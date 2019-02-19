custdata = load 'tempcust.txt' using PigStorage(',') as (custId:chararray,fn:chararray,ln:chararray,age:int,prof:chararray);


fildata = foreach custdata generate fn,ln;
dump fildata;
