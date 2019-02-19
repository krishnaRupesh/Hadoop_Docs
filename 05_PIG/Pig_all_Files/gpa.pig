




students = LOAD 'gpa.dat' USING PigStorage(',') AS (name:chararray, term:chararray, gpa:float);

grpstd = GROUP students BY name;

avggpa = FOREACH grpstd GENERATE group as stdName, AVG(students.gpa) as avgGPA;

dump avggpa;
