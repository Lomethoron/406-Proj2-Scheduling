public class Process{
	
	private int burst, arrival, priority, remaining, waiting, response;
	private String pid;
	
	public Process(){
		pid = "";
		arrival = -1;
		burst = -1;
		priority = -1;
		remaining = -1;
		waiting = -1;
	}
	
	public Process(String pid, int burst, int arrival, int priority){
		this.pid = pid;
		this.burst = burst;
		this.arrival = arrival;
		this.priority = priority;
		this.remaining = burst;
		waiting = 0;
		response = -1;
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
	
	public int getRemaining(){
		return remaining;
	}

	public int getWaiting() {
		return waiting;
	}

	public int getResponse(){
		return response;
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
	
	public void setPriority(int priority){
		this.priority = priority;
	}

	public void setRemaining(int remaining){
		this.remaining = remaining;
	}

	public void incrementWaiting(){
		waiting++;
	}

	public void setResponse(int response){
		this.response = response;
	}
}