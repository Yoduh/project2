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

public class Project2 {
	
	public Character[] pretrav = new Character[256];
	public Character[] posttrav = new Character[256];

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
		inputReader = new BufferedReader(new FileReader(new File("C:\\Users\\ahandlovits\\Desktop\\CSC\\CSC 316\\Assignments\\small-input.txt")));
		while(inputReader.ready()) {
			System.out.println(inputReader.readLine());
		}
		inputReader.close();
		outputWriter.close();
	}
}