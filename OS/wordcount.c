#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>
#include <sys/wait.h>

#define READ 0
#define WRITE 1
//struct for file data
struct wcReport {
	int lines;
	int words;
	int bytes;
	int maxWidth;
	char filename[32];
};

//Because this isn't "normal" functionality, I'm sending to stderr
void sigHandle(int a) {
	fprintf(stderr, "Signal received\n");
}
//counts number of words, returns int
int countWords(char* line) {
	int words = 0;
	char* iter = line;
	int inword = 0;

	do switch(*iter) {
		case '\0':
		case ' ': case '\t': case '\n': case '\r':
			if(inword) {
				inword = 0;
				words++;
			}
			break;
		default:
			inword = 1;
	} while(*iter++);
	return words;
}
//builds the rest of the report
struct wcReport parseFile(char* fileName) {

	char* buffer;
	size_t bufsize = 128;
	int chars;
	FILE *filePointer;

	buffer = (char *) malloc(bufsize * sizeof(char));
	if(buffer == NULL) {
		printf("Malloc failed\n");
		exit(1);
	}
	filePointer = fopen(fileName, "r");
	if(filePointer == NULL) {
		struct wcReport error;
		error.lines = 0;
		error.words = 0;
		error.bytes = 0;
		error.maxWidth = 0;
		strcpy(error.filename, fileName);
		fprintf(stderr, "%s file not found\n", fileName);
		free(buffer);
		return error;
	}
	
	int totalBytes = 0;
	int totalLines = 0;
	int totalWords = 0;
	int maxLength = 0;

	//getline returns number of chars read
	chars = getline(&buffer, &bufsize, filePointer);
	while(chars > 0) {
		totalLines = totalLines + 1;
        	totalBytes = totalBytes + chars;
		totalWords = totalWords + countWords(buffer);
		if(chars > maxLength) {
			maxLength = chars;
		}
		chars = getline(&buffer, &bufsize, filePointer);
	}
	struct wcReport toReturn;
	toReturn.lines = totalLines;
	toReturn.words = totalWords;
	toReturn.bytes = totalBytes;
	toReturn.maxWidth = maxLength - 1;
	strcpy(toReturn.filename, fileName);
	free(buffer);
	fclose(filePointer);
	return toReturn;

}
int main(int argc, char* argv[]) {
	if(argc < 2) {
		printf("No files presented, add files as arguments to count words.\n");
		return 0;
	}

	//create pipes that children read from and write to
	int* readPipeArray = malloc(argc * sizeof(int));
	int* writePipeArray = malloc(argc * sizeof(int));
	pid_t who = 0;
	int j = 1;
	//save stdout for later
	int save;
	save = dup(STDOUT_FILENO);
	signal(SIGUSR1, sigHandle);
	pause(); //before parent sends file names
	while(j == 1 || (who && j < argc)) {
		if(strcmp(argv[j], "-L") == 0) {
			j++;
		}
		//create read and write pipes for children
		int fileNames[2];
		pipe(fileNames);

		dup2(fileNames[WRITE], STDOUT_FILENO);
	        dup2(fileNames[READ], STDIN_FILENO);

        	close(fileNames[READ]);
        	close(fileNames[WRITE]);

		int resultPipe[2];
		pipe(resultPipe);
		readPipeArray[j - 1] = resultPipe[READ];
		writePipeArray[j - 1] = resultPipe[WRITE];
		//write file arg to pipe
		fprintf(stdout, "%s\n", argv[j]);
		fflush(stdout);
		//after fork, the loop exits and j is used for referencing pipe array
		who = fork();
		j++;
	}
	if(!who) {
		char fileNameBuffer[32]; //32 seems like a fine limit for filename length
		//stdin is already set to the read pipe
		fscanf(stdin, "%s", fileNameBuffer);
		struct wcReport report = parseFile(fileNameBuffer);
		pause(); //before child reports to parent
		//write to the appropriate pipe arrray
		write(writePipeArray[j-2], &report, (size_t) sizeof(struct wcReport));
		close(writePipeArray[j-2]);
		free(readPipeArray);
		free(writePipeArray);
		return 0;
	}
	else {
		struct wcReport received;
		int totalLines = 0;
		int totalWords = 0;
		int totalBytes = 0;
		int totalWidths = 0;
		j = 1;
		//restore stdout
		dup2(save, STDOUT_FILENO);
		close(save);
		while(j < argc) {
			if(strcmp(argv[j], "-L") == 0) {
				j++;
			}
			//read from each pipe once the child exits
			int rec;
			wait(&rec);
			read(readPipeArray[j-1], &received, (size_t) sizeof(struct wcReport));
			totalLines = totalLines + received.lines;
			totalWords = totalWords + received.words;
			totalBytes = totalBytes + received.bytes;
			if(received.maxWidth > totalWidths) {
				totalWidths = received.maxWidth;
			}
			//output depends on -L
			if(strcmp(argv[1], "-L")) {
				fprintf(stdout, "%8d %8d %8d %s\n", received.lines, received.words, received.bytes, received.filename);
			}
			else {
				fprintf(stdout, "%8d %s\n", received.maxWidth, received.filename);
			}
			j++;
		}
		if(strcmp(argv[1], "-L")== 0 && argc > 3) {
			fprintf(stdout, "--------------------------\n");
			fprintf(stdout,"%8d %s\n", totalWidths, "totals");
		}
		else if(strcmp(argv[1], "-L") && argc > 2) {
			fprintf(stdout, "---------------------------\n");
			fprintf(stdout, "%8d %8d %8d totals\n",totalLines, totalWords, totalBytes);
		}

		free(readPipeArray);
		free(writePipeArray);
	}

	return 0;
}

