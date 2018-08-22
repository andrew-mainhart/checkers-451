#!/bin/bash
if [ -f ./pid.file ]; then
    kill $(cat ./pid.file) || true
fi
