package project2;

import org.w3c.dom.Node;

public class ForSCreenShots {
	
	Object top = new Object();
	Object tail = new Object();

	public boolean remove(String s) {
		//target is the string
		//Case 0, the list is empty
		if(top == null) {
			return false;
		}
		//case 1, There's only one element
		if(top.getNext() == null && top.getData() == s) {
			top = tail = null;
			return true;
		}
		//if top.getData() != s and top = tail return false
		
		//case 2, you remove the top element
		if(top != tail && top.getData() == s) {
			top = top.getNext();
			return true;
		}
		Node temp = top;
		while(temp.getNext() != null) {
			if(temp.getNext.getNext.getData == s)
				temp.setNext()(temp.getNext().getNext());
			else
				temp = temp.getNext;
			
		}
		else {
		}
		//case 3, you remove the last element
		//case 4, you remove one of the middle elements
	}
}
