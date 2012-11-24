#!/bin/bash
export DMXHOME=/usr/local/dmexpress
export PATH=$PATH:$DMXHOME/bin
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$DMXHOME/lib

## remove the intermediate files used for joining
rm -f join-reducer-side[12].dat

dmexpress /run join-tpch-mapper.dxt
