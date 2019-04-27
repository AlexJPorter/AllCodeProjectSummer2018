#!/bin/bash
while read x
do 
	#echo $x
	if [ -$1 $x ]
	then 
		echo $x
	fi	
done
