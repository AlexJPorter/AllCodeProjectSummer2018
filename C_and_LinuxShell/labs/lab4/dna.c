#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define LEN 4

char* strnsub (char *p, int n);

int main()
{
    char line[] = "His textbook was bought from the bookstore";  
    char *p1, *p2;

    p1 = line;
    //set p1 to the beginning of string line;
    while (*p1 != '\0') 	
    {
	p2 = (p1 + 1);    
        //set p2 to the position immediately after p1
		
        while ( *p2 != '\0')	
        {
            if(strcmp(strnsub(p1, LEN), strnsub(p2, LEN)) == 0) 	 
	    //a match is found // use strncmp() to compare
            {
                printf("The original string is:\n%s\n", line);
                printf("The first substring:  %s\n", strnsub(p1, LEN));
                printf("The second substring: %s\n", strnsub(p2, LEN));
                return 0;
            }
	    p2 ++;    
            //advance p2 to the next position
        }
	p1++;
       // advance p1 to the next position
    }
    printf("No repeated patterns of length %d in the following string:\n%s\n",
            LEN, line);
    return 0;
}


// returns a string with the first n characters of string p

char* strnsub (char *p, int n)
{
	char* toReturn = (char*) malloc(sizeof(char) * (n + 1));
	int i;
	for(i = 0; i < n; i++) {
		*(toReturn + i) = *(p + i);
	}
	return toReturn;
    // write function definition here

}
