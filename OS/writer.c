#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <string.h>

#define FOO 4096

int main () {

    //set up and init memory
    int writeID;
    char *writePtr;

    int readID1;
    int readID2;
    char* read1Ptr;
    char* read2Ptr;
    key_t write_key = ftok("secret.txt", 42);
    key_t read1_key = ftok("read1.txt", 1);
    key_t read2_key = ftok("read1.txt", 2);

    writeID = shmget(write_key, FOO, IPC_CREAT | S_IRUSR | S_IWUSR);
    writePtr = shmat(writeID, 0, 0);

    readID1 = shmget(read1_key, 1, IPC_CREAT | S_IRUSR | S_IWUSR);
    read1Ptr = shmat(readID1, 0, 0);
    readID2 = shmget(read2_key, 1, IPC_CREAT | S_IRUSR | S_IWUSR);
    read2Ptr = shmat(readID2, 0, 0);
    *read1Ptr = 0;
    *read2Ptr = 0;
    //main running loop
    while(1){
    	while(*read1Ptr || *read2Ptr); //do nothing until both readers have read
    	printf("Enter message: ");
    	fgets(writePtr, FOO, stdin);
	if(writePtr[strlen(writePtr) - 1] == '\n') {
	    writePtr[strlen(writePtr) - 1] = '\0';
	}
    	*read1Ptr = 1;
    	*read2Ptr = 1;
        if(strcmp(writePtr, "quit") == 0) {
            break;
        }

    }
    while(*read1Ptr || *read2Ptr); //block until both readers exit
    //clean up memory
    shmdt(writePtr);
    shmdt(read1Ptr);
    shmdt(read2Ptr);

    shmctl(writeID, IPC_RMID, 0);
    shmctl(readID1, IPC_RMID, 0);
    shmctl(readID2, IPC_RMID, 0);

    return 0;
}
