#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include <semaphore.h>
#include <pthread.h>

void* consumeItem(void*);
void* produceItem(void*);
int sharedArr[3];
int bufferSize = 0;
sem_t filled_bin;
sem_t empty_bin;
sem_t mutex;
sem_t timeMutex; //used to protect time array
//in theory each index of the array can have its own mutex, but one is used for simplicity
//[0] is producer delay, [1] is consumer, [2] is set to 1 when time to quit
int main (int argc, char* argv[]) {
	bufferSize = atoi(argv[1]);
	sharedArr[0] = atoi(argv[2]);
	sharedArr[1] = atoi(argv[3]);
	sharedArr[2] = 0;
	int* itemBuffer = (int*) malloc(bufferSize * sizeof(int));
	itemBuffer[1] = 2;
	pthread_t produce;
	pthread_t consume;

	sem_init(&filled_bin, 0, 0);
	sem_init(&empty_bin, 0, bufferSize);
	sem_init(&mutex, 0, 1);
	sem_init(&timeMutex, 0, 1);

	pthread_create(&produce, NULL, produceItem, itemBuffer);
	pthread_create(&consume, NULL, consumeItem, itemBuffer);

	char input = 'b';
	while(input != 'q') {
		scanf(" %c", &input);
		sem_wait(&timeMutex);
		switch (input) {
			case 'a':
				sharedArr[0] = sharedArr[0] + 250;
				break;
			case 'z':
				sharedArr[0] = sharedArr[0] - 250;
				break;
			case 's':
				sharedArr[1] = sharedArr[1] + 250;
				break;
			case 'x':
				sharedArr[1] = sharedArr[1] - 250;
				break;
			default:
				break;
		}
		sem_post(&timeMutex);
	}
	sem_wait(&timeMutex);
	sharedArr[2] = 1;
	sem_post(&timeMutex);
	int rc;
	int rd;
	pthread_join(consume,(void**) &rc);
	pthread_join(produce, (void**) &rd);
	printf("Done\n");
	sem_destroy(&filled_bin);
	sem_destroy(&empty_bin);
	sem_destroy(&mutex);
	sem_destroy(&timeMutex);
	free(itemBuffer);
	return 0;	
}
void* produceItem(void* arg) {
	int in = 0;
	int* buffer = (int*) arg;
	while( ! sharedArr[2]) {
		sem_wait(&timeMutex);
		useconds_t sleepTime = sharedArr[0];
		sem_post(&timeMutex);
		if(sleepTime < 0) {
			sleepTime = 0;
		}
		usleep(sleepTime * 1000);
		int randomNumber = (rand() % 9000) + 1000;
		sem_wait(&empty_bin);
		sem_wait(&mutex);
		buffer[in] = randomNumber;
		printf("Produced %d in index %d\n", randomNumber, in);
		in++;
		in %= bufferSize;
		sem_post(&mutex);
		sem_post(&filled_bin);
	}
	return NULL;
}
void* consumeItem(void* arg) {
	int out = 0;
	int* buffer = (int*) arg;
	while(1) {
		sem_wait(&timeMutex);
		useconds_t sleepTime = sharedArr[1];
		sem_post(&timeMutex);
		if(sleepTime < 0) {
			sleepTime = 0;
		}
		usleep(sleepTime * 1000);
		int empty = 1;
		int filled = 1;
		sem_getvalue(&empty_bin, &empty);
		sem_getvalue(&filled_bin, &filled);
		if(empty == bufferSize && filled == 0 && sharedArr[2] == 1) {
			break;
		}
		sem_wait(&filled_bin);
		sem_wait(&mutex);
		int consumed = buffer[out];
		printf("\t\t\tConsumed %d from index %d\n", consumed, out);
		out ++;
		out %= bufferSize;
		sem_post(&mutex);
		sem_post(&empty_bin);
	}
	return NULL;
}

