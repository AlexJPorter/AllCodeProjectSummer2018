def factorial(num) :
    if(num == 0) :
        return 1
    else :
        return factorial(num - 1) * num
print (factorial(5))
print (factorial(0))

def perm(n, r) :
    return (factorial(n) / factorial(n - r))

def choose(n, r) :
    return (perm(n, r) / (factorial(r)))

print (choose(5, 3))
print (choose(5, 5))

print (perm(5, 3))
print (perm(5, 5))
print (perm(5, 0))

def lat_two(points) :
    counter = 0
    for key in points :
        if(counter == 1) :
            key2 = key
        else :
            key1 = key
        counter += 1
    
    xCoord = abs(key2 - key1)
    yCoord = abs(points[key2] - points[key1])
    totalChoices = xCoord + yCoord
    toChoose = xCoord
    if(yCoord > xCoord) :
        toChoose = yCoord
    return choose(totalChoices, toChoose)

print (lat_two({0 : 0, 5 : 3}))       
print (lat_two({2 : 5, 3 : 5}))   

def lat_three(points) :
    counter = 0
    for key in points :
        if(counter == 1) :
            key2 = key
        elif(counter == 2) :
            key3 = key
        else :
            key1 = key
        counter += 1
    first = lat_two({key1 : points[key1],key2 : points[key2]})
    second = lat_two({key2 : points[key2], key3 : points[key3]})
    return first * second

print ("\n")
print (lat_three({0 : 0, 2 : 1, 5 : 3}))
print (lat_three({2 : 5, 3 : 5, 5 : 5}))

def permutation(lst):
    if len(lst) == 0:
        return []
    if len(lst) == 1:
        return [lst]
    l = [] 
    for i in range(len(lst)):
       m = lst[i]
       remLst = lst[:i] + lst[i+1:]
       for p in permutation(remLst):
           l.append([m] + p)
    return l

def list_perm(nums) :
    toReturn = []
    data = list(set(nums))
    for datum in permutation(data) :
        toReturn.append(datum)
    return toReturn

print(list_perm([1,2,3]))




