import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SalCount {
	public static class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
		Text word = new Text();
		IntWritable one = new IntWritable(1);
		public void map(LongWritable key, Text value, Context con)throws IOException, InterruptedException{
			
			String line = value.toString();
        	String[]  strts = line.split(",");
        	int sal = Integer.parseInt(strts[4]);
        	if(sal>2500) {
        		word.set("Count :");
        		con.write(word, new IntWritable(1));
			}		
        }
	}
	//Count :	1
	//Count :	1
	//Count :	1
	//Count :	1
	//Count :	1
	//Count :	1
	
	//Count : [1,1,1,1,1,1]
	
	public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
		IntWritable  result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context con)throws IOException, InterruptedException{
			int sum = 0;
			for(IntWritable val : values ){
				sum += val.get();
			}
			result.set(sum);
			con.write(key, result);
		}
	}


	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "MySalaryCount");
	    
	    job.setJarByClass(SalCount.class);
	    
	    job.setMapperClass(MyMapper.class);
	    job.setReducerClass(MyReducer.class);

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    Path outputPath = new Path(args[1]);
	    FileOutputFormat.setOutputPath(job, outputPath);
		outputPath.getFileSystem(conf).delete(outputPath);
	    
	    
	    job.waitForCompletion(true);
	  }

}