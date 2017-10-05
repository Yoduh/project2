package proj2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import proj2.Queue.EmptyQueueException;
import proj2.Queue.FullQueueException;
import proj2.Tree.Node;

public class Project2 {
	//home file
	String home = "C:\\Users\\Yoduh\\Desktop\\CSC\\316\\Assignments\\input.txt";
	//work file
	String work = "C:\\Users\\ahandlovits\\Desktop\\CSC\\CSC 316\\Assignments\\input.txt";
	
	public Character[] pretrav = new Character[256];
	public Character[] posttrav = new Character[256];
	Tree<Character> myTree = new Tree<Character>();

	public Project2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Project2 obj = new Project2();
		obj.initialize();
		
	}
	
	public void initialize() throws FileNotFoundException, IOException {
		//prepare input and output streams
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		
		if(!inputReader.ready()) {
			System.out.println("Enter an input filename (e.g. \"filename.txt\"): ");
			File inputFileName = new File(inputReader.readLine());
			System.out.println("file=" + inputFileName.getAbsolutePath());
			System.out.println("Enter an output filename (e.g. \"filename.txt\"): ");
			File outputFileName = new File(inputReader.readLine());
			inputReader = new BufferedReader(new FileReader(inputFileName));
			outputWriter = new BufferedWriter(new FileWriter(outputFileName));
		}
		
		//inputReader = new BufferedReader(new FileReader(new File(work)));
		
		// populate pretrav array
		String preString = inputReader.readLine();
		int size = 0;
		for(int i = 2; i < preString.length() - 1; i += 3) {
			pretrav[size] = preString.charAt(i);
			size++;
		}
		
		// populate posttrav array
		String postString = inputReader.readLine();
		size = 0;
		for(int i = 2; i < postString.length() - 1; i += 3) {
			posttrav[size] = postString.charAt(i);
			size++;
		}
		
		//debug
		/*size = 0;
		while(pretrav[size] != null) {
			System.out.print(pretrav[size] + ", ");
			size++;
		}
		size = 0;
		System.out.println();
		while(pretrav[size] != null) {
			System.out.print(posttrav[size] + ", ");
			size++;
		}*/
		
		
		myTree.addRoot(buildTree(size, 0, 0));
		
		while(inputReader.ready()) {
			String relation = inputReader.readLine();
			if(!relation.isEmpty() && relation.charAt(0) == '?') {
				outputWriter.write(getRelation(relation.charAt(2), relation.charAt(5)) + "\n");
				//System.out.println(getRelation(relation.charAt(2), relation.charAt(5)));
			}
		}
		
		/*System.out.println(getRelation('H', 'X'));
		System.out.println(getRelation('X', 'H'));
		System.out.println(getRelation('B', 'F'));
		System.out.println(getRelation('C', 'B'));
		System.out.println(getRelation('B', 'R'));
		System.out.println(getRelation('P', 'Q'));
		System.out.println(getRelation('H', 'N'));
		System.out.println(getRelation('B', 'N'));*/
		try {
			String tree = printLevelOrderTree();
			if(tree != null) {
				outputWriter.write(tree + "\n");
			}
		} catch (FullQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		inputReader.close();
		outputWriter.close();
	}

	public Node<Character> buildTree(int size, int prestart, int poststart) {
		Node<Character> root = new Node<Character>(pretrav[prestart], null, null);
		
		if(pretrav[prestart] == null) {
			return null;
		}
		if(size > 1) {
			if(pretrav[prestart] == posttrav[poststart + size - 1] && pretrav[prestart + 1] == posttrav[poststart]) {
				// start of pretrav == end of posttrav, and pretrav[1] == posttrav[0], so we have a final subtree here
				// create children and add to current subtree root node
				for(int i = 0; i < size - 1; i++) {
					Node<Character> c = buildTree(1, prestart + i + 1, poststart + i);
					myTree.addChild(root, c);
					c.setParent(root);
				}
				return root;
				
			} else {
				while(poststart < size - 1) {
					// else there is a deeper subtree to find
					int count = poststart;
					
					while(pretrav[prestart + 1] != posttrav[count]) {
						count++;
					}
					count++; // one more to include the parent
					count = count - poststart; // count = number of nodes in subtree
					// recursive call with new prestart/poststart?
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
	
	public void clearAllMarks(Node<Character> p) {
		p.clearMark();
		while(!myTree.isRoot(p)) {
			p = p.getParent();
			p.clearMark();
		}
	}
	
	public Node<Character> getNode(Character c, Node<Character> p) {
		for(Node<Character> child : p.getChildren()) {
			Node<Character> n = getNode(c, child);
			if(n.getElement().equals(c)) {
				return n;
			}
		}
		return p;
	}
	
	public void markAncestors(Node<Character> a) {
		// mark a and all ancestors of a
		int i = 1;
		a.setMark(1);
		while(!myTree.isRoot(a)) {
			a = a.getParent();
			a.setMark(++i);
		}
	}
	
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
	
	public String getRelation(Character a, Character b) {
		Node<Character> nodeA = getNode(a, myTree.root());
		Node<Character> nodeB = getNode(b, myTree.root());
		markAncestors(nodeA);
		int[] paths = findCommonAncestor(nodeB);
		clearAllMarks(nodeA);
		return buildRelationString(paths, a, b);
	}
	
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