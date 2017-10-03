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
import java.util.ArrayList;

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
		/**
		if(!inputReader.ready()) {
			System.out.println("Enter an input filename (e.g. \"filename.txt\"): ");
			File inputFileName = new File(inputReader.readLine());
			System.out.println("Enter an output filename (e.g. \"filename.txt\"): ");
			File outputFileName = new File(inputReader.readLine());
			inputReader = new BufferedReader(new FileReader(inputFileName));
			outputWriter = new BufferedWriter(new FileWriter(outputFileName));
		}
		**/
		inputReader = new BufferedReader(new FileReader(new File(work)));
		
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
		size = 0;
		while(pretrav[size] != null) {
			System.out.print(pretrav[size] + ", ");
			size++;
		}
		size = 0;
		System.out.println();
		while(pretrav[size] != null) {
			System.out.print(posttrav[size] + ", ");
			size++;
		}
		inputReader.close();
		
		Node<Character> root = buildTree(size, 0, 0);
		
		outputWriter.close();
	}

	public Node<Character> buildTree(int size, int prestart, int poststart) {
		if(pretrav[prestart] == null) {
			return null;
		}
		if(size > 0 && myTree.isEmpty()) {
			myTree.addRoot(pretrav[prestart]);
			prestart++;
		} else if(size > 1) {
			if(posttrav[poststart + size - 1] == pretrav[prestart]) {
				// start of pretrav == end of posttrav, so we have a subtree here
				// create parent?
				Node<Character> parent = new Node<Character>(pretrav[prestart], null, null);
			} else {
				// else we need to look for next subtree
				int count = poststart;
				while(pretrav[prestart + 1] != posttrav[count]) {
					count++;
				}
				count++; // one more to include the parent
				// recursive call with new prestart/poststart?
			}
		} else if(size == 1) {
			// create child
			return new Node<Character>(pretrav[prestart], null, null);
		}
		return null;
	}
}