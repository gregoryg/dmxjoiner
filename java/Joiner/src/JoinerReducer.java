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
    // int accumulator indexes
    private static final int IDXQTY = 0;
    private static final int IDXCNT = 1;
    // amount (double) accumulator indexes
    private static final int IDXPRICE = 0;
    private static final int IDXDISC =  1;
    private static final int IDXTAX =   2;

    private static final int IDXJOINSIDE = 0;
    // column indexes into order records
    private static final int IDXORDERCUST   = 1;
    private static final int IDXORDERSTATUS = 2;
    private static final int IDXORDERTOTAL  = 3;
    private static final int IDXORDERDATE   = 4;
    // column indexes into line item records
    private static final int IDXLITEMQTY     = 1;
    private static final int IDXLITEMPRICE   = 2;
    private static final int IDXLITEMDISC    = 3;
    private static final int IDXLITEMTAX     = 4;
    private static final int IDXLITEMCNT     = 5;



    @Override
	public void reduce(Text key, Iterator<Text> values,
			   OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

	// gjg
	Boolean orderFound = false; 
	int[] counters = {0,0}; // qty, lineitem count
	double[] amtTotals = {0.00, 0.00, 0.00}; // price, discount, tax
	String custkey="", orderstatus="", ordertotal="", orderdate=""; // one per key (order side)
      
      
	while (values.hasNext()) {

	    String mycols[] = values.next().toString().split("\\|");
	    if (mycols[IDXJOINSIDE].toString().equals("1")) { // order side
		orderFound = true;
		custkey = new String(mycols[IDXORDERCUST]);
		orderstatus = new String(mycols[IDXORDERSTATUS]);
		ordertotal = new String(mycols[IDXORDERTOTAL]);
		orderdate = new String(mycols[IDXORDERDATE]);
	    } else if (mycols[IDXJOINSIDE].toString().equals("2")) { // line item side
		counters[IDXQTY] += Integer.parseInt(mycols[IDXLITEMQTY]);
		counters[IDXCNT] += Integer.parseInt(mycols[IDXLITEMCNT]);

		amtTotals[IDXPRICE] += Double.parseDouble(mycols[IDXLITEMPRICE]);
		amtTotals[IDXDISC] += Double.parseDouble(mycols[IDXLITEMDISC]);
		amtTotals[IDXTAX] += Double.parseDouble(mycols[IDXLITEMTAX]);
	    } else {
		custkey = new String("bummer: the join side is '" + mycols[IDXJOINSIDE] + "' and the key is '" + key.toString() + "' ");
	    }
          
	}
	// if (orderFound) {
	    
	    output.collect(key, new Text( custkey.toString() + "|" + orderstatus.toString() + "|" +
	    				  ordertotal.toString() + "|" + orderdate.toString() + "|" +
	    				  Integer.toString(counters[IDXQTY]) + "|" + Double.toString(amtTotals[IDXPRICE]) + "|" + 
	    				  Double.toString(amtTotals[IDXDISC]) + "|" + Double.toString(amtTotals[IDXTAX]) + "|" +
	    				  Integer.toString(counters[IDXCNT]) ));
	// }
    }
}
