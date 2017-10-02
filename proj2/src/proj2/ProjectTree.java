package proj2;

import java.util.Iterator;
import java.util.LinkedList;

public class ProjectTree implements Tree<Character> {

	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private LinkedList<E> children;
		
		public Node(E e, Node<E> above, LinkedList<E> below) {
			element = e;
			parent = above;
			children = below;
		}
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
		
		public LinkedList<E> getChildren() {
			return children;
		}
		
		public void setElement(E e) {
			element = e;
		}
		
		public void setParent(Node<E> p) {
			parent = p;
		}
		
		public void setChildren(LinkedList<E> below) {
			children = below;
		}
	}
	
	public ProjectTree() {
		
	}

	@Override
	public Position<Character> root() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position parent(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable children(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numChildren(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInternal(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExternal(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoot(Position p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable positions() {
		// TODO Auto-generated method stub
		return null;
	}
}
