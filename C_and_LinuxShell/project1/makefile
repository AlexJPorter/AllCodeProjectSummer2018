sort: sort.o sort.h p1.o
	gcc -o sort sort.o p1.o
p1.o: sort.h sort.c p1.c
	gcc -c p1.c
sort.o: sort.c sort.h
	gcc -c sort.c
run:
	./sort
clean:
	rm -f sort.o sort p1.o
