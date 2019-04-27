import math
'''This is the main function. Call this when
#actually working with a matrix. It takes a matrix,
#which is represented as a list of numbers with a length
#n^2 where n is the number of vertices.
#There are many internal functions to this, see the comments
#on each of those for further explanation'''
def fleury(matrix): 
    length = len(matrix)
    vertices = int(math.sqrt(length))
    cycleList = []
    #Declare and find some variables
    if eulerPossible(matrix) :
        #if there is an Euler Path, then continue
        flag = True
        beenVisited = []
        for i in range(vertices) :
            beenVisited.append(True)
        #Create a list with a boolean for each vertex if it has been visited or not
        while(checkFinished(matrix)) :
            #Checks if the matrix is finished, all 0s
            if flag :
                startVertex = 0
                flag = False
                continue
            #First time through just sets startvertex to 0
            else :
                col = findNextStart(matrix, vertices, beenVisited)
                #Finds the starting point of the next cycle(may be first cycle also)
                cycleReturn = findCycle(matrix, col, True, 0, [], vertices, beenVisited)
                #Gets a cycle and the updated matrix where the cycles edges are removed, and an updated beenVisited list 
                matrix = cycleReturn[0]
                #update matrix
                cycleList.append(cycleReturn[1])
                #Adds the returned cycle to a list of cycles
                beenVisited = cycleReturn[2]
                #update beenVisited
                #Continues until the matrix is empty, all 0
        return consolidateCycle(cycleList)
        #Consolidates the list of cycles into one cycle that spans all the edges
    else :
        return [-1]
        #Returns -1 if an EulerPath is not possible


def eulerPossible(matrix) :
    length = len(matrix)
    vertices = int(math.sqrt(length))
    degreeSum = 0
    for j in range(vertices) :
        for i in range(vertices) :
            i = i * vertices
            #Traverse the column
            degreeSum += matrix[i + j]
        if degreeSum % 2 != 0 or degreeSum == 0:
            return False #there is a vertex of odd degree, the Euler circuit is impossible
        else :
            degreeSum = 0
            #For each column reset the sum if it's even and non-zero
    return True
    #An euler circuit is possible
'''
Here's a real big chunk in the program. This function uses recursion to search
through a list until it finds the vertex it started with. While it searches,
it appends the vertices it visits to a list, and deletes the edges it "traverses"
from the matrix it is given. It then returns a matrix, a list of each vertex that has
been visited, and a list of vertices that form a cycle.
'''
def findCycle(matrix, col, starting, startVertex, vertexList, vertices, beenVisited) :
    #Create a copy of the matrix to modify
    newMatrix = matrix
    if starting and len(beenVisited) == 0:
        beenVisited = [False]
        for i in range(vertices - 1) :
            beenVisited.append(False)
    # initialize the beenVisited list if it's empty
    for i in range(vertices) :
        #iterate down the vertices
        if newMatrix[(vertices * i) + col] == 1 :
            #if there is a one in that column given as a param
            newMatrix[(vertices * i) + col] = 0
            #Set that one to 0
            reverse = whereAmI((vertices * i) + col, vertices)
            #Use a function to get the row and column of the matrix from the index
            newMatrix[getElement(reverse[1],reverse[0], vertices)] = 0
            #Reverse the row and column and delete the symmetrical 1 in the matrix for the same edge
            vertexList.append(whereAmI((vertices * i) + col, vertices)[1])
            #Add the vertex, represented by a column, to the cycle list
            beenVisited[col] = True
            #set that vertex as being visited
            if starting :
                #If this is a non-recursive call
                startVertex = whereAmI(vertices * i + col, vertices)[1]
                #start vertex is the column you find
                newCol = whereAmI(vertices * i + col, vertices)[0]
                #Next column to search through is the row that one was in, or the connecting vertex
                return findCycle(newMatrix, newCol, False, startVertex, vertexList, vertices, beenVisited)
                #Recursively find the next vertex in the list
            elif whereAmI(vertices * i + col, vertices)[0] == startVertex :
                #if you're back where you started
                vertexList.append(startVertex)
                #add the startvertex one last time
                return [newMatrix, vertexList, beenVisited]
                #return the result
            else :
                newCol = whereAmI(vertices * i + col, vertices)[0]
                #find the next column
                return findCycle(newMatrix, newCol, False, startVertex, vertexList, vertices, beenVisited)
                #Recursively find the next vertex in the list

#Given a row and col and vertices number,
#return the index of that element in the list
def getElement(row, col, vertices):  
    index = 0
    for i in range(row) :
        index += vertices
    for i in range(col) :
        index += 1
    return index

#Given an index and vertices number,
#return the row and column in the matrix
def whereAmI(index, vertices) :
    row = int(index/vertices)
    col = index % vertices
    return [row, col]

#A little nugget to throw in
#This function determines if a matrix
#has an Euler path
def eulerPath(matrix) :
    length = len(matrix)
    vertices = int(math.sqrt(length))
    degreeSum = 0
    twoOdds = 0
    for j in range(vertices) :
        for i in range(vertices) :
            i = i * vertices
            degreeSum += matrix[i + j]
        if degreeSum == 0 or twoOdds > 2:
            return False
        elif degreeSum % 2 != 0:
            twoOdds += 1
        else :
            degreeSum = 0
    return True
            
#Check if the matrix is empty
def checkFinished(matrix) :
    for number in range(len(matrix)) :
        if matrix[number] != 0 :
            return True
    return False
'''
Takes a list of cycles and returns a single list
where they are all consolidated into one spanning
cycle. Takes in a list of cycles. This one also uses
recursion to compile multiple cycles. I'm pretty
proud of this function on its own, I think it's
quite elegant.
'''
def consolidateCycle(cycleList) :
    if len(cycleList) == 1 :
        return cycleList[0]
    #Exit condition, there is only one cycle in the list
    else :
            for cycle in range(len(cycleList)) :
                #For each cycle in the list
                for vertex in range(len(cycleList[cycle])) :
                    #for each vertex in each cycle
                    for cycle2 in range(len(cycleList)) :
                        #for each cycle in the list, but different from above
                        if cycle == cycle2 :
                            continue
                        #Only consolidate different cycles
                        if cycleList[cycle][vertex] == cycleList[cycle2][0] :
                            #if the vertex of the super cycle is the same as the start(and end)
                            #of the subcycle
                            cycleList[cycle][vertex : vertex + 1] = cycleList[cycle2]
                            #replace the vertex with the cycle
                            del cycleList[cycle2]
                            #delete the subcycle from the list
                            return consolidateCycle(cycleList)
                            #return the function again but with one less cycle
                                
                            
                            
                        
                                

#Find the next starting column for finding cycles
def findNextStart(matrix, vertices, beenVisited) :
    for j in range(len(matrix)) :
        #For all the elements in the matrix
        if matrix[j] == 1 and beenVisited[whereAmI(j, vertices)[1]] == True :
            #If there's an edge and that column has been visited
            return whereAmI(j, vertices)[1]
            #return that column
        

matrix = [0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0]
print(fleury(matrix))

                        







                        
