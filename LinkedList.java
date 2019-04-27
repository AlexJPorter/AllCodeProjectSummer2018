
package project4;
/**********************************************************************
 * This class holds the operations of a LinkedList, plus
 * clipboard functionality. Includes operations that modify
 * the list plus some methods that help with operations. See
 * method comments for details on each. 
 * 
 * @author Alex Porter
 * @version 4/11/2018
 *********************************************************************/
public class LinkedList {
	/** Holds the node that is operated on*/
	private Node top;
	
	/** Holds the top of a clipboard linked list*/
	private NewNode clipper;

	/******************************************************************
	 * This simple constructor initializes top and clipper.
	 * 
	 * @param node Node replaces clipper
	 *****************************************************************/
	public LinkedList(Node node) {
		top = node;
		//this.message = message;
		clipper = new NewNode(0, null, null);
	}

	/******************************************************************
	 * Run at the end of mixMessage, this quits the program
	 *****************************************************************/
	public void quitMix() {
		System.exit(0);
	}

	/******************************************************************
	 * This method inserts a string before given postion in the linked 
	 * list. It is also called by bInsertAfter to insert after the 
	 * position. It scrolls through the list until it finds the given
	 * position. then it converts the string to a Node. It then 
	 * sandwiches the converted string between the before the pos,
	 * and after the pos.
	 * 
	 * @param s String to be inserted
	 * @param pos Position to insert before
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void bInsert(String s, int pos) {
		
		//checks if position is valid
		if(errorCheck(pos)) {
			throw new IllegalArgumentException();
		}
		else {
			int counter = 0;
			//case 0, list is empty
			if(top == null) {
				return;
			}
			//case 1, insert before top
			else if (pos == 0) {
				Node newTop  = convertToLinkedListInsert(s);
				Node temp = newTop;
				while(temp.getNext() != null) {
					temp = temp.getNext();
				}
				temp.setNext(top);
				top = newTop;
				return;
			}
			//runs if string is only length one
			else if(s.length() == 1) {
				Node previousNode = null;
				Node temp = top;

				while(temp != null) {
					if(counter == pos)
						previousNode.setNext(new Node(s, temp));
					previousNode = temp;
					temp = temp.getNext();
					counter += 1;
				}
			}
			else {
				Node previousNode = null;
				Node temp = top;
				counter = 0;
				while(temp != null) {
					//find before pos
					if(counter == pos - 1) {
						previousNode = temp.getNext();
						//convert to node and set it after position
						Node toSet = new 
								Node(convertToLinkedListInsert(s));
						Node toSetTemp = toSet;
						while(toSetTemp.getNext() != null) {
							toSetTemp = toSetTemp.getNext();
						}
						//set the rest after the end of the new string
						toSetTemp.setNext(previousNode);
						temp.setNext(toSet);
						return;
					}
					//moves down the list
					previousNode = temp;
					temp = temp.getNext();
					counter += 1;
				}

			}
		}

	}
	/******************************************************************
	 * Inserts the string after the given position by calling bInsert.
	 *  
	 * @param s String to insert
	 * @param pos int position to insert after
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void bInsertAfter(String s, int pos) {
		//if pos is negative one, the errorCheck in bInsert won't
		//catch it, so it needs to be caught today
		if(pos == -1)
			throw new IllegalArgumentException();
		else
			bInsert(s, pos + 1);
	}
	/******************************************************************
	 * This private method is used by the remove(int int) method
	 * if the parameters are the same. It makes it easier to 
	 * understand.
	 * 
	 * @param pos Position to remove from list
	 *****************************************************************/
	private void remove(int pos) {
		Node temp = top;
		int counter = 0;
		//case 0, list is empty
		if(top == null) {
			return;
		}
		//remove the top
		else if(pos == 0) {
			top = top.getNext();
		}
		else {
			while(temp.getNext() != null) {
				//find the pos
				if(counter == pos - 1) {
					//skip one and set it to next
					temp.setNext(temp.getNext().getNext());
					break;
				}
				temp = temp.getNext();
				counter += 1;
			}
		}
	}
	/******************************************************************
	 * Removes the given range of ints, including those ints
	 * from the linked list. Finds node at pos1, and pos2, and removes
	 * them to connect the ends.
	 * 
	 * @param pos1 First position to remove
	 * @param pos2 Second postion to remove until.
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void remove(int pos1, int pos2) {
		//check for errors or negative
		if(errorCheck(pos1) || errorCheck(pos2)) {
			throw new IllegalArgumentException();
		}
		//switch pos1 and 2 if out of order
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		//do a simple remove if they're the same
		if(pos1 == pos2) {
			remove(pos1);
			return;
		}

		//create some nodes to store vals
		Node temp = new Node(top.getData(), top.getNext());
		Node atPos1 = null;
		Node atPos2 = null;
		int counter = 0;
		//if empty don't remove anything 
		if(top == null) {
			throw new IllegalArgumentException();
		}
		//if top is only one long
		else if(top.getNext() == null) {	
			if(pos1 == 0 && pos2 == 0) {				
				top = null;
			}
			//they aren't both 0
			else if(pos1 != pos2) {				
				throw new IllegalArgumentException();
			}
		}
		else {
			boolean bothIfs = false;
			while(temp != null) {	
				//find first position
				if(counter == pos1 - 1) {	
					//set the node at that position
					atPos1 = new Node(temp.getData(), temp.getNext());
				}
				//find second position
				else if(counter == pos2 + 1) {	
					//set the node at that position
					atPos2 = new Node(temp.getData(), temp.getNext());
					//if pos2 is at the end
					if(pos2 == getLength() - 1) {
						atPos2.setNext(null);
					}
					//both ifs have now been hit
					bothIfs = true;
				} 			
				//squich the two together
				if(bothIfs) {
					atPos1.setNext(atPos2);					
					break;
				}			
				temp = temp.getNext();			
				counter += 1;
			}
			Node newTemp = top;			
			counter = 0;			
			while(newTemp.getNext() != null) {
				//if you remove from the front, set top new node
				if(pos1 == 0) {
					top = new Node(atPos2.getData(), atPos2.getNext());
					return;
				}
				//else go through until you find the right position
				else if(counter == pos1 - 1) {
					newTemp.setNext(atPos1.getNext());
					return;
				}				
				newTemp = newTemp.getNext();				
				counter += 1;
			}

		}
	}
	
	/******************************************************************
	 * Takes the given character and moves it to the front of the list
	 * 
	 * @param pos int Character to move
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void moveToFront(int pos) {
		if(errorCheck(pos))
			throw new IllegalArgumentException();
		Node atPos = null;
		Node temp = top;
		int counter = 0;
		//if top is null do nothing
		if(top == null) {
		}
		while(temp.getNext() != null) {
			//find position
			if(counter == pos) {
				//create a new node
				atPos = new Node(temp.getData(), null);
				//set the next to top
				atPos.setNext(top);
				top = atPos;
				//remove the position that was moved
				remove(pos + 1);
				break;
			}
			temp = temp.getNext();
			counter += 1;
		}

	}

	/******************************************************************
	 * Takes two positions and swaps them in the list.
	 * 
	 * @param pos1 Position one to swap
	 * @param pos2 Position two to swap
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void swapPositions(int pos1, int pos2) {
		//check for errors
		if(errorCheck(pos1) || errorCheck(pos2))
			throw new IllegalArgumentException();
		
		//switch positions if out of place
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		Node temp = top;
		int counter = 0;
		String first = null;
		String second = null;
		//possible error if pos 1 is not smaller than pos 2
		while(temp.getNext() != null) {
			//find the first pos
			if(counter == pos1) {
				first = temp.getData();
			}
			//find the second pos
			else if(counter == pos2) {
				second = temp.getData();
			}
			temp = temp.getNext();
			counter += 1;
		}
		if(first == null || second == null) {
			throw new IllegalArgumentException();
		}
		//remove and insert the strings of the nodes
		remove(pos1);
		bInsert(second, pos1);
		remove(pos2);
		bInsert(first, pos2);
	}

	/******************************************************************
	 * Replaces a given range of ints with a different string.
	 * 
	 * (Although it looks like a combination of remove and insert,)
	 * (it's actually much more annoying and complicated than that.)
	 * (At least, from a bug causing standpoint)
	 * 
	 * @param pos1 Beginning of range to replace
	 * @param pos2 End of range to replace
	 * @param s String to replace given range
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void replace(int pos1, int pos2, String s) {
		if(errorCheck(pos1) || errorCheck(pos2))
			throw new IllegalArgumentException();
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		//if replacing from the end
		if(pos2 == getLength() - 1) {
			remove(pos1, pos2);
			bInsertAfter(s, pos1 - 1);
		}
		//replace from anywhere else
		else {
			remove(pos1, pos2);
			bInsert(s, pos1);
		}
	}

	/******************************************************************
	 * Takes a string and converts it to a node. Sets that node to top.
	 * 
	 * @param s String to be converted to top.
	 *****************************************************************/
	public void convertToLinkedListNew(String s) {
		//empty top
		top = new Node("", null);
		Node temp = top;

		//set first data point
		temp.setData(s.substring(0, 1));
		//for each char in string, create a new node and set its data
		for(int i = 1; i < s.length(); i++) {
			temp.setNext(new Node(s.substring(i,i + 1), null));
			temp = temp.getNext();
		}
		//set the last point in the list
		temp.setNext(new Node(s.substring(s.length() -1,
				s.length()), null));
	}

