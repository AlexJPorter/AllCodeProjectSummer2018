#include "coding.h"

int main(int argc, char * argv[])
{
	FILE * inf, * outf;
	short int ret;

	inf = fopen(argv[2], "r");
	outf = fopen(argv[3], "w");

	if(inf == NULL || outf == NULL)
	{
		puts("File open failed!");
		return 1;
	}

	if(argv[1][0] == 'c')
		ret = encode(inf, outf);
	else if(argv[1][0] == 'd')
		ret = decode(inf, outf);
	else
	{
		puts("Wrong commandline input!");
		puts("Usage: ./coding c|d input_file_name output_file_name");
		return 1;
	}

	if(ret == 0)
	{
		fclose(inf);
		fclose(outf);
		return 0;
	}
	else
	{
		puts("File open failed. Nothing is done!");
		return 1;
	}
}
