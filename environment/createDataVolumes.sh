#!/bin/bash
# Lists all directories in current working directory and create subdirectories 
LIST='ls -D'
echo $LIST
mkdir -p data
cd data
## declare an array variable
declare -a dataarr=("neo4j" "postgres")

## now loop through the above array
 for volname in "${dataarr[@]}"
  do
     echo "$volname"
     mkdir -p "$volname"
  done

