import pandas as pd
import numpy as np 
import random

#Professor David Austin of the Math department at GVSU created this library for working with matrices,
#specifically for linear algebra students (like me). Since I already work with this library daily,
#I figured I'd use it for this assignment as well. Source: https://github.com/davidaustinm/MTH205-W20/blob/master/libs/mth205.py
import mth205source as mt


#https://www.shanelynn.ie/select-pandas-dataframe-rows-and-columns-using-iloc-loc-and-ix/#loc-selection
#https://stackoverflow.com/questions/34091877/how-to-add-header-row-to-a-pandas-dataframe
#https://stackoverflow.com/questions/17412439/how-to-split-data-into-trainset-and-testset-randomly
#https://www.geeksforgeeks.org/python-standard-deviation-of-list/ 
#https://www.youtube.com/watch?v=IIuXF5QRBTY image for formula
iris_data = pd.read_csv('iris.csv', names = ['sepal_length', 'sepal_width', 'petal_length', 'petal_width', 'flower_class'])
iris_data.set_index("flower_class", inplace=True)
virginica_prep = iris_data.loc['Iris-virginica']
setosa_prep = iris_data.loc['Iris-setosa']
versicolor_prep = iris_data.loc['Iris-versicolor']

virginica = mt.matrix(50, 4, virginica_prep.values.tolist())
setosa = mt.matrix(50, 4, setosa_prep.values.tolist())
versicolor = mt.matrix(50, 4, versicolor_prep.values.tolist())

#All of the above lines convert the csv to matrices seperated by class, where each row in the matrix is the 4 values for that entry

def get_mean(data_in):
    entries = data_in.entries
    random.shuffle(entries)
    #There are 50 of each class. So that's a 30-20 split, 30 to training and 20 for testing
    training = mt.matrix(entries[:30])
    testing = mt.matrix(entries[30:])
    mean_list = list()
    demean_vectors = list()
    for i in range (4):
        mean_list.append(sum(training.column(i).entries) / len(training.column(i).entries))
        demean_vectors.append(training.column(i).demean())
    
    #https://datascienceplus.com/understanding-the-covariance-matrix/
    #fp = training.column(0) - mt.vector(mean_list[0])
    #sp = training.column(0) - mt.vector(mean_list[0])
    #tp = fp * sp
    #oxy = 1/(50 - 1) * tp
    #print(oxy)
    #If you un-comment these lines, you'll see the top left entry of the covariance_matrix
    #I used a shortcut (demean), but the result is the same as seen here for the covariance of two values x,y

    trainingDT = mt.matrix([demean_vectors[0], demean_vectors[1], demean_vectors[2], demean_vectors[3]])
    trainingD = trainingDT.transpose()
    covariance_matrix = (1/ (50 - 1)) * trainingDT * trainingD
    #Uncomment to verify output
    #print(covariance_matrix)    
    
    return mt.vector(mean_list), covariance_matrix, testing
#print(mean_vector)

virg_mean, virg_cov, virg_testing = get_mean(virginica)
set_mean, set_cov, set_testing = get_mean(setosa)
vers_mean, vers_cov, vers_testing = get_mean(versicolor)

def get_gaussian(mean_vec, cov_mat, x_vec) :
    # pi (3.1415...) = np.pi
    # np.exp() is e^ ()
    k = 4
    sigma = cov_mat.det()
    sigma_i = cov_mat.inverse()
    a = 1 / (((((2*np.pi) ** k)) * sigma) ** .5)
    b = -.5 * (mt.matrix([x_vec - mean_vec]) * sigma_i * (x_vec - mean_vec))
    b = b[0]
    return a * np.exp(b)


def determine_correct(class_mean_vectors, cov_mat_list, testing_mat, actual_class) :
    #class_means_vectors is the list of all mean vectors [u1, u2, u3]
    #cov_mat_list is the list of all covariance matrices
    #testing_mat is the testing_matrix for a particular class
    #actual_class is the actual flower the testing data belongs to
    correct = 0
    for row in testing_mat.entries:
        row_vec = mt.vector(row)
        gauss_values = list()
        for i in range(len(class_mean_vectors)): # there are 3 classes
            gauss_values.append([i, get_gaussian(class_mean_vectors[i], cov_mat_list[i], row_vec)])
        max_gauss = gauss_values[0]
        for j in range(len(gauss_values)) :
            if(gauss_values[j][1] > max_gauss[1]):
                max_gauss = gauss_values[j]
        if max_gauss[0] == actual_class:
            correct += 1
    return correct

def get_reports(mean_vector, cov_vector, test_vectors) :
    for test in range(len(test_vectors)) :
        corrects = determine_correct(mean_vector, cov_vector, test_vectors[test], test)
        print("Class " + str(test) + " had " + str(corrects) + "/20 classified correctly.")
    print("(0 = virginica, 1 = setosa, 2 = versicolor")

means = [virg_mean, set_mean, vers_mean]
covs = [virg_cov, set_cov, vers_cov]
tests = [virg_testing, set_testing, vers_testing]
get_reports(means, covs, tests)
