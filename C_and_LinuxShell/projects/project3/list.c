#include "list.h"
#include <string.h>

// create a list and return the pointer to it.
product * create()
{
    return makeNode();
}

// make a node and return a pointer to the node
// return a NULL pointer if failed.
product * makeNode()
{
    product * tmp = (product *) malloc(sizeof(product));
    if (tmp == NULL)
    {
        puts("InMakeNode: Memory allocation failed");
        return NULL;
    }
    fflush(stdin);
    printf("Please input name: (string)  ");
    scanf("%s", tmp->name);
    fflush(stdin);
    printf("Please input quantity: (float)  ");
    scanf("%f", &(tmp->quantity_value));
    fflush(stdin);
    printf("Please input unit: (string)  ");
    scanf("%s", tmp->quantity_unit);
    fflush(stdin);
    printf("Please input price: (float) ");
    scanf("%f", &(tmp->price_value));
    fflush(stdin);
    printf("Please input price unit: (string) ");
    scanf("%s", tmp->price_unit);
    fflush(stdin);
    tmp->next = NULL;

    return tmp;
}

// display a list
void display(product *l)
{
	int j =0;
	product* temp = l;
	while(temp != NULL) {
		displayNode(temp);
		temp = temp->next;
	}
	return;
	//Display all the node on the list l.
	//for each node, diplay first name, last name and id
}
void displayNode(product *l)
{
	printf("Name: %s\n", l->name);
	printf("Quantity available: %.2f\n", l->quantity_value);
	printf("Sold by the: %s\n", l->quantity_unit);
	printf("Price: %.2f\n", l->price_value);
	printf("..per unit: %s\n", l->price_unit);
	printf("=================================\n");
	return;

}

/* check on the uniqueness of id on the list.
 * if yes, return 1; otherwise, return 0.
 */
int isUniqueID(product *l, char in[])
{
    product * tmp = l;
    while(tmp != NULL) {
        if((strcmp(tmp->name, in)) == 0)
            return 0;
        tmp = tmp->next;
    }
    return 1;
}

// append a node at the end of a list
// return the updated list
product * append(product *l, product * p)
{
	int j = 0;
	if(l->name == NULL) {
		l = p;
	}
	else {
		while((*(l+j)).next != NULL) {
			j++;
		}
		(*(l+j)).next = p;
	}
	//append a node that this pointed by p to the end of a list named l if 
	//the list is not empty. Otherwise, make this node as the first node
	//of this list.
	//return the upated list.

	return l;
}

/* Save all the data on current list to a file named fn */
void save(product * l, char fn[])
{
    FILE * fout = fopen(fn, "w");
    if(fout == NULL) {
        printf("InSave: File open failed (%s)\n", fn);
        return;
    }
    product * current = l;
    while (current != NULL) {
        fprintf(fout, "%s %f %s %f %s\n", 
                current->name, current->quantity_value, current->quantity_unit, current->price_value, current->price_unit);
        current = current->next;
    }
    fclose(fout);
}

/*create a node with first name (fname), last name (lname) and ID, return it*/
product * buildNode(char pname[], float pquantity_value, char pquantity_unit[], float pprice_value, char pprice_unit[])
{
    product * p = (product *) malloc(sizeof(product));
    if(p == NULL) {
        puts("InBuildNode: Memory Allocation Failed!");
        return NULL;
    }
    strcpy(p->name, pname);
    strcpy(p->quantity_unit, pquantity_unit);
    strcpy(p->price_unit, pprice_unit);
    p->quantity_value = pquantity_value;
    p->price_value = pprice_value;

    return p;
}

/*load(): Load data from file fn, append the data to l
 *        and return the resulting list.
 */
product * load(product *l, char fn[])
{
    char pname[20], pquantity_unit[20], pprice_unit[20];
    int rt;
    float pquantity_value, pprice_value;
    product * head = l;
    FILE * fin = fopen(fn, "r");
    if(fin == NULL) {
        printf("InLoad: File open failed (%s)\n", fn);
        return NULL;
    }

    while (1) {
        rt = fscanf(fin, "%s %f %s %f %s\n", pname, &pquantity_value, pquantity_unit, &pprice_value, pprice_unit);
        if (rt < 3)
            break;
        if (head == NULL)
            head = buildNode(pname, pquantity_value, pquantity_unit, pprice_value, pprice_unit);
        else
            append(head, buildNode(pname, pquantity_value, pquantity_unit, pprice_value, pprice_unit));
    }
    fclose(fin);
    return head;
}

// count a list recursively
int count (product *l)
{
    if (l == NULL)
        return 0;
    else
        return (1 + count(l -> next));
}

/* destroy a list and release memory to the system */
void destroy (product **l)
{
    if(!(*l))
        return;
    destroy (&((*l)->next));
    free(*l);
    *l = NULL;
}

/* find a person with ID and return it */
product * find (product *l, char in[])
{
	int i = 0;
	product* temp = l;
	while(temp != NULL) {
		if(strcmp(temp->name, in) == 0) {
			return (temp);
		}
		temp = temp->next;
	}
	return NULL;
}

/* Insert a node after the person with the ID 
 * append this node if the person with the ID is not found
 */

void insertAfter(product *l, char name[], product *p)
{
    product * tmp = find (l, name);
    if(tmp != NULL) {
        p->next = tmp->next;
        tmp->next = p;
    }
    else
    {
	    append(l, p);
    }
}

/*Delete a node and release memory to system */
void deleteNode(product **l, char pname[])
{
    product * current = *l;
    product * previous = current;
    while(current != NULL) {
        if (strcmp((current->name), pname) == 0) {
            if(previous == current)  // the first node
                *l = (current->next);
            else // not the first one
                previous->next = current->next;
            
            free (current);
            return;
        }
        previous = current;
        current = current->next;
    }
}
