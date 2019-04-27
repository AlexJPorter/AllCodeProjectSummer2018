/* A lab for time and math */
#include<stdio.h>
#include<math.h>
#include<time.h>
#include<stdlib.h>

int main()
{
	/* clean up the screen for the results of this program */
	system("clear");
	
	/* Tell user the program is running. Please wait. */
	printf("Program is running ... \n\n");
	
	clock_t start, end, sqstart, sqend;
	double cpu_time_used, cpu_time_used_2;
	start = clock();	
	/* This is how to invoke a function in <math.h> */
	int i;
	for(i = 0; i < 100000000; i++) {
		pow(1000.5, 222.2);
	}
	end = clock();
	cpu_time_used = (double) (end - start)/ CLOCKS_PER_SEC;
	cpu_time_used = cpu_time_used * 1000;
	sqstart = clock();
	for(i = 0; i < 100000000; i++) {
		sqrt(100);
	}
	sqend = clock();
	cpu_time_used_2 = (double) (sqend - sqstart) / CLOCKS_PER_SEC;
	cpu_time_used_2 = cpu_time_used_2 * 1000;
       	printf("CPU time to invoke pow() 100000000 times in milliseconds %f",cpu_time_used);
	printf("\n\nCPU time to invoke sqrt() 100000000 times in milliseconds %f\n",cpu_time_used_2);

	return 0;
}
