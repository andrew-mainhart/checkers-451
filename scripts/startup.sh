#!/bin/bash
java -jar -Xms8g -Xmx12g /opt/checkers451/checkers451-0.0.1.jar &>/dev/null &
echo $! > /opt/checkers451/pid.file