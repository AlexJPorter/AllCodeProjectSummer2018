#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

struct wcReport {
	int lines;
	int words;
	int bytes;
};
void printWC(struct wcReport in) {
	printf("%d lines %d words %d bytes\n", in.lines, in.words, in.bytes); 

}
int main() {
	int fd[2];
	pipe(fd);
	struct wcReport onefile;
	onefile.lines = 1;
	onefile.words = 2;
	onefile.bytes = 3;
	write(fd[1], & onefile, (size_t) sizeof(struct wcReport));
	struct wcReport twoFile;
	twoFile.lines = 0;
	twoFile.words = 0;
	twoFile.bytes = 0;
	read(fd[0], &twoFile, (size_t) sizeof(struct wcReport));
	printWC(twoFile);
	return 0;	
}
