import java.util.*;
import java.io.*;

public class Main{
	
	public static void main (String[] args) {
		
		String sortType, inputFileName;
		// get stuff from arguements
		try{
			sortType = args[0];
		}
		catch(Exception e){
			System.out.println("Sorting method not specified, please properly format your arguement.");
			return;
		}
		try{
			inputFileName = args[1];
		}
		catch(Exception e){
			System.out.println("File name not specified, please properly format your arguement.");
			return;
		}
		
		//read from file
		LinkedList<Process> tempQueue = new LinkedList<Process>();
		
		Scanner in;
		try{
			in = new Scanner(new FileReader(inputFileName));
		}
		catch(Exception e){
			System.out.println("File not readable: "+ e );
			return;
		}
		
		in.close();
		
		//switch to proper scheduling method
		
	}
}