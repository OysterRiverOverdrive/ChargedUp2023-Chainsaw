#!/bin/bash

if find . -name '*.java' -exec grep MotorType.kBrushed {} +; then
    echo ''
    echo 'ERROR:'
    echo 'ERROR:  Found references to unallowed MotorType.kBrushed types in the above files.'
    echo 'ERROR:'
    echo ''
    exit 1
fi