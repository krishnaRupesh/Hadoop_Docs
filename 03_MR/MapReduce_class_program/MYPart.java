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
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MYPart {
	public static class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
		Text word = new Text();
		IntWritable one = new IntWritable(1);
		public void map(LongWritable key, Text value, Context con)throws IOException, InterruptedException{
			StringTokenizer itr = new StringTokenizer(value.toString());
			while(itr.hasMoreElements()){
				word.set(itr.nextToken());
				con.write(word, one);
			}
		}
	}
	
	
	public static class MyPartitioner extends Partitioner<Text, IntWritable> {

		@Override
		public int getPartition(Text key, IntWritable value, int numPartitions) {

			String myKey = key.toString().toLowerCase();

			if (myKey.equals("edureka")|| myKey.equals("hadoop")||myKey.equals("learning")|| myKey.equals("program")) {
				return 0;  //reducer 1
			}
			if (myKey.startsWith("t")||myKey.startsWith("T")) {
				return 1;  //reducer 2
			}
			else {
				return 2;  //reducer 3
			}
		}
	}	
	
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

	public static class MyCombiner extends Reducer<Text,IntWritable,Text,IntWritable> {
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
	    Job job = Job.getInstance(conf, "MyFirstWordCount");
	    
	    job.setJarByClass(MYPart.class);
	    
		job.setNumReduceTasks(3);
		
	    job.setMapperClass(MyMapper.class);
	    job.setReducerClass(MyReducer.class);

		//job.setCombinerClass(MyCombiner.class); // Reduces burden on Redcuer
		
		job.setPartitionerClass(MyPartitioner.class);
	
	    
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