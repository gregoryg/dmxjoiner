import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class JoinerMapper extends MapReduceBase implements
						   Mapper<LongWritable, Text, Text, Text> {

    @Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {

	// gjg
	String[] cols = value.toString().split("\\t");
	String lineno = cols[0];
	if (cols.length > 1) {
	    String txt = cols[1];
	    for (String word : txt.split("\\W+")) {
		output.collect(new Text(word), new Text("bsfile@" + lineno));
	    }
	}
    }
}
