cipher: cipher.o
	gcc cipher.o -o cipher
cipher.o: cipher.c

test: test1 test2
compare: test
	diff -s data.txt data.bak
clean:
	rm -f cipher.o cipher data.out data.bak
test1:
	./cipher 1 5 data.txt data.out
test2:
	./cipher 2 5 data.out data.bak
