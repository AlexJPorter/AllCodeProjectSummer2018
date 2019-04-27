#include "list.h"

// create a list and return the pointer to it.
person * create()
{
    return makeNode();
}

// make a node and return a pointer to the node
// return a NULL pointer if failed.
person * makeNode()
{
    person * tmp = (person *) malloc(sizeof(person));
    if (tmp == NULL)
    {
        puts("InMakeNode: Memory allocation failed");
        return NULL;
    }
    fflush(stdin);
    printf("Please input first name: ");
    scanf("%s", tmp->first);
    fflush(stdin);
    printf("Please input last  name: ");
    scanf("%s", tmp->last);
    fflush(stdin);
    printf("Please input ID:         ");
    scanf("%d", &(tmp->id));
    fflush(stdin);
    tmp->next = NULL;

    return tmp;
}

// display a list
void display(person *l)
{
	person* temp = l;
	int i = 0;
	int r = 0;
	while(temp != NULL) {
		printf("Name: %s\n", temp->first);
		printf("Last Name: %s\n", (*temp).last);
		printf("ID: %i\n", temp->id);
		printf("===========================\n");
		temp = temp->next;
	}
}

/* check on the uniqueness of id on the list.
 * if yes, return 1; otherwise, return 0.
 */
int isUniqueID(person *l, person *p)
{
    person * tmp = l;
    while(tmp != NULL) {
        if((tmp->id) == (p->id))
            return 0;
        tmp = tmp->next;
    }
    return 1;
}

// append a node at the end of a list
// return the updated list
person * append(person *l, person * p)
{
	int j = 0;
	if((*l).first == NULL) {
		l = p;
	}
	else {
		while((*(l+j)).next != NULL) {
			//Going down the list
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
void save(person * l, char fn[])
{
    FILE * fout = fopen(fn, "w");
    if(fout == NULL) {
        printf("InSave: File open failed (%s)\n", fn);
        return;
    }
    person * current = l;
    while (current != NULL) {
        fprintf(fout, "%s %s %d\n", 
                current->first, current->last, current->id);
        current = current->next;
    }
    fclose(fout);
}

/*create a node with first name (fname), last name (lname) and ID, return it*/
person * buildNode(char fname[], char lname[], int id)
{
    person * p = (person *) malloc(sizeof(person));
    if(p == NULL) {
        puts("InBuildNode: Memory Allocation Failed!");
        return NULL;
    }
    strcpy(p->first, fname);
    strcpy(p->last, lname);
    p->id = id;

    return p;
}

/*load(): Load data from file fn, append the data to l
 *        and return the resulting list.
 */
person * load(person *l, char fn[])
{
    char fname[MAX], lname[MAX];
    int id, rt;
    person * head = l;
    FILE * fin = fopen(fn, "r");
    if(fin == NULL) {
        printf("InLoad: File open failed (%s)\n", fn);
        return NULL;
    }

    while (1) {
        rt = fscanf(fin, "%s %s %d\n", fname, lname, &id);
        if (rt < 3)
            break;
        if (head == NULL)
            head = buildNode(fname, lname, id);
        else
            append(head, buildNode(fname, lname, id));
    }
    fclose(fin);
    return head;
}

// count a list recursively
int count (person *l)
{
    if (l == NULL)
        return 0;
    else
        return (1 + count(l -> next));
}

/* destroy a list and release memory to the system */
void destroy (person **l)
{
    if(!(*l))
        return;
    destroy (&((*l)->next));
    free(*l);
    *l = NULL;
}

/* find a person with ID and return it */
person * find (person *l, int id)
{
	person* found;
	int i = 0;
	while ((l+i)->next != NULL) {
		if((*(l + i)).id == id) {
			return (l + i);
		}
	}
	return NULL;
	// find a person with the id from the list l and return 
	// a pointer to the found person.
	// if the person not found, return NULL pointer.
}

/* Insert a node after the person with the ID 
 * append this node if the person with the ID is not found
 */

void insertAfter(person *l, int id, person *p)
{
    person * tmp = find (l, id);
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
void deleteNode(person **l, int id)
{
    person * current = *l;
    person * previous = current;
    while(current != NULL) {
        if ((current->id) == id) {
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
