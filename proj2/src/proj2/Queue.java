package proj2;

/**
 * Custom implementation of Queue data structure for use in helping
 * construct level-order traversal string of a general tree
 * @author aehandlo
 *
 */
public class Queue {
	/** max size of queue is number of ascii characters (256) plus 1 to keep track of rear */
	public static final int N = 257;
	/** index of front of queue */
	int front;
	/** index of rear of queue */
	int rear;
	/** array to hold all queue elements */
	Object[] queue;

	/**
	 * Queue constructor creates empty queue
	 */
	public Queue() {
		queue = new Object[N];
		front = 0;
		rear = 0;
	}
	
	/**
	 * Enqueues element to end of queue
	 * @param o Element to enqueue
	 * @throws FullQueueException if queue is full
	 */
	public void enqueue(Object o) throws FullQueueException {
		if(size() == N - 1) {
			throw new FullQueueException("Queue is full.");
		}
		queue[rear] = o;
		rear = (rear + 1) % N;
	}
	
	/**
	 * Dequeues element from front of queue
	 * @return Dequeued element
	 * @throws EmptyQueueException if queue is empty
	 */
	public Object dequeue() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Queue is empty.");
		}
		Object o = queue[front];
		front = (front + 1) % N;
		return o;
	}
	
	/**
	 * Determine if queue is empty
	 * @return True if queue is empty, false if not
	 */
	public boolean isEmpty() {
		return front == rear;
	}
	
	/**
	 * Get size of queue
	 * @return Queue size
	 */
	public int size() {
		return (N - front + rear) % N;
	}
	
	
	
	/**
	 * Exception to throw if queue is empty
	 * @author aehandlo
	 */
	public class EmptyQueueException extends Exception {
		private static final long serialVersionUID = -6182566293380126936L;
		
		public EmptyQueueException(String msg) {
			super(msg);
		}
	}
	/**
	 * Exception to throw if queue is full
	 * @author aehandlo
	 */
	public class FullQueueException extends Exception {
		private static final long serialVersionUID = -6182566293380126936L;
		
		public FullQueueException(String msg) {
			super(msg);
		}
	}

}
