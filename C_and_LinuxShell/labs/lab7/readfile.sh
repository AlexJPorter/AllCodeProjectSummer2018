#!/bin/bash
while read -r f1 f2
do
	echo "$f1"
	[ ! -d $f1 ] && mkdir $f1
	touch ./$f1/$f2
done < classes
