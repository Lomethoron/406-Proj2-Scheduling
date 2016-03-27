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
			System.out.println("Sorting method not specified, please properly format your arguement. " + e);
			return;
		}
		try{
			inputFileName = args[1];
		}
		catch(Exception e){
			System.out.println("File name not specified, please properly format your arguement. " + e);
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
		
		//takes each line and breaks it at ,[whitespace] combinations
		while(in.hasNextLine()){
			String[] brokenLine = in.nextLine().split(",\\s");
			//testing to make sure it breaks in the right place
			/*for(String bleh : brokenLine){
				System.out.println("|"+bleh+"|");
			}*/
			
			//converting all of these strings to the right things
			String pid = brokenLine[0];
			
			int burst = -1;
			try{
				burst = Integer.parseInt(brokenLine[1]);
			}
			catch(Exception e){
				System.out.println("Burst time either not given or not an integer: "+e);
				return;
			}
			int arrival = -1;
			try{
				arrival = Integer.parseInt(brokenLine[2]);
			}
			catch(Exception e){
				System.out.println("Arrival time either not given or not an integer: "+e);
				return;
			}
			int priority = -1;
			try{
				priority = Integer.parseInt(brokenLine[3]);
			}
			catch(Exception e){
				System.out.println("Priority either not given or not an integer: "+e);
				return;
			}
			
			//adding it to the queue structure
			tempQueue.add(new Process(pid, burst, arrival, priority));
		}
		
		in.close();
		
		//switch to proper scheduling method
		
	}
}