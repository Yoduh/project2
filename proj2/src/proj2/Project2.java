package proj2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import proj2.Queue.EmptyQueueException;
import proj2.Queue.FullQueueException;
import proj2.Tree.Node;

/**
 * Constructs a representation of a family tree from listings of its nodes in
 * preorder and postorder, then answer questions about relationships of pairs of individuals (nodes)
 * in the tree. Also prints the tree in level-order traversal.
 * @author aehandlo
 *
 */
public class Project2 {
	/** Holds pre-traversal array of tree nodes */
	public Character[] pretrav = new Character[256];
	/** Holds post-traversal array of tree nodes */
	public Character[] posttrav = new Character[256];
	/** Tree to be constructed from pretrav and posttrav */
	Tree<Character> myTree = new Tree<Character>();

	/**
	 * Main method launches initialization of program
	 * @param args Command line arguments (not used)
	 * @throws FileNotFoundException if file not found
	 * @throws IOException if probably with input/output
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Project2 obj = new Project2();
		obj.initialize();
	}
	
	/**
	 * Opens input and output reader and writer, populates node arrays,
	 * calls for tree to be built, answers relationship queires, 
	 * then prints level-order traversal
	 * @throws FileNotFoundException if files not found
	 * @throws IOException if problem with input/output
	 */
	public void initialize() throws FileNotFoundException, IOException {
		// prepare input and output streams
		// for redirection
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
		
		// for files
		if(!inputReader.ready()) {
			System.out.println("Enter an input filename (e.g. \"filename.txt\"): ");
			File inputFileName = new File(inputReader.readLine());
			System.out.println("Enter an output filename (e.g. \"filename.txt\"): ");
			File outputFileName = new File(inputReader.readLine());
			inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), "UTF-8"));
			outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF-8"));
		}
		
		// populate pretrav array
		String preString = inputReader.readLine();
		int size = 0;
		for(int i = 2; i < preString.length(); i += 3) {
			pretrav[size] = preString.charAt(i);
			size++;
		}
		
		// populate posttrav array
		String postString = inputReader.readLine();
		size = 0;
		for(int i = 2; i < postString.length(); i += 3) {
			posttrav[size] = postString.charAt(i);
			size++;
		}		
		
		myTree.addRoot(buildTree(size, 0, 0));
		
		// answer relationship queries
		while(inputReader.ready()) {
			String relation = inputReader.readLine();
			if(!relation.isEmpty() && relation.charAt(0) == '?') {
				outputWriter.write(getRelation(relation.charAt(2), relation.charAt(5)) + "\n");
			}
		}
		
		// write level-order traversal
		try {
			String tree = printLevelOrderTree();
			if(tree != null) {
				outputWriter.write(tree + "\n");
			}
		} catch (FullQueueException e) {
			e.printStackTrace();
		} catch (EmptyQueueException e) {
			e.printStackTrace();
		}
		
		// close streams
		inputReader.close();
		outputWriter.close();
	}

	/**
	 * Recursive function builds tree using preorder and postorder arrays
	 * @param size Size of tree or sub-tree
	 * @param prestart Location to read from pre-traversal array
	 * @param poststart Location to read from post-traversal array
	 * @return Root of tree
	 */
	public Node<Character> buildTree(int size, int prestart, int poststart) {
		Node<Character> root = new Node<Character>(pretrav[prestart], null, null);
		
		// base case
		if(size == 1) {
			return root;
		}
		else if(size > 1) {
			if(isFinalSubTree(prestart, poststart, size)) {
				for(int i = 0; i < size - 1; i++) {
					Node<Character> c = buildTree(1, prestart + i + 1, poststart + i);
					myTree.addChild(root, c);
					c.setParent(root);
				}
				return root;
				
			} else {
				int totalLoops = 0;
				while(totalLoops < size - 1) {
					// else there is a deeper subtree to find
					int count = poststart;
					while(!pretrav[prestart + 1].equals(posttrav[count])) {
						count++;
						totalLoops++;
					}
					count++; // one more to include the parent
					totalLoops++;
					count = count - poststart; // count = number of nodes in subtree
					prestart++;
					Node<Character> c = buildTree(count, prestart, poststart);
					myTree.addChild(root, c);
					c.setParent(root);
					
					prestart += count - 1;
					poststart += count;
				}
			}
		}
		return root;
	}
	
	/**
	 * Determines if a given subtree is a final subtree (i.e. it has only leaves)
	 * @param prestart index where currently in the preorder of the tree
	 * @param poststart index where currently in the postorder of the tree
	 * @param size size of the subtree
	 * @return true if this is a final subtree, false if not
	 */
	public boolean isFinalSubTree(int prestart, int poststart, int size) {
		if(pretrav[prestart] != posttrav[poststart + size - 1]) {
			return false;
		}
		for(int i = 0; i < size; i++) {
			if(pretrav[prestart + i] != posttrav[poststart + size - i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Constructs string of level-order traversal of tree
	 * @return String of level-order traversal
	 * @throws FullQueueException if Queue is full
	 * @throws EmptyQueueException if Queue is empty
	 */
	@SuppressWarnings("unchecked")
	public String printLevelOrderTree() throws FullQueueException, EmptyQueueException {
		String tree = "";
		if(myTree.root() == null) {
			return null;
		}
		Character[] inordertrav = new Character[256];
		int i = 0;
		Queue q = new Queue();
		Node<Character> p = myTree.root();
		q.enqueue(p);
		while(!q.isEmpty()) {
			p = (Node<Character>) q.dequeue();
			inordertrav[i] = p.getElement();
			i++;
			for(Node<Character> child : p.getChildren()) {
				q.enqueue(child);
			}
		}
		
		for(int j = 0; j < i - 1; j++) {
			tree += inordertrav[j] + ", ";
		}
		tree += inordertrav[i - 1] + ".";
		
		return tree;
	}
	
	/**
	 * Clear all the marks for a node and its sub-tree
	 * @param p Node to clear all marks from
	 */
	public void clearAllMarks(Node<Character> p) {
		p.clearMark();
		while(!myTree.isRoot(p)) {
			p = p.getParent();
			p.clearMark();
		}
	}
	
	/**
	 * Finds a node using postorder traversal of the tree
	 * @param c Value of node being searched for
	 * @param p Node of sub-tree being searched
	 * @return Node being searched for
	 */
	public Node<Character> getNode(Character c, Node<Character> p) {
		for(Node<Character> child : p.getChildren()) {
			Node<Character> n = getNode(c, child);
			if(n.getElement().equals(c)) {
				return n;
			}
		}
		return p;
	}
	
	/**
	 * Mark a given node and all its ancestors
	 * @param a The node to mark along with its ancestors
	 */
	public void markAncestors(Node<Character> a) {
		int i = 1;
		a.setMark(1);
		while(!myTree.isRoot(a)) {
			a = a.getParent();
			a.setMark(++i);
		}
	}
	
	/**
	 * Find common ancestor of given node
	 * @param b Node searching for an ancestor
	 * @return Array that contains two ints. 
	 * First int is the length of path from A (node that all marks are based on) to ancestor
	 * Second int is the length of path from B to the ancestor 
	 */
	public int[] findCommonAncestor(Node<Character> b) {
		// search B, B's parent, grandparent, until marked node is found
		int path = 0;
		while(!myTree.isRoot(b)) {
			if(b.isMarked()) {
				int[] vals = {b.getMark() - 1, path};
				return vals;
			}
			b = b.getParent();
			path++;
		}
		// check one last time now that b is root
		if(b.isMarked()) {
			int[] vals = {b.getMark() - 1, path};
			return vals;
		}
		// common ancestor not found
		return null;
	}
	
	/**
	 * Get relation between two nodes given their node values
	 * @param a Value of first node
	 * @param b Value of second node
	 * @return String that details the relationship between the two nodes
	 */
	public String getRelation(Character a, Character b) {
		Node<Character> nodeA = getNode(a, myTree.root());
		Node<Character> nodeB = getNode(b, myTree.root());
		markAncestors(nodeA);
		int[] paths = findCommonAncestor(nodeB);
		clearAllMarks(nodeA);
		return buildRelationString(paths, a, b);
	}
	
	/**
	 * Constructs String that details the relationship between two nodes
	 * @param paths The lengths of the nodes paths to a common ancestor
	 * @param charA Value of the first node
	 * @param charB Value of the second node
	 * @return String of the relation
	 */
	public String buildRelationString(int[] paths, Character charA, Character charB) {
		int a = paths[0];
		int b = paths[1];
		String AisB = charA + " is " + charB;
		
		//A is B
		if(a == 0 && b == 0) {
			return AisB + ".";
		} 
		//A is B's parent
		else if(a == 0 && b == 1) {
			return AisB + "'s parent.";
		} 
		//A is B's grandparent
		else if(a == 0 && b == 2) {
			return AisB + "'s grandparent.";
		} 
		//A is B's great-grandparent
		else if(a == 0 && b == 3) {
			return AisB + "'s great-grandparent.";
		} 
		//A is B's great^(b-2)-grandparent
		else if(a == 0 && b > 3) {
			String g = AisB + "'s great-";
			for(int i = 1; i < b - 2; i++) {
				g += "great-";
			}
			g += "grandparent.";
			return g;
		} 
		//A is B's child
		else if(a == 1 && b == 0) {
			return AisB + "'s child.";
		} 
		//A is B's grandchild
		else if(a == 2 && b == 0) {
			return AisB + "'s grandchild.";
		} 
		//A is B's great^(b-2)-grandchild
		else if(a >= 3 && b == 0) {
			String g = AisB + "'s great-";
			for(int i = 1; i < a - 2; i++) {
				g += "great-";
			}
			g += "grandchild.";
			return g;
		} 
		//A is B's sibling
		else if(a == 1 && b == 1) {
			return AisB + "'s sibling.";
		} 
		//A is B's aunt/uncle
		else if(a == 1 && b == 2) {
			return AisB + "'s aunt/uncle.";
		} 
		//A is B's great^(b-2)-aunt/uncle
		else if(a == 1 && b >= 2) {
			String g = AisB + "'s great-";
			for(int i = 1; i < b - 2; i++) {
				g += "great-";
			}
			g += "aunt/uncle.";
			return g;
		} 
		//A is B's niece/nephew
		else if(a == 2 && b == 1) {
			return AisB + "'s niece/nephew.";
		} 
		//A is B's great^(b-2)-niece/nephew
		else if(a >= 2 && b == 1) {
			String g = AisB + "'s great-";
			for(int i = 1; i < a - 2; i++) {
				g += "great-";
			}
			g += "niece/nephew.";
			return g;
		} 
		//A is B's (min(b,a) - 1)th cousin |a - b| times removed.
		else if(a >= 2 && b >= 2) {
			int cousin = Integer.min(b, a) - 1;
			int rem = Math.abs(a - b);
			return AisB + "'s " + cousin + "th cousin " + rem + " times removed.";
		}
		return charA + " is not related to " + charB;
	}
}