#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <string.h>

#define FOO 4096

int main (int argc, char* argv[]) {
    //set up and find memory

    int writeID;
    char *writePtr;

    int readID1;
    char* read1Ptr;

    key_t my_key = ftok("secret.txt", 42);
    key_t read_key = ftok("read1.txt", atoi(argv[1]));

    writeID = shmget(my_key, FOO, S_IRUSR | S_IWUSR);
    writePtr = shmat(writeID, 0, 0);

    readID1 = shmget(read_key, 1, S_IRUSR | S_IWUSR);
    read1Ptr = shmat(readID1, 0, 0);
    //main loop
    while(1) {
    	while(*read1Ptr == 0); //do nothing until I can read
    	if(strcmp(writePtr, "quit") == 0) {
		*read1Ptr = 0;
		break;
	}
	//print then notify I'm ready for next message
    	printf("%s\n", writePtr);
    	*read1Ptr = 0;
    }
    printf("Reader returned.\n");
    return 0;
}

