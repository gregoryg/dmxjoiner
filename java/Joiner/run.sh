#!/bin/bash
tmpscript="/tmp/run-join.$$.sh"

jobname="Join TPCH Java"
genericargs=""
dmxSortPluginArgs="-D mapred.sortplugin.class=com.syncsort.dmexpress.hadoop.DMXHadoopSortPlugin -D dmx.home.dir=/usr/local/dmexpress -D dmx.map.task=mapsort.dxt -D dmx.reduce.task=reducemerge.dxt -files dmx/mapsort.dxt,dmx/reducemerge.dxt -libjars /usr/local/dmexpress/lib/dmxhadoop.jar"

indir="/tpch/input/s1/[lo][ir]*.tbl" # should be directory with *orders.tbl and *lineitem.tbl
outdir="dmxjoiner-out"


while [ $# -gt 0 ]
do
    cuurentOption="$1"
    shift
done

hadoop fs -rm -r "$outdir"

hadoop jar /home/gregj/work/dmxjoiner/java/Joiner/dist/Joiner.jar  Joiner \
    -D mapred.job.name="Join TPCH Java" \
    -D mapreduce.input.fileinputformat.split.minsize=1073741824 \
    -D mapred.sortplugin.class=com.syncsort.dmexpress.hadoop.DMXHadoopSortPlugin \
    -D dmx.home.dir=/usr/local/dmexpress \
    -D mapred.reduce.tasks=10 \
    -D dmx.map.task=mapsort-task-numeric.ins \
    -D dmx.reduce.task=reducemerge-task-numeric.ins \
    -files dmx/mapsort-task-numeric.ins,dmx/reducemerge-task-numeric.ins \
    -libjars /usr/local/dmexpress/lib/dmxhadoop.jar \
    "$indir"  "$outdir"


#    -D dmx.map.task=mapsort.dxt \
#    -D dmx.reduce.task=reducemerge.dxt \
#    -files dmx/mapsort.dxt,dmx/reducemerge.dxt \
