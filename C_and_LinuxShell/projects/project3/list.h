/* define a structure and functions to implement a linear linked list */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX 20

struct product {
    char name[MAX];
    float quantity_value;
    char quantity_unit[MAX];
    float price_value;
    char price_unit[MAX];
    struct product * next;
};

typedef struct product product;

// make a node and return a pointer to the node
product * makeNode();

// create a list.
product * create();

// display a list
void display(product *l);

// append a node to the tail of a list
// return the updated list
product * append(product *l, product * p);

// save list: save the data to a file
void save(product *l, char fn[]);

// load list: load data from a file, appending to the list l, return it.
product * load(product *l, char fn[]);

//build a node
product * buildNode(char name[], float quantity_value, char quantity_unit[], float price_value, char price_unit[]);

//count a list
int count(product *l);

//destroy a list
void destroy(product ** l);

//found a person with id
product * find (product *l, char name[]);

//Insert after
void insertAfter(product *l, char name[], product *p);

//delete a node
void deleteNode(product **l, char name[]);

//check on the uniqueness of id
int isUniqueID(product *l, char name[]);

void displayNode(product *l);