	/******************************************************************
	 * This method takes a string and converts it to a linkedkist.
	 * It then returns that linked list, unlike above.
	 * 
	 * @param s String to be converted
	 * @return Node Linked List of converted string
	 *****************************************************************/
	public Node convertToLinkedListInsert(String s) {
		Node atPos1 = new Node("", null);
		Node temp = atPos1;
		int counter = 0;
		//while there's letters in the string
		while(counter < s.length()) {
			//set the current node
			temp.setData(s.substring(counter, counter+1));
			if(counter + 1 < s.length()) {
				//make another node
				temp.setNext(new Node("", null));
			}
			//move one spot down the list
			temp = temp.getNext();
			counter += 1;
		}
		//return linked list version of string
		return atPos1;
	}
	/******************************************************************
	 * Removes a section from the linked list and copies it to a
	 * clipboard given by index. The Node stays there until
	 * it is replaced by some other node later. 
	 * 
	 * @param pos1 Beginning of range
	 * @param pos2 End of range
	 * @param index Clipboard to copy to
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void cut(int pos1, int pos2, int index) {
		if(errorCheck(pos1) || errorCheck(pos2))
			throw new IllegalArgumentException();
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		NewNode temp = clipper;
		while(temp.getNext() != null) {
			//if the index is already in use, replace it
			if(temp.getIndex() == index) {
				temp.setClipboard(getSnippet(pos1, pos2));
				return;
			}
			temp = temp.getNext();
		}
		//set the next clip to a new board with the given node snippet
		temp.setNextClip(new NewNode(index, 
				new Node(getSnippet(pos1, pos2)), null));
		//remove that snippet
		remove(pos1, pos2);
	}
	/******************************************************************
	 * Copies the given range to a new clipboard at index int index.
	 * 
	 * @param pos1 Beginning of range
	 * @param pos2 End of range
	 * @param index Clip index to copy to
	 * @throws IllegalArgumentException If pos out of range
	 *****************************************************************/
	public void copy(int pos1, int pos2, int index) {
		if(errorCheck(pos1) || errorCheck(pos2))
			throw new IllegalArgumentException();
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		NewNode temp = clipper;
		//go through NewNodes
		while(temp.getNext() != null) {
			//if board is in use, replace it
			if(temp.getIndex() == index) {
				temp.setClipboard(getSnippet(pos1, pos2));
				return;
			}
			temp = temp.getNext();
		}
		//or make a new board with given snippet 
		temp.setNextClip(new NewNode(index, 
				new Node(getSnippet(pos1, pos2)), null));
	}
	/******************************************************************
	 * This method pastes a node snippet from a clipboard
	 * before position pos.
	 * 
	 * @param pos Position to insert before
	 * @param clipIndex Index of clipboard to paste from
	 * @throws IllegalArgumentException() Throws clip index is null
	 *****************************************************************/
	public void paste(int pos, int clipIndex) {
		NewNode temp = new NewNode(clipper);
		while(temp.getIndex() != clipIndex) {
			//clip index could not be found
			if(temp.getNext() == null) {
				throw new IllegalArgumentException();
			}
			temp = temp.getNext();
		}
		//get a string of node
		String toInsert = temp.getClip().toString();
		
		//insert that string
		bInsert(toInsert, pos);

	}
	/******************************************************************
	 * Returns the length of the linked list (top)
	 * 
	 * @return int Length of linked list
	 *****************************************************************/
	public int getLength() {
		Node temp = new Node(top);
		//if it's empty
		if(top == null) {
			return 0;
		}
		int counter = 0;
		//for each element in list, add one
		while(temp.getNext() != null) {
			counter +=1;
			temp = temp.getNext();
		}
		return counter;

	}

