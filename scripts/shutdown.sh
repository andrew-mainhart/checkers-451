#!/bin/bash
if [ -f ./pid.file ]; then
    kill $(cat ./pid.file)
fi
