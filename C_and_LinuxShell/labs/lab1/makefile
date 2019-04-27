lab1: lab1.o lab1.h main.o
	gcc -o lab1 lab1.o main.o

main.o: lab1.h lab1.c main.c
	gcc -c main.c

lab1.o: lab1.h lab1.c
	gcc -c lab1.c

run:
	./lab1

clean:
	rm -f lab1.o main.o lab1
