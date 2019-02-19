movies = LOAD 'movieData.txt' USING PigStorage(',') as (id,name,year,rating,duration);
grpd = group movies by year;
top3 = foreach grpd {
            sorted = order movies by rating desc;	
            top    = limit sorted 3;
            generate group, flatten(top);
};
dump top3;