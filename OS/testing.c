#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int main() {
	int ret;
	struct tm info;
	char buffer[80];

	info.tm_year = 2020 - 1900;
	info.tm_mon = 9 - 1;
	info.tm_mday = 9;
	info.tm_hour = 0;
	info.tm_min = 0;
	info.tm_sec = 1;
	info.tm_isdst = -1;

	ret = mktime(&info);
	if (ret == -1) {
		printf("Error\n");
	}
	else {
		strftime(buffer, sizeof(buffer), "%c", &info);
		for(int i = 0; i < 4; i++) {
			printf("%c", buffer[i]);
		}
	}
	time_t rawtime;
	struct tm *apple;
	time(&rawtime);
	apple = localtime( &rawtime);
	printf("%s", asctime(apple));
	return 0;
}
