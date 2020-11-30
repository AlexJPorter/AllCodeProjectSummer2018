#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

#define READ 0
#define WRITE 1

void returnWordCount(char* fileName);

int main(int argc, char* argv[]) {

	int fileNames[2];
	int a = pipe(fileNames); //used for parent to communicate with children
	printf("%d\n", a);
	//int reports[2];
	//pipe(reports); //used for childern to send reports back to parent
	printf("Created pipes\n");	
	for(int i = 1; i < argc; i++) {
		dprintf(fileNames[1], "%s", argv[i]);
		printf("Printed %s\n", argv[i]);
	}
	//dprintf(fileNames[WRITE], "%d", 4);
	printf("Pushed files\n");
	pid_t who;
	int j = 1;
	while(j == 1 ||  (who && j < argc)) {
		who = fork();
		j++;
	}
	printf("forked a lot\n");
	if(!who) {
		printf("I'm a child\n");
		dup2(fileNames[READ], fileno(stdin));
		//dup2(fileNames[WRITE], fileno(stdout));
		close(fileNames[READ]);
		//close(fileNames[WRITE]);

		printf("did some duping\n");
		char fileName[30];
		//char* fileName = NULL;
		printf("Here now\n");
		//int readNumber;
		fgets(fileName, 30, stdin);
		//fscanf(stdin, "%d", &readNumber);
		//return readNumber;
		//int bytesRet = fread(fileName, 1, 29, stdin); 
		printf("%s is the file name\n", fileName);

		//returnWordCount(fileName);
		return 0;

		//close(fileNames[0]);
		//close(reports[1]);
	}
	else {
		//runs the parent
	
		printf("I'm the parent\n");
		j = 1;
		int r;
        	//while(j < argc) {
               // 	wait(&r);
		//	j++;
        	//}
		wait(&r);
		printf("I'm done %d \n", r);
	}

        return 0;
}
void returnWordCount(char* fileName) {
	printf("Inside word count\n");
}
