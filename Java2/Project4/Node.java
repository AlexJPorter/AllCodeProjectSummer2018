package project4;

public class Node {
	//hold data as string
	private String data;
	
	//next node pointer
	private Node nextNode;
	
	//Create a node from another node
	Node(Node n) {
		data = n.getData();
		nextNode = n.getNext();
	}
	
	//create a node with data s and pointer next
	Node(String c, Node next) {
		data = c;
		nextNode = next;
	}
	//setters and getters
	public void setNext(Node next) {
		nextNode = next;
	}
	public void setData(String c) {
		data = c;
	}
	public Node getNext() {
		return nextNode;
	}
	public String getData() {
		return data;
	}
	//return a string of data and each pointer's data
	public String toString() {
		String toReturn = "";
		Node temp = this;	
		while(temp.getNext() != null) {
			toReturn += temp.getData();
			temp = temp.getNext();
		}
		return toReturn;
	}
}
