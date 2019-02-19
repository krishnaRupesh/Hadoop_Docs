import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SalaryList {
	
	//7369,SMITH,CLERK,34,800
	
	
	public static class MyMapper extends Mapper<LongWritable,Text,Text,Text>{
		Text mykey = new Text();
		Text myvalue = new Text();

		public void map(LongWritable key, Text value, Context con)throws IOException, InterruptedException{
			
			String line = value.toString();
        	String[]  strts = line.split(",");
        	int sal = Integer.parseInt(strts[4]);
        	if(sal>2500) {
        		mykey.set(strts[0]);
        		myvalue.set(strts[1] + "  ==>  "+ strts[4]);
        		con.write(mykey, myvalue);
			}		
        }
	}
	//key : 7369	value : SMITH

	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "MySalaryList");
	    
	    job.setJarByClass(SalaryList.class);
	    
	    job.setMapperClass(MyMapper.class);
		job.setNumReduceTasks(0);

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    Path outputPath = new Path(args[1]);
	    FileOutputFormat.setOutputPath(job, outputPath);
		outputPath.getFileSystem(conf).delete(outputPath);
	    
	    
	    job.waitForCompletion(true);
	  }

}