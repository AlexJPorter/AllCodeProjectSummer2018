p2: functions.h functions.o p2.o
	gcc p2.o functions.o -o p2
p2.o: functions.h p2.c
	gcc -c p2.c

test: testing.txt p2
	./p2 e TEST testing.txt testing2.txt
	./p2 d TEST testing2.txt testing3.txt
	diff -s testing.txt testing3.txt	
clean: p2
	rm p2 p2.o functions.o testing2.txt testing3.txt	
