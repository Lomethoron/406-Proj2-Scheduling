public class Process{
	
	private int burst, arrival, priority;
	private String pid;
	
	public Process(){
		pid = "";
		arrival = -1;
		burst = -1;
		priority = -1;
	}
	
	public Process(String pid, int burst, int arrival, int priority){
		this.pid = pid;		
		this.burst = burst;
		this.arrival = arrival;
		this.priority = priority;
	}
	
	public String getpid() {
		return pid;
	}
	
	public int getBurst() {
		return burst;
	}
	
	public int getArrival(){
		return arrival;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setpid(String pid) {
		this.pid = pid;
	}
	
	public void setBurst(int burst) {
		this.burst = burst;
	}
	
	public void setArrival(int arrival){
		this.arrival = arrival;
	}
	
	public void getPriority(int priority){
		this.priority = priority;
	}
	
}