/** Programming project 2
	* @author Bret Black, Will Dixon **/

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

	/** FIRST COME FIRST SERVE */
	// TODO: Handle case where a process is completed but no others have arrived yet
	// FIXED 4/2/16 dixonw
	// TODO: Ensure that a process, once being executed, is not interupted
	// FIXED 4/2/16 dixonw
	public void fcfs(LinkedList<Process> inQueue){
		System.out.println("RUNNING FCFS");
		
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
			currentProcess.setIsCurrentProcess(true);
			boolean processHasArrived = currentProcess.getArrival() <= time;
			if(processHasArrived){
				System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
				
				//note when the process is first served to the CPU
				if(currentProcess.getResponse() == -1){
					currentProcess.setResponse(time - currentProcess.getArrival());
				}
			}
			
			else System.out.println("Time: "+time+ ", No process running");
			//increment all other processes waiting times
			for(Process entry: sched){
				//make sure a process only thinks that it is waiting if it has already arrived
				if(entry.getArrival()<=time){
					entry.incrementWaiting();
				}
			}

			
			//decrement remaining time and put back in queue so long as the process is not yet done running
			//puts a not arrived process back into the queue as well
			if(currentProcess.getRemaining()>1 || !processHasArrived){
				currentProcess.setRemaining(currentProcess.getRemaining()-1);
				sched.add(currentProcess);
			}
			time++;
		}
		//analyze
		analyze(inQueue);
	}

	/** SHORTEST JOB FIRST */
	public void sjf(LinkedList<Process> inQueue){
		System.out.println("RUNNING SJF");
		
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new CompareSJF());
		
		//get all the initial processes into the queue
		for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
		}
		
		// handle case where no processes are added
		if(sched.isEmpty()) return;
		
		//simulate
		int time = 0;
		int numProcess = inQueue.size()-1;
		int completeProcess = 0;
		
		while(numProcess>=completeProcess) {
			//no process ready
			if(sched.isEmpty()){
				System.out.println("Time: "+time+ ", No process running");
			}
			//processes ready
			else{
				Process currentProcess = sched.poll();
				currentProcess.setIsCurrentProcess(true);
				//print
				System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
				//increment all other processes waiting times
				for(Process entry: sched){
					entry.incrementWaiting();
				}
				currentProcess.incrementWaiting();

				if(currentProcess.getResponse() == -1){
					currentProcess.setResponse(time - currentProcess.getArrival());
				}
				
				//decrement remaining time and put back in queue so long as the process is not yet done running
				currentProcess.setRemaining(currentProcess.getRemaining()-1);

				// handle process completion
				if(currentProcess.getRemaining() <= 0){
					completeProcess++;
				}
				else sched.add(currentProcess);
			}
			time++;
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);
	}

	/** SHORTEST REMAINING TIME FIRST */
	public void srtf(LinkedList<Process> inQueue){
		System.out.println("RUNNING SRTF");
		
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new CompareSRTF());
		
		//get all the processes into the queue
		for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
		}
		
		//simulate
		int time = 0;
		int numProcess = inQueue.size()-1;
		int completeProcess = 0;
		
		while(numProcess>=completeProcess) {
			//no process ready
			if(sched.isEmpty()){
				System.out.println("Time: "+time+ ", No process running");
			}
			//processes ready
			else{
				Process currentProcess = sched.poll();
				currentProcess.setIsCurrentProcess(true);
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
				currentProcess.setRemaining(currentProcess.getRemaining()-1);

				// handle process completion
				if(currentProcess.getRemaining() <= 0){
					completeProcess++;
				}
				else sched.add(currentProcess);
			}
			time++;
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);
		//get all the processes into the queue
		/*for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
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
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);*/
	}

	/** Priority, non-preemptive */
	// TODO: Add aging
	public void nonpreprior(LinkedList<Process> inQueue){
		System.out.println("RUNNING PRIORITY NON-PREEMPTIVE");
		
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new ComparePriorityNonPre());
		
		//get all the initial processes into the queue
		for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
		}
		
		//simulate
		int time = 0;
		int numProcess = inQueue.size()-1;
		int completeProcess = 0;
		
		while(numProcess>=completeProcess) {
			//no process ready
			if(sched.isEmpty()){
				System.out.println("Time: "+time+ ", No process running");
			}
			//processes ready
			else{
				Process currentProcess = sched.poll();
				currentProcess.setIsCurrentProcess(true);
				//print
				System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
				//increment all other processes waiting times
				for(Process entry: sched){
					entry.incrementWaiting();
					entry.setPriority(entry.getPriority()+1);
				}

				if(currentProcess.getResponse() == -1){
					currentProcess.setResponse(time - currentProcess.getArrival());
				}
				
				//decrement remaining time and put back in queue so long as the process is not yet done running
				currentProcess.setRemaining(currentProcess.getRemaining()-1);

				// handle process completion
				if(currentProcess.getRemaining() <= 0){
					completeProcess++;
				}
				else sched.add(currentProcess);
			}
			time++;
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);
	}

	/** Priority,preemptive */
	// TODO: Add aging
	public void preprior(LinkedList<Process> inQueue){
		System.out.println("RUNNING PRIORITY PREEMPTIVE");
		
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new ComparePriority());
		
		//get all the initial processes into the queue
		for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
		}
		
		//simulate
		int time = 0;
		int numProcess = inQueue.size()-1;
		int completeProcess = 0;
		
		while(numProcess>=completeProcess) {
			//no process ready
			if(sched.isEmpty()){
				System.out.println("Time: "+time+ ", No process running");
			}
			//processes ready
			else{
				Process currentProcess = sched.poll();
				//print
				System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
				//increment all other processes waiting times
				for(Process entry: sched){
					entry.incrementWaiting();
					entry.setPriority(entry.getPriority()+1);
				}

				if(currentProcess.getResponse() == -1){
					currentProcess.setResponse(time - currentProcess.getArrival());
				}
				
				//decrement remaining time and put back in queue so long as the process is not yet done running
				currentProcess.setRemaining(currentProcess.getRemaining()-1);

				// handle process completion
				if(currentProcess.getRemaining() <= 0){
					completeProcess++;
				}
				else sched.add(currentProcess);
			}
			time++;
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);
		
	}

	/** Round Robin */
	public void rr(LinkedList<Process> inQueue){
		System.out.println("RUNNING ROUND ROBIN");
		int quantum = 3; // how long before switching?
		
		LinkedList<Process> sched = new LinkedList<Process>();
		
		//get all the initial processes into the queue
		for(Process entry: inQueue){
			if(entry.getArrival() == 0)	sched.add(entry);
		}
		
		// handle case where no processes are added
		if(sched.isEmpty()) return;
		
		//simulate
		int time = 0;
		int numProcess = inQueue.size()-1;
		int completeProcess = 0;
		int q = 0;
		
		while(numProcess>=completeProcess) {
			//no process ready
			if(sched.isEmpty()){
				System.out.println("Time: "+time+ ", No process running");
			}
			//processes ready
			else{
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
				currentProcess.setRemaining(currentProcess.getRemaining()-1);

				q++;
				// handle process completion
				//process is done
				if(currentProcess.getRemaining() <= 0){
					completeProcess++;
					q = 0;
				}
				//process is out of time
				else if(q == quantum){
					q = 0;
					sched.add(currentProcess);
				}
				//process should run again
				else sched.push(currentProcess);
			}
			time++;
			
			// consider adding a new process
			for(Process entry: inQueue){
				if(entry.getArrival() == time){
					sched.add(entry);
				}
			}
		}
		//analyze
		analyze(inQueue);
		
	}
	
	/** analysis function called at the end */
	public void analyze(LinkedList<Process> inQueue){
		double awt = 0;
		double wawt= 0;
		double art = 0;
		double wart = 0;
		int sumPri = 0;
		int numProc = inQueue.size();
		for(Process entry: inQueue){
			//average waiting time
			awt += entry.getWaiting();
			System.out.println("Waiting: "+entry.getWaiting());
			System.out.println("Response: "+entry.getResponse());
			//weighted averae waiting time
			wawt += entry.getPriority()*entry.getWaiting();

			//average response time
			art += entry.getResponse();

			//weighted average response time
			wart += entry.getPriority()*entry.getResponse();

			//sum of priorities
			sumPri += entry.getPriority();
		}
		System.out.println("Average waiting time is: "+ awt/numProc+" cycles.");
		System.out.println("Average weighted waiting time is: "+wawt/sumPri + " cycles.");
		System.out.println("Average response time is: "+art/numProc + " cycles.");
		System.out.println("Average weighted response time is: "+wart/sumPri + " cycles.");
	}
}