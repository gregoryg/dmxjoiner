#!/bin/bash
PATH=$PATH:/usr/local/dmexpress/bin
LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/dmexpress/lib
dmexpress /run max-key.dxt
