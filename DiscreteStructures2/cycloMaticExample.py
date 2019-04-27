import math
def cyclomaticGraphExample(a) :
    result = 1
    a += 1
    if a < 0 :
        result -= a
    else :
        result += a
    result += 1
    return result


    
