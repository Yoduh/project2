package proj2;

import java.util.Iterator;
import java.util.LinkedList;

/** Concrete implementation of a general tree using a node-based, linked structure. */
public class Tree<E> {
	
	private Node<E> root = null;
	private int size = 0;

	//---------------- nested Node class ----------------
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private LinkedList<Node<E>> children;
		private int mark;
		
		public Node(E e, Node<E> above, LinkedList<Node<E>> below) {
			element = e;
			parent = above;
			children = new LinkedList<Node<E>>();
			if(below != null) {
				children = below;
			}
			mark = 0;
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
		
		public void setMark(int m) {
			mark = m;
		}
		
		public void clearMark() {
			mark = 0;
		}
		
		public int getMark() {
			return mark;
		}
		
		public boolean isMarked() {
			if(mark > 0) {
				return true;
			}
			return false;
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
	/*
	public void clearAllMarks(Node<E> p) {
		p.clearMark();
		if(numChildren(p) > 0) {
			for(Node<E> child : p.getChildren()) {
				clearAllMarks(child);
			}
		}
	}
	
	public Node<E> getNode(E e, Node<E> p) {
		for(Node<E> child : p.getChildren()) {
			Node<E> c = getNode(e, child);
			if(c.getElement().equals(e)) {
				return c;
			}
		}
		return p;
	}
	
	public void markAncestors(Node<E> a) {
		// mark a and all ancestors of a
		int i = 0;
		a.setMark(0);
		while(!isRoot(a)) {
			a = a.getParent();
			a.setMark(++i);
		}
	}
	
	public int[] findCommonAncestor(Node<E> b) {
		// search B, B's parent, grandparent, until marked node is found
		int path = 0;
		while(!isRoot(b)) {
			if(b.isMarked()) {
				int[] vals = {b.getMark(), path};
				return vals;
			}
			b = b.getParent();
			path++;
		}
		// check one last time now that b is root
		if(b.isMarked()) {
			int[] vals = {b.getMark(), path};
			return vals;
		}
		// common ancestor not found
		return null;
	}
	*/
}
