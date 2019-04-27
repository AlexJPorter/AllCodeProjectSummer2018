#include <stdlib.h>
#include <stdio.h>

char* removeDuplicates(char* key, int keyLength);
char* createKey(char* key, int keyLength); //This is an n^2 function where the first elements are the elements returned by the removeDuplicates method. Then the next element is 25 if the previous elements does not contain 25. Then 24, but only the first elements returned from removeDuplicates needs to be checked over and over again. The rest can be ignored. We will start with this method.
char encryptLetter(char in, char* key);
char decryptLetter(char in, char* key);
char countDups(char keylength, char* key);
