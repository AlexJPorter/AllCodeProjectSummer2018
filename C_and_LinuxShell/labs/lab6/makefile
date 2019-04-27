lab6: coding.h coding.o lab6.o
	gcc -o coding coding.o lab6.o

lab6.o: coding.h lab6.c
	gcc -c lab6.c

coding.o: coding.h coding.c
	gcc -c coding.c

encode:
	./coding c data.let data.num

decode:
	./coding d data.num rdata.let

test:
	./coding c coding.c coding.num
	./coding d coding.num rcoding.c
	diff -s coding.c rcoding.c

clean:
	rm -f *.o coding rdata.* *.num rcoding.c
