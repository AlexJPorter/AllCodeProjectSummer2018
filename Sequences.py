import math

def arith(start, number, index) :
    toReturn = []
    counter = 0
    while (index >= 0) :
        toAdd = number * counter
        toReturn.append(start + toAdd)
        counter += 1
        index -= 1
    return toReturn

print (arith(3, 5, 3))
print (arith(5, -1, 4))
print (arith(4, 4, 0))

def geo(start, number, index) :
    toReturn = []
    counter = 0
    while(index >= 0) :
        toMult = number ** counter
        toReturn.append(start * toMult)
        counter += 1
        index -= 1
    return toReturn

print (geo(3, 5, 3))
print (geo(5, -1, 4))
print (geo(4, 4, 0))


def recur(co1, co2, start1, start2) :
    counter = 0
    toReturn = []
    toReturn.append(start1)
    toReturn.append(start2)
    toReturn.append((co2 * start1) + (co1 * start2))
    prevVal = start2
    nextVal = ((co2 * start1) + (co1 * start2))
    toReturn.append((co2 * prevVal) + (co1 * nextVal))
    for i in range(6):
        temp = nextVal
        nextVal = (co2 * prevVal) + (co1 * nextVal)
        prevVal = temp
        toReturn.append((co2 * prevVal) + (co1 * nextVal))
    return toReturn
    

print (recur(1, 1, 0, 1))
print (recur(1, -1, 2, 2))

def delta_one(nums) :
    for i in range(len(nums) - 2) :
        toCompare = abs(nums[i + 1] - nums[i])
        toCompare2 = abs(nums[i + 2] - nums[i+1])
        if(toCompare != toCompare2) :
            return False
    return True


print (delta_one([1, 3, 5, 7, 9, 11, 13]))
print (delta_one([1, 4, 9, 16, 25, 36, 49, 64]))
print (delta_one([0, 4, 18, 48, 100, 180]))

def delta_two(nums) :
    firstDegree = []
    for i in range(len(nums) - 1) :
        toCompare = abs(nums[i + 1] - nums[i])
        firstDegree.append(toCompare)
    return delta_one(firstDegree)

print (delta_two([1, 3, 5, 7, 9, 11, 13])) 
print (delta_two([1, 4, 9, 16, 25, 36, 49, 64]))
print (delta_two([0, 4, 18, 48, 100, 180]))

def delta_three(nums) :
    secondDegree = []
    for i in range(len(nums) - 1) :
        toCompare = abs(nums[i + 1] - nums[i])
        secondDegree.append(toCompare)
    return delta_two(secondDegree)

print (delta_three([1, 3, 5, 7, 9, 11, 13])) 
print (delta_three([1, 4, 9, 16, 25, 36, 49, 64]))
print (delta_three([0, 4, 18, 48, 100, 180]))
