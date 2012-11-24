#!/bin/bash


hadoop fs -rmr /dmx/joiner/output

# hadoop jar /usr/lib/hadoop-mapreduce/hadoop-streaming.jar \
hadoop jar  /usr/lib/hadoop-0.20-mapreduce/contrib/streaming/hadoop-streaming-2.0.0-mr1-cdh4.0.1.jar \
    -D mapred.job.name="Join TPCH test" \
    -D mapred.min.split.size=629145600 \
    -D mapred.output.key.comparator.class=org.apache.hadoop.mapred.lib.KeyFieldBasedComparator \
    -D stream.map.output.field.separator=. \
    -D stream.num.map.output.key.fields=4 \
    -D map.output.key.field.separator=. \
    -D mapred.text.key.comparator.options=-k1,1n \
    -numReduceTasks 2 \
    -input /dmx/joiner/input/lineitem.tbl  \
    -input /dmx/joiner/input/orders.tbl  \
    -output /dmx/joiner/output   \
    -file run-mapper-tpch.sh \
    -file run-reducer-tpch.sh \
    -file join-tpch-mapper.dxt \
    -file join-reducer-step1.dxt \
    -file join-reducer-step2-tpch.dxt \
    -file join-reducer-sum-step3.dxt \
    -file join-reducer-job.dxj \
    -mapper  run-mapper-tpch.sh   \
    -reducer run-reducer-tpch.sh 
