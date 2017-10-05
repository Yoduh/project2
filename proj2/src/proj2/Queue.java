package proj2;

public class Queue {
	
	public static final int N = 257;
	int front;
	int rear;
	Object[] queue;

	public Queue() {
		queue = new Object[N];
		front = 0;
		rear = 0;
	}
	
	public void enqueue(Object o) throws FullQueueException {
		if(size() == N - 1) {
			throw new FullQueueException("Queue is full.");
		}
		queue[rear] = o;
		rear = (rear + 1) % N;
	}
	
	public Object dequeue() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		Object o = queue[front];
		front = (front + 1) % N;
		return o;
	}
	
	public boolean isEmpty() {
		return front == rear;
	}
	
	public int size() {
		return (N - front + rear) % N;
	}
	
	
	
	
	public class EmptyQueueException extends Exception {
		
		private static final long serialVersionUID = -6182566293380126936L;
		
		public EmptyQueueException(String msg) {
			super(msg);
		}
	}
	
	public class FullQueueException extends Exception {
		
		private static final long serialVersionUID = -6182566293380126936L;
		
		public FullQueueException(String msg) {
			super(msg);
		}
	}

}
