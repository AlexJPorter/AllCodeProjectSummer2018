p3: list.h list.o p3.o
	gcc -o p3 list.o p3.o
p3.o: list.h p3.c
	gcc -c p3.c
list.o: list.h list.c
	gcc -c list.c
run:
	./p3
clean:
	rm -f *.o p3
	
