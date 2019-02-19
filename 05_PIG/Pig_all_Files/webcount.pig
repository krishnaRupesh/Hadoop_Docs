records = LOAD 'webcount.txt' using PigStorage(' ') as  (projectname:chararray, pagename:chararray, pagecount:int,pagesize:int);

filtered_records = FILTER records by projectname=='en';

grouped_records = GROUP filtered_records by pagename;  

results = FOREACH grouped_records GENERATE group,SUM(filtered_records.pagecount) ;

sorted_result = ORDER results by $1 desc;

dump sorted_result;