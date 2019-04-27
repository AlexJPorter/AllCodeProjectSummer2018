#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "sort.h"

int n = 0;
int main()
{
	printf("Please input the size of the array: ");
	scanf("%d", &n);
	if(n < 1) 
	{
		return 0;
	}
	int randomArray[n];
	int sortedArray[n];
	int i = 0;
	int j = 0;
	for(i = 0; i < n; i ++) 
	{
		sortedArray[i] = i; //Creates an already sorted array
		j = rand();
		randomArray[i] = j; //Random array
	}

	double bubStart, bubEnd, selecStart, selecEnd, bubAvg, selecAvg, bubBest, selecBest;
	bubStart = clock();
	bubbleSort(randomArray, n);
	bubEnd = clock();
	if(n < 50) {
		printf("Here is the sorted array in ascending order: \n");
		int l;
		for(l = 0; l < n; l ++) {
			printf("%i ", randomArray[l]);
		}
		printf("\n");
	}
	bubAvg = (double) (bubEnd - bubStart) / CLOCKS_PER_SEC;
	bubAvg = bubAvg * 1000; //Find average case
	bubStart = clock();
	bubbleSort(sortedArray, n); //Best case, array already sorted
	bubEnd = clock();
	bubBest = (double) (bubEnd - bubStart) / CLOCKS_PER_SEC;
	printf("Array_Size	Algorithm	Average		Best\n%d		Bubble		%f	%f\n",n, bubAvg, bubBest);


	int randomArray2[n];
	int y = 0;
	int e = 0;
	for(y = 0;  y < n; y ++) {
		e = rand();
		randomArray2[y] = e; //Create a new random array
	}
	selecStart = clock();
	selectionSort(randomArray2, n);
	selecEnd = clock();
	if(n < 50) {
		printf("Here is the sorted array in ascending order: \n");
		int aa;
		for(aa = 0; aa < n; aa ++) {
			printf("%i ", randomArray2[aa]);
		}
		printf("\n");
	}
	selecAvg = (double) (selecEnd - selecStart) / CLOCKS_PER_SEC;
	selecStart = clock();
	selectionSort(sortedArray, n); //sorted array is still sorted
	selecEnd = clock();
	selecBest = (double) (selecEnd - selecStart) / CLOCKS_PER_SEC;
	printf("Array_Size	Algorithm	Average		Best\n%d		Selection	%f	%f\n",n,selecAvg,selecBest);
	return 0;

}
