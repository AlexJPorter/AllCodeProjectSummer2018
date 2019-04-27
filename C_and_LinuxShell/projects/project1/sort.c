#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "sort.h"

int bubbleSort(int passn[], int n) 
{
	int i, j;
	for(i = 0; i < n-1; i++) {
		for(j = 0; j < n - i - 1; j++) {
			if(passn[j] > passn[j + 1]) {
				int temp = passn[j];
				passn[j] = passn[j+1];
				passn[j+ 1] = temp;
			}
		}
	}
	return 0;
}
int selectionSort(int passtwo[], int n) {
	int count1,count2;
	for(count1 = 0; count1 < n-1; count1++) {
		int currentMinIndex = count1;
		for(count2 = count1+1; count2 < n; count2++){
			if(passtwo[count2] < passtwo[currentMinIndex]) {
				currentMinIndex=count2;
			}
		}
		if(currentMinIndex != count1) {
			int temp = passtwo[count1];
			passtwo[count1] = passtwo[currentMinIndex];
			passtwo[currentMinIndex] = temp;
	}	
	}
	return 0;
}
