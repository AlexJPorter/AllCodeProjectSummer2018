#include <stdlib.h>
#include "functions.h"
#include <stdio.h>
#include <math.h>
#include <ctype.h>

char* removeDuplicates(char* key, int keyLength) {
	int dupeCounter = 0;
	int r;
	char tempNumber;
	for(r = 0; r < keyLength; r++) {
		tempNumber = *(key + r); // - 'A'
		*(key + r) = tempNumber;
	}
	int i;
	int j;
	for(i = 0; i < keyLength; i++) {
		for(j = 0; j < keyLength; j ++) {
			if(j != i && *(key + i) == *(key + j) && *(key + i) != 120) {
				*(key + j) = 120;
				dupeCounter ++;
			}
		}
	}
	char* newKey = (char*) malloc((keyLength - dupeCounter) * sizeof(char));
	int dup = 0;
	for(i = 0; i < keyLength; i++) {
		if( *(key + i) != 120) {
			*(newKey + i - dup) = *(key + i);
		}
		else {
		dup ++;
		}
	}	
	return newKey;
}

char* createKey(char* key, int keyLength) {
	int i;
	int j;
	char* extendKey = (char*) malloc((26 + keyLength) * sizeof(char));
	for(i = 0; i < 26; i++) {
		*(extendKey + i) = *(key + i);
	}
	for(i = 0; i < 26; i ++) {
		*(extendKey + i + keyLength) = 90 - i;
	}
	for(i = 0; i <keyLength; i ++) {
		for(j = keyLength; j < keyLength + 26; j++) {
			if(*(key + i) == *(extendKey + j) && i != j && *(extendKey + j) != 120) {
				*(extendKey + j) = 120;
			}
		}
	}
	char* newKey = (char*) malloc(26 * sizeof(char));
	char dup = 0;
	int t;
	for(t = 0; t < 26 + keyLength; t ++) {
		if(*(extendKey + t) == 120) {
			dup++;
		}
		else {
			*(newKey + t - dup) = *(extendKey + t);
		}
	}
	free(extendKey);
	return newKey;
}
char encryptLetter(char in, char* key) {
	int num = (int) in;
	num = num - 65;
	if(num < 0 || num > 90) {
		return in;
	}
	return *(key + num);
}

char decryptLetter(char in, char* key) {
	int num = (int) in;
	int i;
	for(i = 0; i < 26; i++) {
		if(num == *(key + i)) {
			return (i + 65);
		}
	}
	return in;
}
char countDups(char keylength, char* key) {
	char dupecounter = 0;
	char* working = (char*) malloc(keylength * sizeof(char));
	int i;
	for(i = 0; i < keylength; i ++) {
		*(working + i) = *(key + i);
	}
	int j;
	for(i = 0; i < keylength; i ++) {
		for(j = 0; j < keylength; j++) {
			if(*(working + i) == *(working + j) && *(working + i) != 120 && i != j) {
				*(working + i) = 120;
				dupecounter += 1;
			}
		}
	}
	free(working);
		return dupecounter;
}
