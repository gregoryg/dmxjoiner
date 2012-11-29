import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class JoinerMapper extends MapReduceBase implements
        Mapper<LongWritable, Text, Text, Text> {

    int whichfile = -1; // -1: unknown; 1: left side (orders); 2: right side (lineitem)

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
            Reporter reporter) throws IOException {

        
        FileSplit split = (FileSplit) reporter.getInputSplit();
        String myfile = split.getPath().getName();

        if (myfile.endsWith("orders.tbl")) {
            whichfile = 1;
        } else if (myfile.endsWith("lineitem.tbl")) {
            whichfile = 2;
        }

        // gjg
        String mycols[] = value.toString().split("\\|");
        String newkey;
        String newval;

        if (whichfile == 1) {
            //orders side
            newkey = mycols[0];
	    // CUSTKEY, ORDERSTATUS, ORDERTOTAL, ORDERDATE
            newval = "1" + "|" + mycols[1] + "|" + mycols[2] + "|" + mycols[3] + "|" + mycols[4];
        } else if (whichfile == 2) {
            // lineitem side
	    //            newkey = Long.parseLong(mycols[0]);
            newkey = mycols[0];
	    // QTY, PRICE, DISCOUNT, TAX, COUNT
            newval = "2" + "|" + mycols[4] + "|" + mycols[5] + "|" + mycols[6] + "|" + mycols[7] + "|" + "1";
        } else {
            // who knows
            newkey = "-1";
            newval = "-1|UNKNOWN FILE: " + System.getenv("map_input_file");
        }
        output.collect(new Text(newkey), new Text(newval));
    }
}
