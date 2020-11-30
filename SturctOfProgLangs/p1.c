#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "functions.h"

/* Alex Porter
 * CIS 343 SEC 02
 *
 * This program reads in a text file from stdin, and prints the file to
 * the screen after reformatting the line length. This works for
 * justify and regular left align for a given length. If a word
 * is larger than the entire line length, the user will be notified.
 * 
 * */
int main(int argc, char* argv[]) {
	char read_in[100];
	char* words;
	int running_length = 0;
	int overflow_counter = 0;
	//check if there are input errors
	int check = errorCheck(argc, argv);
	if(check !=0) {
		return 1;
	}

	//create and init to_print buffer for -j
	int row_length = atoi(argv[2]);
	char * to_print = (char*) malloc(sizeof(char) * row_length);
	strcpy(to_print, "");
	int word_count = 0;
	//read in each line
	while(fgets(read_in, sizeof(char) * 101, stdin) != NULL) {
		//if the line is just a \n, go to the next line
		if(read_in[0] == 13 || read_in[0] == 10) {
			printf("%s", to_print);
			printf("\n\n");
			running_length = 0;
			strcpy(to_print, "");
			word_count = 0;
			running_length = 0;
			continue;
		}
		else {
			//chop the \n off the end of the line
			read_in[strlen(read_in) - 1] = 0;
		}
		if(strcmp(argv[1], "-w") == 0) {
			words = strtok(read_in, " ");
			while(words != NULL) {
				//print each word until the
				//row would be full
				if(running_length + strlen(words) 
						< row_length) {
					running_length = 
					parse_beginw(running_length, 
						words, row_length);
				}
				else {
					//print the last word on a new
					//line, update overflow if
					//needed
					overflow_counter = 
					parse_endw(words,
					row_length, overflow_counter);
					running_length = strlen(words);
				}
				words = strtok (NULL, " ");
			}	
		}
		else {
			//use str_tokr for multiple tracking
			char* line_track = NULL;
			words = strtok_r(read_in, " ", &line_track);
			while(words != NULL) {
				//update overflow first
				if(strlen(words) > row_length) {
					overflow_counter = overflow_counter + 1;
				}
				//add word to buffer
				if(running_length + strlen(words)
					       	< row_length) {
					running_length = 
					parse_beginj(running_length, 
					words, row_length, to_print);
					word_count = word_count + 1;
				}
				else {
					// check if next word is too
					// large.
					if(strlen(words) >= row_length
					|| word_count == 1) {
						if(word_count == 1) {
						     // if there's a
						     // word in the 
						     // buffer, print
						     // it and then
						     // whatever word 
						     // we are on. Then
						     // continue with
						     // next word.
						      printf("%s\n%s\n", 
						      to_print, words);
						      word_count = 0;
						      running_length = 0;
						      strcpy(to_print, 
						      "");
						      words = 
						      strtok_r(NULL, 
						      " ", &line_track);
							continue;
						}
						//if the word is too big
						if(strlen(words) >= 
						row_length) {
						//print the buffer and
						//the word if there
						//is anything in the
						//buffer.
						  if(strlen(to_print) >
								  0) {
							printf("%s\n%s\n", 
							to_print, 
							words);
							}
						  //otherwise print 
						  //the word. Update
						  //overflow for each
							else {
							printf("%s\n", 
							words);
							}

						}
					}
					else {
						//next word will fill
						//the line. So we need
						//to print and clear 
						//the buffer and any
						//tracking variables. 
						parse_endj(words, 
						row_length, 
						overflow_counter, 
						word_count, 
						to_print);
						strcpy(to_print, "");
						word_count = 0;
						running_length = 0;
						//re parse the current
						//word so we don't
						//miss it.
						continue; 
						}

				}
				words = strtok_r(NULL, " ", 
						&line_track);
			}
		}
	}
	//print the end of the buffer at the end of the file
	if(strcmp(argv[1], "-j") == 0) {
		printf("%s", to_print);
		if(overflow_counter > 0) {
			printf("\n");
		}
	}
	//free pointers
	free(words);
	free(to_print);
	//if there's overflow, let them know
	if(overflow_counter > 0) {
		fprintf(stderr, "Warning: %d overfull line(s)\n", 
				overflow_counter);
	}
	//success
	return 0;
}
