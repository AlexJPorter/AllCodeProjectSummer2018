#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
	int fd[2];
	pipe(fd);
	dup2(fd[1], STDOUT_FILENO);
	dup2(fd[0], STDIN_FILENO);

	close(fd[0]);
	close(fd[1]);

	char apple[6];
	char* pushMe = "Test";

	fprintf(stdout, "%s ", pushMe);
	fflush(stdout);
	fprintf(stdout, "Pear ");
	fflush(stdout);

	fprintf(stderr, "Apple pushed into pipe\n");

	//fgets(apple, 6, stdin);
	fscanf(stdin, "%s", apple);
	//fread(apple, 6, 1, stdin);

	fprintf(stderr, "|%s| received from pipe.\n", apple);

	fscanf(stdin, "%s", apple);
	fprintf(stderr, "|%s| recevied from pipe.\n", apple);

	return 0;
}
