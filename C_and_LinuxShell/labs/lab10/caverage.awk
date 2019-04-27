#!/bin/gawk -f
# take file of spreadsheet and calculate collumn average.
BEGIN	{
	ORS = ""
	discarded = 0
	gtotal = 0
	goodrecord = 0		# number of good records
	nrecord = 0
	}
NR == 1	{		# first record only
	nfields = NF	# set nfields to number of fields in the record (NF)
	}		# assume this should be the number of fields in the file
	{		# for each record
		nrecord += 1
		if ($0 ~ /[^0-9. \t]/ || NF < nfields)		# check each record to see if it contains
		{						# any characters that are not digits,
	    							# periods, spaces, or TABs, or missing fields
	    		#write some code here
			discarded += 1
			print "\nRecord " nrecord " skipped.\n"
			# print $0
			# skip bad records
	    		
	    		next					# ignore the rest of the commands for the 
		}						# current record if this is a bad record.
		else
		{
	    		goodrecord += 1
	    		for (count = 1; count <= nfields; count++)	# for good records, loop through each field
	    		{
				printf "%8.2f", $count > "caverage.out"
				gtotal = gtotal + $1 + $2 + $3 + $4 + $5 + $6
				sum[count] += $count

				
				
	    		}
	    		print "\n" > "caverage.out"
		}
	}
END	{	# print summary after processing the last record
	    print "\n"

	    for (count = 1; count <= nfields; count++)
	    {
		print " =======" > "caverage.out"
	    }
	    print "\n" > "caverage.out"
	    for (count = 1; count <= nfields; count++)
	    {
		printf "%8.2f", sum[count] / goodrecord > "caverage.out"
	    }
	    print "\n\n  Total Records: " nrecord "  Good Records: " \
	          goodrecord "  Discarded Records: " discarded > "caverage.out"
	    print "\n  Grand Average " gtotal / (goodrecord * nfields) \
                  "\n" > "caverage.out"
	}

