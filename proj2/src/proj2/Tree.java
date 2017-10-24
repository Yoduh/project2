package proj2;

/**
 * Custom implementation of a node-based general tree able to be constructed when 
 * only the postorder and preorder traversals of the tree are known.
 * @author aehandlo
 *
 * @param <E> Generic type
 */
public class Tree<E> {
	
	/** root node of tree */
	private Node<E> root = null;
	/** size of tree. always equals zero when first constructed */
	private int size = 0;

	/**
	 * Nested Node class
	 * @author aehandlo
	 *
	 * @param <E> Generic type
	 */
	protected static class Node<E> {
		/** generic value held by the node */
		private E element;
		/** link to parent node */
		private Node<E> parent;
		/** linked list of children nodes */
		private LinkedList<Node<E>> children;
		/** ancestor mark for determining relation queries */
		private int mark;
		
		/**
		 * Constructor
		 * @param e Node value
		 * @param above Parent node
		 * @param below Children nodes
		 */
		public Node(E e, Node<E> above, LinkedList<Node<E>> below) {
			element = e;
			parent = above;
			children = new LinkedList<Node<E>>();
			if(below != null) {
				children = below;
			}
			mark = 0;
		}
		
		/**
		* Returns the element stored at this position.
		* @return the stored element
		* @throws IllegalStateException if position no longer valid
		*/
		public E getElement() throws IllegalStateException {
			if(element == null) {
				throw new IllegalStateException("Position no longer valid");
			}
			return element;
		}
		/**
		 * Get parent node
		 * @return parent
		 */
		public Node<E> getParent() {
			if(parent != null) {
				return parent;
			}
			return null;
		}
		/**
		 * Get children nodes
		 * @return children
		 */
		public LinkedList<Node<E>> getChildren() {
			if(children != null) {
				return children;
			}
			return null;
		}
		/**
		 * Set value of node
		 * @param e Value
		 */
		public void setElement(E e) {
			element = e;
		}
		/**
		 * Set parent node
		 * @param p Parent
		 */
		public void setParent(Node<E> p) {
			parent = p;
		}
		/**
		 * Set children nodes
		 * @param below Children
		 */
		public void setChildren(LinkedList<Node<E>> below) {
			children = below;
		}
		/**
		 * Set single child node
		 * @param c Single child
		 */
		public void setChild(Node<E> c) {
			children.add(c);
		}
		/**
		 * Set mark value
		 * @param m Mark value
		 */
		public void setMark(int m) {
			mark = m;
		}
		/**
		 * Set mark back to default (zero)
		 */
		public void clearMark() {
			mark = 0;
		}
		/**
		 * Get mark value
		 * @return Mark value
		 */
		public int getMark() {
			return mark;
		}
		/**
		 * Find if node is marked or not
		 * @return True if node is marked, false if not
		 */
		public boolean isMarked() {
			if(mark > 0) {
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Factory function to create a new node storing element e.
	 * @param e Value of node
	 * @param parent Parent of node
	 * @param children Children of node
	 * @return Newly created node
	 */
	protected Node<E> createNode(E e, Node<E> parent, LinkedList<Node<E>> children) {
		return new Node<E>(e, parent, children);
	}
	
	/**
	 * Constructs empty tree
	 */
	public Tree() {
	}
	/**
	 * Get root node
	 * @return root
	 */
	public Node<E> root() {
		return root;
	}
	/**
	 * Get parent node of given node
	 * @param p Node needing parent returned
	 * @return Parent of node
	 * @throws IllegalArgumentException if node has no parent
	 */
	public Node<E> parent(Node<E> p) {
		if(p.getParent() == null) {
			throw new IllegalArgumentException("Node has no parent");
		}
		return p.getParent();
	}
	/**
	 * Get children of given node
	 * @param p Node needing children returned
	 * @return Children of node
	 * @throws IllegalArgumentException if node has no children
	 */
	public LinkedList<Node<E>> children(Node<E> p) {
		if(p.getChildren() == null) {
			throw new IllegalArgumentException("Node has no children");
		}
		return p.getChildren();
	}
	/**
	 * Get number of children of given node
	 * @param p Node needing number of children returned
	 * @return Number of children
	 */
	public int numChildren(Node<E> p) {
		LinkedList<Node<E>> list = p.getChildren();
		return list.size();
	}
	/**
	 * Add child to given node
	 * @param p Node to add child to
	 * @param c Child being added
	 */
	public void addChild(Node<E> p, Node<E> c) {
		p.setChild(c);
		size++;
	}
	/**
	 * Add root to tree
	 * @param e Root node
	 * @return Root node
	 */
	public Node<E> addRoot(Node<E> e) {
		root = e;
		size = 1;
		return root;
	}
	/**
	 * Determine if node is root
	 * @param p Node to find out if root or not
	 * @return True if node is root, false if not
	 */
	public boolean isRoot(Node<E> p) {
		if(isEmpty() || p == null) {
			return false;
		} else if(p.getElement() == root.getElement()) {
			return true;
		}
		return false;
	}
	/**
	 * Get number of nodes in the tree
	 * @return Number of tree nodes
	 */
	public int size() {
		return size;
	}
	/**
	 * Determine if tree is empty
	 * @return True if tree is empty, false if not
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}
}
