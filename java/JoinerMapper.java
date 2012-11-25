import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class JoinerMapper extends MapReduceBase implements
						   Mapper<LongWritable, Text, Text, Text> {
    private String WHICHFILE;

    @Override 
	public void configure(JobConf job) {
	
	WHICHFILE = System.getenv("map_input_file");
	WHICHFILE = WHICHFILE.substring(WHICHFILE.lastIndexOf("/") + 1);
    }

    @Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {

	// gjg
	String mycols[] = value.toString().split("\\|");
	String newkey,newval;

	if (WHICHFILE.endsWith("orders.tbl")) {
	    //orders side
	    newkey = mycols[0];
	    newval = "1|ORDERS SIDE|whatever|blah blah";
	} else if (WHICHFILE.endsWith("lineitem.tbl")) {
	    // lineitem side
	    newkey = mycols[0];
	    newval = "2|LINEITEM SIDE|whatever|blah blah";
	} else {
	    // who knows
	    newkey = "-1";
	    newval = "-1|UNKNOWN FILE: " + WHICHFILE;
	}
	output.collect(new Text(newkey), new Text(newval));
    }
}
