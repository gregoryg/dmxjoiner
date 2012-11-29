#!/bin/bash
hadoop fs -rm -r dmxjoiner-out

hadoop jar /home/gregj/work/dmxjoiner/java/Joiner/dist/Joiner.jar  Joiner \
    -D mapred.job.name="Join TPCH Java" \
    -D mapreduce.input.fileinputformat.split.minsize=10737418240 \
    -D mapred.sortplugin.class=com.syncsort.dmexpress.hadoop.DMXHadoopSortPlugin \
    -D dmx.home.dir=/usr/local/dmexpress \
    -D mapred.reduce.tasks=1 \
    -D dmx.map.task=mapsort.dxt \
    -D dmx.reduce.task=reducemerge.dxt \
    -files dmx/mapsort.dxt,dmx/reducemerge.dxt \
    -libjars /usr/local/dmexpress/lib/dmxhadoop.jar \
    /tpch/input/s1/  dmxjoiner-out
