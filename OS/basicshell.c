#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/resource.h>
#include <sys/time.h>

//Compiled with clang -Wall
//Alex Porter

int main() {
	//print commands and set up variables
	printf("Command~| ");
	char input[100];
	//can't be null for first while loop iteration
	char* tok;
	char* command;
	//will be passed to exec
	char* args[54];
	//the first free space in args
	int i = 1;
	int ret;
	//tracking unknown commands
	int unknownCommands = 0;

	//get input, limit 100 chars
	fgets(input, 100, stdin);
	
	//keep going until input is quit
	while(strcmp(input, "quit\n") != 0) {
		//get command to run and save it
		command  = strtok(input, " \n");
		tok = command;
		args[0] = command;
		//get the rest of the ouputs into args
		while(tok != NULL && i < 50) {
			tok = strtok(NULL, " \n");
			args[i] = tok;
			i += 1;
		}
		//set the last arg to null and fork. reset i also
		args[i] = NULL;
		i = 1;
		pid_t pid = fork();
	
		//runs if child
		if(pid == 0) {
			//run command
			execvp(args[0], args);
			//printf("Catastrophic failure\n");
			exit(-1);
		}
		else {
			//parent waits for child to finish execution
			wait(&ret);
			//If there's an error, let them know and chalk it up
			if(ret != 0) {
				printf("Command failed. Perhaps \"%s\" is not an actual command.\n", args[0]);
				unknownCommands += 1;
			}
			//get and print usage stats with rusage.
			struct rusage usage;
			getrusage(RUSAGE_CHILDREN, &usage);
			printf("Usage for command:\t CPU TIME: %ld.%03ld , INVOLUNTARTY CONTEXT SWITCHES: %ld\n\n", usage.ru_stime.tv_sec, usage.ru_stime.tv_usec, usage.ru_nivcsw);	

		}
		//print command, get input, return to top
		printf("Command~| ");
		fgets(input, 100, stdin);
	}
	//once terminated, let the user know the unknownCommands entered
	printf("|~ Shell Terminated ~|\n");
	printf("Unknown Commands entered: %i\n\n", unknownCommands);

	
	return 0;
}
