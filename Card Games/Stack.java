public class Stack<E> {
	Node<E> top;

	public Stack() {
		this.top = null;
	}

	public boolean isEmpty() {
		if (top == null) {
			return true;
		}
		return false;
	}

	public void push(E item) {
		if (item == null) {
			System.out.println("Some error???");
		}
		top = new Node<E>(item, top);
	}

	public E pop() throws StackException {
		if (top == null) {
			throw new StackException();
		}
		Node<E> temp = top;
		this.top = top.getNext();
		return temp.getItem();
	}

	public E peek() throws StackException {
		if (top == null) {
			throw new StackException();
		}
		Node<E> temp = top;
		return temp.getItem();
	}

	public void debug() {
		Node<E> curr = top;
		System.out.println();
		while (curr != null) {
			System.out.println(curr.getItem());
			curr = curr.getNext();
		}
		System.out.println();
	}

}