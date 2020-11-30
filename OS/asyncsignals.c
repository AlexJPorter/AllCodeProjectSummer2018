#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

pid_t who;
int running = 1;

void sigHandleParent1(int received) {
	printf("\t\treceived a SIGURS1 signal\n");
}
void sigHandleParent2(int received) {
        printf("\t\treceived a SIGURS2 signal\n");
}
void sigHandleParentInt(int received) {
	printf("\t\t^C received.");
        printf("That's it, I'm shutting you down\n");
        running = 0;
        kill(SIGUSR1, who);
}
void sigHandleChild1(int received) {
        running = 0;
}


int main() {

	who = fork();

	if(!(who)) {
		signal(SIGUSR1, sigHandleChild1);
		int random;
		int sleepTime;
		pid_t parentPID = getppid();
		while(running)  {
			random = rand();
			sleepTime = (random % 5) + 1;
			sleep(sleepTime);
			if(!(running)) {
				break;
			}
			if(random % 2) { //random is odd
				kill(parentPID, SIGUSR1);
			}
			else {
				kill(parentPID, SIGUSR2);
			}
		}
		return 0;
	
	}
	else {
		signal(SIGUSR1, sigHandleParent1);
		signal(SIGUSR2, sigHandleParent2);
		signal(SIGINT, sigHandleParentInt);
		while(running) {
			printf("waiting...");
			pause();
		}
		int childReturn;
		kill(who, SIGUSR1);
		wait(&childReturn);
		return 0;
	}
}
