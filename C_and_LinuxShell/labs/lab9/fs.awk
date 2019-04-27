#!/bin/gawk -f
BEGIN	{
	d = 0	# count for number of directory
	f = 0
	l = 0
	total = 0
	storage = 0
# define more variables here.
	}
$1 ~ /^d[rwxts-]{9}/	{d += 1}
# directory
$1 ~ /^-[rwxts-]{9}/	{f += 1}
$1 ~ /^l[rwxts-]{9}/	{l += 1}
{storage += $5}
{total = d + f + l}
# add more here.

END	{print ""
	 print "Here is the summary of files under your home directory:"
	 print ""
	 print "directories   files     links      total       storage(Bytes)"
	 print "============================================================="
	 printf "%-13d %-9d %-11d %-10d %-10d\n\n", d, f, l, total, storage
	} 
