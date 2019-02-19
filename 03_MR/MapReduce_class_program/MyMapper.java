import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
	
	
	//0, welcome to hadoop learning
	//26, hadoop learining made easy
	Text word = new Text();
	IntWritable one = new IntWritable(1);
	public void map(LongWritable key, Text value, Context con)throws IOException, InterruptedException{
		
		// Business logic
		
		StringTokenizer itr = new StringTokenizer(value.toString());
		
		while(itr.hasMoreElements()){
			word.set(itr.nextToken());
			con.write(word, one);
		}
	}
	
	//welcome,1
	//to,1
	//hadoop,1
	//learning,1
	//hadoop,1
	//learining,1
	//made,1
	//easy,1

}
