#!/bin/bash
if [ -f /opt/checkers451/pid.file ]; then
    kill $(cat /opt/checkers451/pid.file) || true
fi
