#!/bin/bash
export DMXHOME=/usr/local/dmexpress
export PATH=$PATH:$DMXHOME/bin
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$DMXHOME/lib

dmexpress /run join-reducer-step1.dxt

dmexpress /run join-reducer-step2-tpch.dxt | dmexpress /run join-reducer-sum-step3.dxt

## dmxjob /run join-reducer-job.dxj
