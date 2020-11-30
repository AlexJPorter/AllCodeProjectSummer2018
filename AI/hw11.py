
#https://stackoverflow.com/questions/12201577/how-can-i-convert-an-rgb-image-into-grayscale-in-python
#https://numpy.org/doc/stable/reference/generated/numpy.histogram.html
#https://www.kite.com/python/answers/how-to-display-an-image-as-grayscale-using-matplotlib-in-python
#https://www.tutorialspoint.com/numpy/numpy_histogram_using_matplotlib.htm 
#https://www.w3schools.com/python/gloss_python_join_lists.asp 
from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
img = Image.open("geese.png")

gs = img.convert("L")
gsarr = np.asarray(gs)
#print(type(gsarr))
#print(len(gsarr)) # = 375
#print(len(gsarr[150])) # = 250

#The dimensions of the image I'm using are 375 by 250. 
#What the above means is that the image is being read as a multidimensional array, one for rows and one for columns
#Before plotting this as a histogram, we need to convert it to a one dimensional list that contains each value
#the below code does this

all_points = list()
for row in gsarr:
    for entry in row:
        all_points.append(entry)
#There are a fair amount of pure whit pixels. That's probably from the glare/sun in the upper left

plt.hist(all_points, bins=range(0,256)) 
plt.show()

