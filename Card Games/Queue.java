public class Queue<E> {
	Node<E> top;

	public Queue() {
		this.top = null;
	}

	public boolean isEmpty() {
		if (top == null) {
			return true;
		}
		return false;
	}

	public void enqueue(E element) {
		if (element == null) {
			return;
		}
		Node<E> newnode = new Node<E>(element);
		Node<E> prev = null;
		Node<E> curr = top;
		while (curr != null) {
			prev = curr;
			curr = curr.getNext();
		}
		if (prev == null) {
			top = newnode;
		}
		else {
			prev.setNext(newnode);
		}
	}

	public E dequeue() {
		if (top == null) {
			return null;
		}
		Node<E> temp = top;
		top = top.getNext();
		return temp.getItem();
	}

	public E peek() {
		if (top == null) {
			return null;
		}
		return top.getItem();
	}

	public static void main(String[] args) {
		Queue<Integer> tail = new Queue<Integer>();
		tail.enqueue(1);
		tail.enqueue(2);
		tail.enqueue(3);
		while (!tail.isEmpty()) {
			System.out.println(tail.dequeue());
		}
	}
}