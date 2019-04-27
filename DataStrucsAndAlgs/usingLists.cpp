#include <list>
#include <iostream>
#include <chrono>
// A simple program that uses lists
// as defined in the Standard Template Library in C++
// The program expects an integer n as an argument
// in the command line.
// That integer n is the size of a list that will be created
// by inserting the integers 0..n-1 in the list

using namespace std;


int main(int argc, char *argv[]) {

  if (argc != 2) {
    cout << "Usage: usingLists sizeOfTheList " << endl;
    return -1;
  }

  int n = atoi(argv[1]);

  list < int > aList;

  for(int i = 0; i < n;i++) {
    aList.push_back(i);
  }

  // Now look for the middle value in the list
  // How long does it take?
  int middle = n / 2;
  chrono::high_resolution_clock::time_point start = chrono::high_resolution_clock::now();
  chrono::high_resolution_clock::time_point finish;
  for(auto it = aList.begin(); it!= aList.end();it++) {
    if (*it == middle) {
      cout << "Found " << middle << endl;
      finish = chrono::high_resolution_clock::now();
      break;
    }
  }
  chrono::duration<double> time_span = chrono::duration_cast
    < chrono::duration < double >> ( finish - start );

  cout << "Took: " << time_span.count() << " seconds. " << endl;
}
