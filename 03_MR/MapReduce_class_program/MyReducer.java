import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
	
	//welcome,[1,1]
	IntWritable  result = new IntWritable();
	
	public void reduce(Text key, Iterable<IntWritable> values, Context con)throws IOException, InterruptedException{
		
		//Aggretion or Consolidation
		
		int sum = 0;
		for(IntWritable val : values ){
			
			sum += val.get();
		}
		result.set(sum);
		con.write(key, result);
		
	}

}
