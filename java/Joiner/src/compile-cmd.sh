#!/bin/bash
javac -cp `hadoop classpath` -d ../build/classes *.java && \
    (cd ../build/classes; jar cvf ../../dist/Joiner.jar *.class)

