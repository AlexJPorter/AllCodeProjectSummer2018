/* define a structure and functions to implement a linear linked list */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX 20

struct person {
    char first[MAX];
    char last[MAX];
    int id;
    struct person * next;
};

typedef struct person person;

// make a node and return a pointer to the node
person * makeNode();

// create a list.
person * create();

// display a list
void display(person *l);

// append a node to the tail of a list
// return the updated list
person * append(person *l, person * p);

// save list: save the data to a file
void save(person *l, char fn[]);

// load list: load data from a file, appending to the list l, return it.
person * load(person *l, char fn[]);

//build a node
person * buildNode(char fname[], char lname[], int id);

//count a list
int count(person *l);

//destroy a list
void destroy(person ** l);

//found a person with id
person * find (person *l, int id);

//Insert after
void insertAfter(person *l, int id, person *p);

//delete a node
void deleteNode(person **l, int id);

//check on the uniqueness of id
int isUniqueID(person *l, person *p);
