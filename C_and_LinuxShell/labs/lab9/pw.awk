#!/bin/gawk -f
# This awk script is to process the /etc/passwd file
BEGIN	{FS = ":" 
	 uid = $3
	 gid = $4
	 maxu = 0
	 maxg = 0
	 print ""
	 print "Here are the results:\n"
	 print "user_name password uid   gid   full_name        home            shell"
	 print "=============================================================================="
	}
	{printf"%-12s %-5s %-5d %-5d %-16s %-16s %-16s\n", \
		substr($1,0,10), substr($2,0,10), $3, $4, \
		substr($5,0,16), substr($6,0,16), substr($7,0,16)
	if($3 > maxu)
		maxu = $3
	if($4 > maxg)
		maxg = $4
	}
END	{print "=============================================================================="
	 print "Next available uid: " maxu + 1
	 print "Next available gid: " (maxg + 1)
	}
