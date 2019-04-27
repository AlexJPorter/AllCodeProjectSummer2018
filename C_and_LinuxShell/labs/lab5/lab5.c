#include "list.h"

void displayMenu()
{
    puts("");
    puts("Here is the menu for this list.");
    puts("============================================================");
    puts("1: Load data			2: Save data");
    puts("3: Append a node		4: Insert a node after another");
    puts("5: Delete a node		6: Destroy current list");
    puts("7: Count current list		8: Display current list");
    puts("9: Exit				10: Create a list");
}
   
// main function to test the list operations
int main(int argc, char *argv[])
{
    person * head = NULL, *p;
    int choice, tmp, done = 0;
    system("clear");

    while (!done) {
        displayMenu();
        puts("What do you want to do?");
        fflush(stdin);
        scanf("%d", &choice);
        fflush(stdin);

        switch (choice)
        {
            case 1:
                head = load(head, "data");
                break;
            case 2:
                save(head, "data");
                break;
            case 3:
                head = append(head, makeNode());
                break;
            case 4:
                fflush(stdin);
                printf("Input the person ID you want to insert after: ");
                fflush(stdin);
                scanf("%d", &tmp);
                fflush(stdin);
                insertAfter(head, tmp, makeNode());
                break;
            case 5:
                fflush(stdin);
                printf("Input the person ID you want to delete: ");
                fflush(stdin);
                scanf("%d", &tmp);
                fflush(stdin);
                deleteNode(&head, tmp);
                break;
            case 6:
                destroy(&head);
                break;
            case 7:
                printf("\nNumber of nodes on list: %d\n", count(head));
                break;
            case 8:
                display(head);
                break;
            case 9:
                puts("Thank you. Buy.");
                done = 1;
                break;
            case 10:
                if (head == NULL)
                    head = create();
                else
                    puts("\nCase 10: A list already created.");
                break;
            default:
                puts("Wrong code. Please try again.");
                break;
        }
    }

    return 0;
}

