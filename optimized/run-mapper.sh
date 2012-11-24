#!/bin/bash
export DMXHOME=/usr/local/dmexpress
export PATH=$PATH:$DMXHOME/bin
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$DMXHOME/lib

## remove the intermediate files used for joining
rm -f join-reducer-side[12].dat

if [ `basename $map_input_file` == "orders.tbl" ]; then
    dmexpress /run mapper-combiner-orders.dxt
else
    dmexpress /run mapper-combiner-lineitem.dxt
fi

### dmexpress /run join-tpch-mapper.dxt
