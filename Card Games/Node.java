class Node<E>{
	E item;
	Node<E> next;
	
	Node(E item, Node<E> next) {
		this.item=item;
		this.next=next;
	}
	
	Node(E item) {
		this.item=item;
		this.next=null;
	}
	
	Node() {
		this.item=null;
		this.next=null;
	}

	public E getItem() {
		if (item == null) {
			return null;
		}
		return this.item;
	}

	public Node<E> getNext() {
		if (next == null) {
			return null;
		}
		return this.next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}
	
}