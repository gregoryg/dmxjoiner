#!/bin/bash

hadoop fs -rm -r join-combiner-output

hadoop jar  /usr/lib/hadoop-0.20-mapreduce/contrib/streaming/hadoop-streaming-2.0.0-mr1-cdh4.1.1.jar \
    -D mapred.job.name="Join TPCH test with DMX COMBINER" \
    -D mapreduce.input.fileinputformat.split.minsize=10737418240 \
    -D mapred.sortplugin.class=com.syncsort.dmexpress.hadoop.DMXHadoopSortPlugin \
    -D dmx.home.dir=/usr/local/dmexpress \
    -D dmx.map.task=mapsort.dxt \
    -D dmx.reduce.task=reducemerge.dxt \
    -D dmx.map.task.useAsPartitioner=true \
    -libjars=/usr/local/dmexpress/lib/dmxhadoop.jar \
    -numReduceTasks 10 \
    -input /tpch/input/lineitem.tbl  \
    -input /tpch/input/orders.tbl  \
    -output join-combiner-output   \
    -file mapper-combiner-lineitem.dxt \
    -file mapper-combiner-orders.dxt \
    -file run-mapper.sh \
    -file join-reducer-step1.dxt \
    -file join-reducer-step2.dxt \
    -file join-reducer-step3.dxt \
    -file run-reducer.sh \
    -file mapsort.dxt \
    -file reducemerge.dxt \
    -mapper  run-mapper.sh   \
    -reducer run-reducer.sh 

#    -D mapred.output.key.comparator.class=org.apache.hadoop.mapred.lib.KeyFieldBasedComparator \
#    -D map.output.key.field.separator=. \
#    -D stream.num.map.output.key.fields=2 \
#    -D mapred.text.key.comparator.options=-k1,1n \
    # -mapper  run-mapper-tpch.sh   \
    # -mapper '/usr/bin/wc -l' \
    # -D stream.num.map.output.key.fields=2 \
    # -D stream.map.output.field.separator=. \
