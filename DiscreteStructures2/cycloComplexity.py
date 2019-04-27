import math
def findCycloComplexity(matrix) :
    vertices = math.sqrt(len(matrix))
    #find number of vertices of a square matrix
    edgeSum = 0
    for edge in matrix :
        if edge == 1:
            edgeSum = edgeSum + 1
    #total all the 1s in the matrix
    edgeSum = edgeSum / 2
    #every edge was counted twice, so divide by two
    return edgeSum - vertices + 2
    #return the equation

matrix = [0, 1, 1,
          1, 1, 0,
          1, 0, 1]
print(findCycloComplexity(matrix))
#prints 2.0
