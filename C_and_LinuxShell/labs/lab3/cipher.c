#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char encrypt(char ch, int k);

int main(int argc, char* argv[])
{
      	char *key_array;	
	int choice, key;
	char ch;
	FILE *fin, *fout;

	if (argc != 5)
	{
		printf ("Usage: cipher option key infile, outfile\n");
		printf ("Option 1 for encryption and 2 for decryption\n");
		exit(1);
	}
	
	choice = atoi(argv[1]);
	int i;
	int letter;
	int keylength = (int) strlen(argv[2]);

	key_array = (char *)malloc(sizeof(char) * keylength);

	for(i = 0; i < keylength; i ++) {
		letter = (argv[2][i]) - 'A';
		key_array[i] = letter;
	}

	if (choice == 2) {
		int y;
		for(y = 0; y < keylength; y++) {
			key_array[y] = 0 - key_array[y];
		}
	}
		
	
    	fin = fopen(argv[3], "r");
	fout = fopen(argv[4], "w");
    
    	if (fin ==  NULL || fout == NULL) 
	{
		printf("File could not be opened\n");
		exit(1);
	}
	
	int counter = 0;
	while ( fscanf(fin, "%c", &ch) != EOF )
	{
		key = key_array[counter];
		fprintf(fout, "%c", encrypt(ch, key));
		counter = counter + 1;
		counter = counter % keylength;
	}

	fclose(fin);
	fclose(fout);
	free(key_array);
	return 0;
}

char encrypt(char ch, int k)
{
	if ( k < 0 )
		k = k + 26;

	if ( isupper(ch) )
		return (ch - 'A' + k) % 26 + 'A';
	
	if ( islower(ch) )
		return (ch - 'a' + k) % 26 + 'a';
	
	return ch;
}
