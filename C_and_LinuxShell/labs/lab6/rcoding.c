#include "coding.h"

/* encode characters into unsigned long integers and save the encoded integers
 * in a file.
 * charaters are read from a file.
 * infile: input file, contains plaintext.
 * outfile: output file, where encoded integers are saved.
 * return 0 on success, and 1 on failure.
 */
int encode(FILE * infile, FILE * outfile)
{
	// return if either of the file pointers is NULL
	if(infile == NULL || outfile == NULL)
		return 1;

	// Need to determine the size of unsigned long
	int ul_size = sizeof(unsigned long int);
	// Need to determine the size of char
	int ch_size = sizeof(unsigned char);

	// number holder and char holder
	unsigned long int ul_holder = 0;
	unsigned char ch_holder;

	int n = 0, ch_per_ul = ul_size / ch_size;
	int ch_size_bits = ch_size * 8;

	/* do the coding */
	while(fscanf(infile, "%c", &ch_holder) != EOF)
	{
		ul_holder = ul_holder | ch_holder;
		
		if (n < (ch_per_ul - 1))
			ul_holder = ul_holder << ch_size_bits;

		if ((++n) == ch_per_ul)
		{
			//done this int, save it & initialize for next one
			fprintf(outfile, "%lu, ", ul_holder);
			n = 0;
			ul_holder = 0;
		}
	}
	
	/* pad the last unsigned long integer with '\0' */
	if(ul_holder != 0)
	{
		ch_holder = '\0';
		while(1)
		{
			ul_holder = ul_holder | ch_holder;
			if((++n) >= ch_per_ul)
			{
				fprintf(outfile, "%lu, ", ul_holder);
				n = 0;
				ul_holder = 0;
				break;
			}
			else
			{
				ul_holder = ul_holder << ch_size_bits;
			}
		}
	}
	
	return 0;
}

/* decode unsigned long integers into chars and save them to another file.
 * infile: input file, contains unsigned long integers.
 * outfile: output file, used to save decoded characters (plain text).
 * return 0 on success and 1 on failure.
 */
int decode(FILE * infile, FILE * outfile)
{
	if(infile == NULL || outfile == NULL) {
		return 1;
	}
	// Need to determine the size of unsigned long
	int ul_size = sizeof(unsigned long int);
	// Need to determine the size of char
	int ch_size = sizeof(unsigned char);

	// number holder and char holder
	unsigned long int ul_holder = 0;
	unsigned char ch_holder;

	int n = 0, ch_per_ul = ul_size / ch_size;
	int ch_size_bits = ch_size * 8;
	unsigned char mask = 0;
	unsigned char ch = 0;
	unsigned long int ul;
	int i;

/*	while(fscanf(infile, "%lu, ", &ul_holder) != EOF)
	{
		//unsigned long mask = 4294967295 max value of unsigned long
		unsigned long firstMask = 4278190080; //11111111 3 * 8 0s
		unsigned long secondMask = 16711680; //0000000011111111 2 * 8 0s
		unsigned long thirdMask = 65280;
		unsigned long fourthMask = 255;
		
		char firstChar = ul_holder & firstMask;
		printf("%c", firstChar);
		char secondChar = ul_holder & secondMask;
		char thirdChar = ul_holder & thirdMask;
		char fourthChar = ul_holder & fourthMask;

		if(firstChar != '\0')
			fprintf(outfile, "%c", firstChar);
		if(secondChar != '\0')
			fprintf(outfile, "%c", secondChar);
		if(thirdChar != '\0')
			fprintf(outfile, "%c", thirdChar);
		if(fourthChar != '\0')
			fprintf(outfile, "%c", fourthChar);
	} */

	while(fscanf(infile, "%lu, ", &ul) != EOF) {
		for(i = ch_per_ul - 1; i >= 0; i--) {
			ch = (ul >> i * ch_size_bits) ^ mask;
			if(ch != '\0')
				fprintf(outfile, "%c", ch);
		}
	}
	unsigned long balls = 23498777487;
	char a = (balls << 1) ^ mask;
	fprintf(outfile,"\n\n%c", a);
	return 0;
}



