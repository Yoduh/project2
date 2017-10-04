package proj2;

import java.util.Iterator;
import java.util.LinkedList;

/** Concrete implementation of a general tree using a node-based, linked structure. */
public class Tree<E> {
	
	protected Node<E> root = null;
	private int size = 0;

	//---------------- nested Node class ----------------
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private LinkedList<Node<E>> children;
		
		public Node(E e, Node<E> above, LinkedList<Node<E>> below) {
			element = e;
			parent = above;
			children = new LinkedList<Node<E>>();
			if(below != null) {
				children = below;
			}
		}
		// accessor methods
		/**
		* Returns the element stored at this position.
		*
		* @return the stored element
		* @throws IllegalStateException if position no longer valid
		*/
		@Override
		public E getElement() throws IllegalStateException {
			if(element == null) {
				throw new IllegalStateException("Position no longer valid");
			}
			return element;
		}
		
		public Node<E> getParent() {
			return parent;
		}
		
		public LinkedList<Node<E>> getChildren() {
			return children;
		}
		// update methods		
		public void setElement(E e) {
			element = e;
		}
		
		public void setParent(Node<E> p) {
			parent = p;
		}
		
		public void setChildren(LinkedList<Node<E>> below) {
			children = below;
		}
		
		public void setChild(Node<E> c) {
			children.add(c);
		}
	} //----------- end of nested Node class -----------
	
	/** Factory function to create a new node storing element e. */
	protected Node<E> createNode(E e, Node<E> parent, LinkedList<Node<E>> children) {
		return new Node<E>(e, parent, children);
	}
	
	/**
	 * Constructs empty tree
	 */
	public Tree() {
	}

	public Node<E> root() {
		return root;
	}

	public Node<E> parent(Node<E> p) throws IllegalArgumentException {
		return p.getParent();
	}

	public LinkedList<Node<E>> children(Node<E> p) throws IllegalArgumentException {
		return p.getChildren();
	}

	public int numChildren(Node<E> p) throws IllegalArgumentException {
		LinkedList<Node<E>> list = p.getChildren();
		return list.size();
	}
	
	public void addChild(Node<E> p, Node<E> c) {
		p.setChild(c);
		size++;
	}
	
	public Node<E> addRoot(Node<E> e) throws IllegalStateException {
		root = e;
		size = 1;
		return root;
	}

	public boolean isRoot(Node<E> p) throws IllegalArgumentException {
		if(isEmpty() || p == null) {
			return false;
		} else if(p.getElement() == root.getElement()) {
			return true;
		}
		return false;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Node<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}
}
