#include "list.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void displayMenu()
{
    puts("");
    puts("Here is the menu for this list.");
    puts("============================================================");
    puts("1: Inventory			2: Save store");
    puts("3: Add Product			4: Check Price");
    puts("5: Purchase Product		6: Clean product from store");
    puts("7: Count current list		8: Show products");
    puts("9: Done for today		10: Find product");

}
   
// main function to test the list operations
int main(int argc, char *argv[])
{
    float currentSales = 0.0;
    char input[20];
    float floInput = 0.0;
    product * head = NULL, *p;
    product * found = NULL;
    int choice, tmp, done = 0;
    system("clear");
    head = load(head, "data.txt");

    while (!done) {
        displayMenu();
        puts("What do you want to do?");
        fflush(stdin);
        scanf("%d", &choice);
        fflush(stdin);

        switch (choice)
        {
            case 1:
		printf("\n==========================\n");
                printf("Current Sales: %.2f\n", currentSales);
		printf("==========================\n");
		display(head);
                break;
            case 2:
                save(head, "data");
                break;
            case 3:
		fflush(stdin);
		printf("Maybe we have that in stock. What is the same of the product? ");
		scanf("%s", input);
		fflush(stdin);
		if(isUniqueID(head, input)) {
			printf("This is a new product.\n");
			head = append(head, makeNode());
		}
		else {
			printf("We have one of these already, I'll update the stock.\n");
			fflush(stdin);
			printf("How many? (float)  ");
			scanf("%f", &floInput);
			fflush(stdin);
			found = find(head, input);
			found->quantity_value += floInput;
			fflush(stdin);
		}
                break;
            case 4:
                fflush(stdin);
                printf("Input the name of the product whose price you want to check: ");
                fflush(stdin);
                scanf("%s", input);
                fflush(stdin);
		found = find(head, input);
		if(found == NULL) {
			printf("We do not have that product.\n");
		}
		else {
			printf("Price: %f\n", found->price_value);
			displayNode(found);
		}
                break;
            case 5:
                fflush(stdin);
                printf("Input the name of the product you want to buy: ");
                fflush(stdin);
                scanf("%s", input);
                fflush(stdin);
		if(isUniqueID(head, input)) {
			printf("We don't have that product.\n");
		}
		else {
			found = find(head, input);
			printf("We have %.2f units available\n", found->quantity_value);
			printf("How many do you want? ");
			fflush(stdin);
			scanf("%f", &floInput);
			fflush(stdin);
			if(found->quantity_value <= floInput) {
				float sale = 0.0;
				sale = found->quantity_value * found->price_value;
				currentSales += sale;
				printf("Transaction complete\n");
				deleteNode(&head, input);
				printf("Sold out\n");
				
			}
			else {
				float sale = 0.0;
				sale = found->price_value * floInput;
				currentSales += sale;
				found->quantity_value -= floInput;
				printf("Transaction complete\n");
			}
		}
		fflush(stdin);
                break;
            case 6:
		printf("Input the name of the product to remove: ");
		fflush(stdin);
		scanf("%s", input);
		fflush(stdin);
		if(isUniqueID(head, input)) {
			printf("That is not in the store.\n");
		}
		else {
			deleteNode(&head, input);
			printf("Product removed\n");
		}
                break;
            case 7:
                printf("\nNumber of nodes on list: %d\n", count(head));
                break;
            case 8:
                display(head);
                break;
            case 9:
                puts("Thank you. Bye.");
                done = 1;
		save(head, "data.txt");
                break;
            case 10:
                printf("Input the name of the node you wish to find: ");
		fflush(stdin);
		scanf("%s", input);
		fflush(stdin);
		if(isUniqueID(head, input)) {
			printf("That product is not in the store\n");
		}
		else {
			found = find(head, input);
			displayNode(found);
		}
		break;
            default:
                puts("Wrong code. Please try again.");
                break;
        }
    }

    return 0;
}

