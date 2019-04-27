package project4;

public class NewNode {
	//Nod clip being held
	private Node clipboard;
	//next clipboard pointer
	private NewNode nextClip;
	//index of clipboard
	private int myIndex;
	
	//create a new node with index, clip, and next clipboard
	public NewNode(int index, Node clip, NewNode next) {
		clipboard = clip;
		myIndex = index;
		next = nextClip;
	}
	
	//create a newNode from a newNode
	public NewNode(NewNode param) {
		clipboard = param.getClip();
		nextClip = param.getNext();
		myIndex = param.getIndex();
	}
	
	//getters and setters
	public void setClipboard(Node clipboard) {
		this.clipboard = clipboard;
	}

	public void setNextClip(NewNode nextClip) {
		this.nextClip = nextClip;
	}

	public void setMyIndex(int myIndex) {
		this.myIndex = myIndex;
	}
	
	public Node getClip() {
		return clipboard;
	}
	
	public NewNode getNext() {
		return nextClip;
	}
	
	public int getIndex() {
		return myIndex;
	}
	
}

