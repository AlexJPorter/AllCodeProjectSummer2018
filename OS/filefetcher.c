#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <signal.h>


int filesCount = 0;

void * getFile(void* fileNameIn);

void signalHandle(int nothing) {
	printf("\nNumber of files accessed: %d\n", filesCount);
	exit(0);
}

int main() {
	//ensure different output on each run
	srand(time(0));
	int status;
	signal(SIGINT, signalHandle);
	pthread_t threadT;
	char fileNameBuffer[32];
	while(1) {
		printf("Enter filename: ");
		scanf("%s", fileNameBuffer);
		if(!strcmp(fileNameBuffer, "quit")) {
			printf("\nUser quit\n");
			break;
		}
		if((status = pthread_create(&threadT, NULL, getFile, &fileNameBuffer)) != 0) {
			fprintf(stderr, "thread create error %d: %s\n", status, strerror(status));
			exit(1);
		}
		pthread_detach(threadT);
	}	
}
void* getFile(void* fileNameIn) {
	char fileNameThread[32];
	//save filename to local stack
	strcpy(fileNameThread, (char *) fileNameIn);
	int random = rand();

	int chance = random % 5; //20% chance of 0
	if(chance) {
		sleep(1);
	}
	else {
		//equal chance 0-3, + 7 makes 7-10
		chance = (random % 4) + 7;
		sleep(chance);
	}
	filesCount ++;
	printf("\nAccessed file: %s\n", fileNameThread);
	return NULL;
}
