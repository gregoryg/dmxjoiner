import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class JoinerReducer extends MapReduceBase implements
    Reducer<Text, Text, Text, Text> {

  private static final String SEP = ",";

  @Override
  public void reduce(Text key, Iterator<Text> values,
      OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

    // gjg
      StringBuffer refs = new StringBuffer();
      while (values.hasNext()) {
	  refs.append(values.next() + ",");
      }
      output.collect(key, new Text(refs.toString()));
  }
}
