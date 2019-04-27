def hakimiHavel(degSeq) :
    #runs the # of times as the largest degree element
    count = 1
    for i in range(degSeq[0]) :    
        degSeq[count] -= 1 #subtract one from each subsequent element
        count += 1
    degSeq = degSeq[1:] # remove the first element
    degSeq.sort(reverse=True) #sort the sequence in descending order
    if(degSeq[0] == 0 and degSeq[-1] == 0) :
       return True
    # in descending order, start and end are 0, so all zeroes is acheived
    #and the degseq is graphic
    elif(degSeq[-1] < 0) :
       return False #the smallest element is less than 0, not graphic
    else :
       return hakimiHavel(degSeq)
    # the numbers aren't 0 or less than 0, do it again

#If the degree sequence is graphic, then the function will return True
#Otherwise it will return false

lister = [4, 4, 4, 2, 2, 2]
print (hakimiHavel(lister))

