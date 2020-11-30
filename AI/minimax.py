class Node() :
    def __init__(self, value, parent, name ) :
        self.value = value
        self.parent = parent
        self.children = []
        self.name = name

A = Node(9990, None, 'A')
B = Node(999, A, 'B')
C = Node(999, A, 'C')
D = Node(999, A, 'D')
E = Node(999, B, 'E')
F = Node(999, B, 'F')
G = Node(999, C, 'G')
H = Node(999, C, 'H')
I = Node(999, D, 'I')
J = Node(999, D, 'J')
K = Node(2, E, 'K')
L = Node(1, E, 'L')
M = Node(3, E, 'M')
N = Node(5, F, 'N')
O = Node(4, F, 'O')
P = Node(7, G, 'P')
Q = Node(6, H, 'Q')
R = Node(8, H, 'R')
S = Node(9, I, 'S')
T = Node(10, I, 'T')
U = Node(12, J, 'U')
V = Node(11, J, 'v')
A.children.extend([B, C, D])
B.children.extend([E, F])
C.children.extend([G, H])
D.children.extend([I, J])
E.children.extend([K, L, M])
F.children.extend([N, O])
G.children.extend([P])
H.children.extend([Q, R])
I.children.extend([S, T])
J.children.extend([U, V])

def minimax(node_in, depth, maximizingPlayer) :
    if depth == 0 or not node_in.children :
        return [node_in.value, node_in.name, maximizingPlayer]
    if maximizingPlayer:
        maxEval = -9999
        for child in node_in.children:
            eval_out = minimax(child, depth - 1, False)
            maxEval = max(eval_out[0], maxEval)
        return [maxEval, node_in.name, maximizingPlayer]
    else:
        minEval = 9999
        for child in node_in.children:
            eval_out = minimax(child, depth - 1, True)
            minEval = min(minEval, eval_out[0])
        return [minEval, node_in.name, maximizingPlayer]

#I included this function for more human readable output. But the list works fine too
def printMiniMax(minmax_in) :
    print(str(minmax_in[0]) + " is the result of minmax on " + minmax_in[1] + " with boolean 'isMaxPLayer' " + str(minmax_in[2]) )

print(minimax(A, 3, True)) # should be 10
print()
print("//Below is a more human readable output//")
print()
printMiniMax(minimax(A, 3, True))
