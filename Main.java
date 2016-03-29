import java.util.*;
import java.io.*;

public class Main{
	
	public static void main (String[] args) {
		Main main = new Main();
		main.doStuff(args);
	}

	public void doStuff(String[] args) {
		
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

		switch(sortType) {
			case "fcfs":
			//run sim
			fcfs(tempQueue);
			//print analysis
			break;

			case "sjf":
			sjf(tempQueue);
			break;

			case "srtf":
			srtf(tempQueue);
			break;

			case "nonpreprior":
			nonpreprior(tempQueue);
			break;

			case "preprior":
			preprior(tempQueue);
			break;

			case "rr":
			rr(tempQueue);
			break;

			case "lolbutts":
			break;

			default:
			throw new RuntimeException("Specified Scheduling method not recognized");
		}
		
	}

	public void fcfs(LinkedList<Process> inQueue){
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new CompareFCFS());
		//get all the processes into the queue
		for(Process entry: inQueue){
			sched.add(entry);
		}
		//simulate
		int time = 0;
		while(!sched.isEmpty()) {
			//pop
			Process currentProcess = sched.poll();
			//print
			System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
			//increment all other processes waiting times
			for(Process entry: sched){
				entry.incrementWaiting();
			}

			if(currentProcess.getResponse() == -1){
				currentProcess.setResponse(time - currentProcess.getArrival());
			}
			//decrement remaining time and put back in queue so long as the process is not yet done running
			if(currentProcess.getRemaining()>1){
				currentProcess.setRemaining(currentProcess.getRemaining()-1);
				sched.add(currentProcess);
			}
			time++;
		}
		//analyze
		double awt = 0;
		double wawt= 0;
		double art = 0;
		double wart = 0;
		int sumPri = 0;
		int numProc = inQueue.size();
		for(Process entry: inQueue){
			//average waiting time
			awt += entry.getWaiting();
			System.out.println(entry.getWaiting());
			//weighted averae waiting time
			wawt += entry.getPriority()*entry.getWaiting();

			//average response time
			art += entry.getResponse();

			//weighted average response time
			wart += entry.getPriority()*entry.getResponse();

			//sum of priorities
			sumPri += entry.getPriority();
		}
		System.out.println("Average waiting time is: "+ awt/numProc);
		System.out.println("Average weighted waiting time is: "+wawt/sumPri);
		System.out.println("Average response time is: "+art/numProc);
		System.out.println("Average weighted response time is: "+wart/sumPri);


	}

	public void sjf(LinkedList<Process> inQueue){
		
	}

	public void srtf(LinkedList<Process> inQueue){
		
	}

	public void nonpreprior(LinkedList<Process> inQueue){
		
	}

	public void preprior(LinkedList<Process> inQueue){
		
	}

	public void rr(LinkedList<Process> inQueue){
		
	}
}