#!/bin/bash 
declare -a arr
total=0
avg=0
size=0
t=0
max=0
while read -r i
do 
	arr+=($i)
done
for i in ${arr[*]}
do
	if [ $i -gt $max ]; then
		max=$i
	fi
	((total+=$i))
done
echo "max:" $max
echo "total:" $total
((total=total/${#arr[@]}))
echo "avg:" $total
