mapload = load 'mapfile.txt' as (myMap:map[]);  
values = foreach mapload generate myMap#'name' as emp_name, myMap#'age' as emp_age, myMap#'gpa' as emp_gpa;  
value = FILTER values BY emp_name is not null;  
dump value