	/******************************************************************
	 * Returns a node snippet from inside top within the given range.
	 * 
	 * @param pos1 Beginning of range
	 * @param pos2 End of range
	 * @return Node Node snippet inside range
	 * @throws IllegalArgumentException If pos is out of range
	 *****************************************************************/
	public Node getSnippet(int pos1, int pos2) {
		if(errorCheck(pos1) || errorCheck(pos2))
			throw new IllegalArgumentException();
		
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;		
		}
		//save the top as a string
		String saveTop = top.toString();
		
		//modify top and return it, then reset top to the save
		if(pos2 == getLength() - 1) {
			//don't remove from the end
		}
		else {
			remove(pos2 + 1, getLength() - 1);
		}
		if(pos1 == 0) {
			//don't remove from the front
		}
		else {
			remove(0, pos1 - 1);
		}
		//converted top node
		Node toReturn = new Node(top.getData(), top.getNext());
		//reset top
		convertToLinkedListNew(saveTop);
		//return converted node
		return toReturn;
	}

	/******************************************************************
	 * This simple method checks if the num given
	 * is negative or longer than the list
	 * 
	 * @returns boolean True if invalid, false if not
	 * @param int Num to check if in range
	 *****************************************************************/
	public boolean errorCheck(int firstNum) {
		if(firstNum > getLength() || firstNum < 0) {
			return true;
		}
		return false;
	}

	/******************************************************************
	 * Returns a spaced out String of the top node. So the
	 * user can easily see which character corresponds to
	 * each index.
	 * 
	 * @return string String spaced with numbers above it
	 *****************************************************************/
	public String displayString() {
		String toReturn = "";
		int counter = 0;
		Node temp = top;
		//print null if empty
		if(top == null) {
			toReturn += "null\n";
		}
		else {
			while(temp.getNext() != null) {
				if(counter < 10) {
					//print two spaces for each char
					toReturn += (counter + "  ");
				}
				else {
					//print one space after each char
					toReturn += (counter + " ");
				}
				temp = temp.getNext();
				counter += 1;
			}
			toReturn += "\n";
			temp = top;
			//print each letter eith two spaces in between
			while(temp.getNext() != null) {
				toReturn += (temp.getData() + "  ");
				temp = temp.getNext();
			}
		}
		return toReturn;
	}

	/******************************************************************
	 * Returns top.toString(). Used to save data in mix class.
	 * 
	 * @returns String Top node .toString()
	 *****************************************************************/
	public String sendNodeToString() {
		return top.toString();
	}
}
