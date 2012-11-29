import java.io.IOException;
import java.util.Iterator;
import java.lang.Integer;

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
	Boolean orderFound = false; 
	StringBuffer refs = new StringBuffer();
	int qty=0, count=0;
	double price=0.00,discount=0.00,tax=0.00;
	String custkey="", orderstatus="", ordertotal="", orderdate=""; // one per key (order side)
      
      
	// while (values.hasNext()) {

	//     String mycols[] = values.next().toString().split("\\|");
	//     // refs.append(values.next() + ",");
	//     if (mycols[0].toString().equals("1")) { // order side
	// 	orderFound = true;
	// 	custkey = new String(mycols[1]);
	// 	orderstatus = new String(mycols[2]);
	// 	ordertotal = new String(mycols[3]);
	// 	orderdate = new String(mycols[4]);
	//     } else if (mycols[0].toString().equals("2")) { // line item side
	// 	qty += Integer.parseInt(mycols[1]);
	// 	price += Double.parseDouble(mycols[2]);
	// 	discount += Double.parseDouble(mycols[3]);
	// 	tax += Double.parseDouble(mycols[4]);
	// 	count++;
	//     } else {
	// 	custkey = new String("bummer: the join side is '" + mycols[0] + "'");
	//     }
          
	// }
	output.collect(key, new Text("howdy"));
	// output.collect(key, new Text(refs.toString()));
	// if (orderFound) {
	//     output.collect(key, new Text("howdy"));
	//     // output.collect(key, new Text( custkey.toString() + "|" + orderstatus.toString() + "|" +
	//     // 				  ordertotal.toString() + "|" + orderdate.toString() + "|" +
	//     // 				  Integer.toString(qty) + "|" + Double.toString(price) + "|" + 
	//     // 				  Double.toString(discount) + "|" + Double.toString(tax) + "|" +
	//     // 				  Integer.toString(count) ));
	// }
    }
}
