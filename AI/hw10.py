import numpy as np
import random

def sigmoid(x_in) :
    ret = 1 / (1 + np.exp(- x_in))
    return ret

def input_node(x_in) :
    bias = 1
    x_weight = random.randint(-10, 10)
    bias_weight = random.randint(-10, 10)

    sig_in = (x_weight * x_in)  + (bias_weight * bias)
    return sigmoid(sig_in)
def hidden_node(x_in, y_in):
    bias = 1
    x_weight = random.randint(-10, 10)
    y_weight = random.randint(-10, 10)
    bias_weight = random.randint(-10, 10)

    sig_in = (x_weight * x_in) + (y_weight * y_in)  + (bias_weight * bias)
    return sigmoid(sig_in)

def output_node(x_in, y_in) : 
    bias = 1
    x_weight = random.randint(-10, 10)
    y_weight = random.randint(-10, 10)

    sig_in = (x_weight * x_in) + (y_weight * y_in)
    return sigmoid(sig_in)

def nn(x, y):  
    in_1 = input_node(x)
    in_2 = input_node(y)
    print("After Inputs: ", in_1, in_2)

    hidden_1 = hidden_node(in_1, in_2)
    hidden_2 = hidden_node(in_1, in_2)
    print("After hidden: ", hidden_1, hidden_2)

    print("Out: ", output_node(hidden_1, hidden_2))

#Use below lines for cl input
#user_in = tuple(input("Enter two numbers seperated by a comma: "))
#x = int(user_in[0])
#y= int(user_in[2])
#nn(x, y)
nn(36, 42)

