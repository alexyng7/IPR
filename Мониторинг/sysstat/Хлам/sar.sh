#!/bin/bash
sar -r 10 240 > /home/student/IPRB/sar_memory.csv
sar -n ALL 10 240 > /home/student/IPRB/sar_network.csv
sar -b 10 240 > /home/student/IPRB/sar_io.csv
sar -u -P ALL 10 240 > /home/student/IPRB/sar_cpu_all.csv
sar -w 10 240 > /home/student/IPRB/sar_process_creation.csv
sar -q 10 240 > /home/student/IPRB/sar_queue.csv
sar -S 10 240 > /home/student/IPRB/sar_podkach.csv
sar -B 10 240 > /home/student/IPRB/sar_pagging.csv
sar -d 10 240 > /home/student/IPRB/sar_disk.csv
iostat 10 240 > /home/student/IPRB/iostat_io.csv