#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>

extern char *strtok_r(char *, const char *, char **);

int errorCheck(int arg_count, char* args_in[]) {
	//wrong args
	if(arg_count != 3) {
	 fprintf(stderr,
		 "Improper amount of arguments. Need 3, got %i.\n", 
		 arg_count);
	 return 1;
	}
	//wronf optoin
	if(strcmp(args_in[1], "-w") != 0 && 
			strcmp(args_in[1], "-j") !=0) {
		fprintf(stderr,
		"Wrong option provided, need -j or -w.\n");
		return 1;
	}
	//length contains non-digit
	int ch;
	for(ch =0; ch < strlen(args_in[2]); ch++) {
		if(!isdigit(args_in[2][ch])) {
			fprintf(stderr, 
			"Non-number passed in for length.\n");
			return 1;
		}
	}
	return 0;
}
//parse for -w, print each word followed by a space, unless
//the word added equals the row_length, then no space
int parse_beginw(int running_length, char* words, int row_length) {
	if(running_length + strlen(words) == row_length) {
		printf("%s", words);
		running_length = running_length + strlen(words);
	}
	else {
		printf("%s ", words);
		running_length = running_length + strlen(words) + 1;
	}
	return running_length;
}
//like above, but we add each word to the queue
int parse_beginj(int running_length, char* words, int row_length, 
		char* to_print) {
	if(running_length + strlen(words) == row_length) {
		strcat(to_print, words);
		running_length = running_length + strlen(words);
	}
	else {
		strcat(to_print, words);
		strcat(to_print, " ");
		running_length = running_length + strlen(words) + 1;
	}
	return running_length;

} 
//print a newline and the word, update overflow if needed
int parse_endw(char* words, int row_length, int overflow_counter) {
	if(strlen(words) > row_length) {
		printf("\n%s", words);
		overflow_counter = overflow_counter + 1;
	}
	else {
		printf("\n%s ", words);
	}
	return overflow_counter;
}
//print the buffer
int parse_endj(char* words, int row_length, int overflow_counter, 
		int word_count, char* to_print) { 
	//calculate spaces to print
	int leftover_space = row_length - (strlen(to_print) - 
			(word_count));
	int straight_spaces = leftover_space / (word_count - 1);
	int additional_spaces = leftover_space % (word_count - 1);
	int running_length = 0;

	//create word and parse pointers
	char* to_print_words;
	char* to_print_pointer = NULL;

	//go through each word in the buffer
	to_print_words = strtok_r(to_print, " ", &to_print_pointer);
	while(to_print_words != NULL) {
		//print the word
		printf("%s", to_print_words);
		running_length = running_length + 
			strlen(to_print_words);
		if(running_length >= row_length) {
			break;
		}
		if(word_count > 1) {
			//print the number of straight spaces
			int i;
			for(i = 0; i < straight_spaces; i++) {
				printf(" ");
				running_length = running_length + 1;
			}
			//print a remainder space if there is one
			if(additional_spaces > 0) {
				printf(" ");
				additional_spaces = 
					additional_spaces - 1;
				running_length = running_length + 1;
			}
		}
		//decrease word count
		word_count = word_count - 1;
		to_print_words = strtok_r(NULL, " ", &to_print_pointer);
	}
	//print a new line at the end of the line
	printf("\n");
	//clear to_print
	strcpy(to_print, "");
	return overflow_counter;
}
