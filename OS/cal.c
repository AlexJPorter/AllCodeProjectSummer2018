#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>

//a function to print the calendar given the parameters
//month is 1-12, year is given year, numDays is a value 28-31, first day is 1-7 depending on weekday (sun,mon etc)

int febDays(int year) {
        //Returns the number of days in February given a year
	//Every fourth year except those divisible by 100 except those divisible by 400
        if(year % 400 == 0) {
                return 29;
        }
        if(year % 100 == 0) {
                return 28;
        }
        if(year % 4 == 0) {
                return 29;
        }
        return 28;
}

int numDays(int month, int year) {
	//Big switch converts month number to number of days
	switch(month) {
		case 1:
			return 31; //jan
		case 2:
			//february uses the above function
			return febDays(year);
		case 3:
			return 31; //mar
		case 4:
			return 30; //apr
		case 5:
			return 31; //may
		case 6:
			return 30; //jun
		case 7:
			return 31; //jul
		case 8:
			return 31; //aug
		case 9:
			return 30; //sep
		case 10:
			return 31; //oct
		case 11:
			return 30; //nov
		case 12:
			return 31; //dec
		default:
			return -1;
	}

}
int parseWeekDay(char* dayString) {
	//Converts a string representing the day to a number 1-7
	if(strcmp(dayString, "Sun") == 0) {
		return 1;
	}
	if(strcmp(dayString, "Mon") == 0) {
		return 2;
	}
	if(strcmp(dayString, "Tue") == 0) {
		return 3;
	}
	if(strcmp(dayString, "Wed") == 0) {
		return 4;
	}
	if(strcmp(dayString, "Thu") == 0) {
		return 5;
	}
	if(strcmp(dayString, "Fri") == 0) {
		return 6;
	}
	if(strcmp(dayString, "Sat") == 0) {
		return 7;
	}
	return -1;
}
int* givenTimeParse(int month, int year) {
	//return [numDays, firstDay] for the given month/year combination
	
	int safety;
	struct tm timeInfo;
	char buffer[80];
	
	//build the time struct given year and month
	timeInfo.tm_year = year - 1900;
	timeInfo.tm_mon = month - 1;
	//mday needs to be one so we know when to start printing the numbers
	////the rest doesn't matter
	timeInfo.tm_mday = 1;
	timeInfo.tm_hour = 0;
	timeInfo.tm_min = 0;
	timeInfo.tm_sec = 1;
	timeInfo.tm_isdst = -1; //Since we don't care about hourly time, this is fine

	//Create the time
	safety = mktime(&timeInfo);
	if(safety == -1) {
		//If the time creation fails an error is printed
		printf("Error\n");
		int* ret = 0;
		return ret;
	}
	else {
		//Copy the time string into the buffer
		strftime(buffer, sizeof(buffer), "%c", &timeInfo);
		//copyu the weekday into this string
		char weekDayStr[4];
		strncpy(weekDayStr, buffer, 3);

		//get number of days
		int numDaysR = numDays(month, year);
		
		//get the weekday number
		int weekDay = parseWeekDay(weekDayStr);
		//return the data needed for printcal
		static int ret[4];
		ret[0] = month;
		ret[1] = year;
		ret[2] = numDaysR;
		ret[3] = weekDay;

		return ret;
	}

}
int* currentTimeParse() {
	//extract current month and year and return givenTimeParse(month, year)
	
	//get current local time in a struct
	time_t epochTime;
	struct tm *timeStruct;
	time(&epochTime);
	timeStruct = localtime(&epochTime);
	
	//copy it into parsedtime
	char parsedTime[30];
	strcpy(parsedTime, asctime(timeStruct));
	//extract the month string
	char monthString[4];
	strncpy(monthString, (parsedTime + 4), 3);
	
	//Depending on the month string, set to the numeral

	int monthInt;
	if(strcmp(monthString, "Jan") == 0) {
		monthInt = 1;
	}
	else if(strcmp(monthString, "Feb") == 0) {
		monthInt = 2;
	}
	else if(strcmp(monthString, "Mar") == 0) {
		monthInt = 3;
	}
	else if(strcmp(monthString, "Apr") == 0) {
                monthInt = 4;
        }
        else if(strcmp(monthString, "May") == 0) {
                monthInt = 5;
        }
	else if(strcmp(monthString, "Jun") == 0) {
                monthInt = 6;
        }
        else if(strcmp(monthString, "Jul") == 0) {
                monthInt = 7;
        }
	else if(strcmp(monthString, "Aug") == 0) {
                monthInt = 8;
        }
        else if(strcmp(monthString, "Sep") == 0) {
                monthInt = 9;
        }
	else if(strcmp(monthString, "Oct") == 0) {
                monthInt = 10;
        }
        else if(strcmp(monthString, "Nov") == 0) {
                monthInt = 11;
        }
	else  {
		monthInt = 12;
	}

	//Get year, which is a numeral, and convert it
	char yearString[5];
	strncpy(yearString, (parsedTime + 20), 4);
	//printf("Year: %s\n", yearString);
	int yearInt = atoi(yearString);

	//pass into givenTimeParse to get the full array

	return givenTimeParse(monthInt, yearInt);

}
void printCal(int month, int year, int numDays, int firstDay) {
	//print intial box
	printf("|--------------------|\n");
	//print month centered
	switch(month) {
		case 1:
			printf("|    January %i    |\n", year);
			break;
		case 2:
			printf("|   February %i    |\n", year);
			break;
		case 3:
			printf("|     March %i     |\n", year);
			break;
		case 4:
			printf("|     April %i     |\n", year);
			break;
		case 5:
			printf("|      May %i      |\n", year);
			break;
		case 6:
			printf("|     June %i      |\n", year);
			break;
		case 7:
			printf("|     July %i      |\n", year);
			break;
		case 8:
			printf("|    August %i     |\n", year);
			break;
		case 9:
			printf("|   September %i   |\n", year);
			break;
		case 10:
			printf("|    October %i    |\n", year);
			break;
		case 11:
			printf("|   November %i    |\n", year);
			break;
		case 12:
			printf("|   December %i    |\n", year);
			break;
	}
	//print weekday initials
	printf("|Su Mo Tu We Th Fr Sa|\n|");

	//until the first day of the week, print 3 spaces
	for(int i = 1; i < firstDay; i++) {
		printf("   "); 
	}
	//print the numbers 1-9 and roll over at the end of the week
	int weekDay = firstDay;
	for(int i = 1; i < 10; i++) {
		if(weekDay == 7) {
			printf(" %i|\n|", i);
			weekDay = 1;
		}
		else {
			printf(" %i ", i);
			weekDay +=1;
		}
	}
	//print the 2 digit numbers and roll over at the end of the week
	for(int i = 10; i <= numDays; i++) {
		if(weekDay == 7) {
			printf("%i|\n|", i);
			weekDay = 1;
		}
		else {
			printf("%i ", i);
			weekDay +=1;
		}
	}
	//edge case where last day of month is saturday
	if(weekDay == 1) {
		printf("--------------------|\n");
	}
	//add spaces to close out the box
	else {
	
	
		for(int i = 7 - weekDay; i > 0; i --) {
			printf("   ");
		}
		printf("  |\n|--------------------|\n");
	}


	return;
}
int main(int argc, const char* argv[]) {
	//check valid number of args
	if(argc != 1 &&  argc != 3) {
		printf("Invalid number of arguments\n");
		return 1;
	}
	//current time if no args passed
	if(argc == 1) {
		//get array and pass to print
		int* app = currentTimeParse();
		printCal(*app, *(app + 1), *(app + 2), *(app + 3));
		return 0;
	}
	//given time if two args passed
	else {
		int monthIn = atoi(argv[1]);
		int yearIn = atoi(argv[2]);
		//validate numbers given
		if(monthIn < 1 || monthIn > 12 || yearIn < 1000 || yearIn > 9999) {
			printf("Invalid entries for month and year.\n");
			return 2;
		}
		//get array and pass to print
		int* app = givenTimeParse(monthIn, yearIn);
		printCal(*app, *(app + 1), *(app + 2), *(app + 3));
		return 0;

	}	
}
