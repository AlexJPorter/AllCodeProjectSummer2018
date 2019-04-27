#include <stdio.h>
#include <stdlib.h>
#include "functions.h"
#include <ctype.h>
#include <string.h>

int main(int argc, char* argv[]) {
	char *key_array, *createdKey;
	char choice; //either e or d
	char ch;
	FILE *fin, *fout;

	if(argc != 5) {
		printf("Usage: p2 option key infile outfile\n");
		printf("Option must be 'd' or 'e' for decrypt/encrypt\n");
		exit(1);
	}

	choice = argv[1][0] - 'a';
	int keyLength = (int) strlen(argv[2]);
	key_array = (char*) malloc(sizeof(char) * keyLength);
	key_array = argv[2];
	char duplicates = countDups(keyLength, key_array);
	key_array = removeDuplicates(key_array, keyLength);
	createdKey = (char*) malloc(sizeof(char) * 26);
	createdKey = createKey(key_array, keyLength - duplicates);

	fin = fopen(argv[3], "r");
	fout = fopen(argv[4], "w");
	
	if(fin == NULL || fout == NULL) {
		printf("File could not be opened\n");
		exit(1);
	}
	if(choice == 4) {
		while(fscanf(fin, "%c", &ch) != EOF) {
			fprintf(fout, "%c", encryptLetter(ch, createdKey));
		}
	}
	if(choice == 3) {
		while(fscanf(fin, "%c", &ch) != EOF){
			fprintf(fout, "%c", decryptLetter(ch, createdKey));
		}
	}
	if(choice != 3 && choice != 4) {
		printf("Invalid choice, type 'e' or 'd'\n");
	}
	fclose(fin);
	fclose(fout);
	free(key_array);
	free(createdKey);
	return 0;
}
