import java.util.*;

class CompareSJF implements Comparator<Process>{
  public CompareSJF(){
    
  }
  
  public int compare (Process p1, Process p2){
	 if(p1.getIsCurrentProcess()){
		return -1;
	}
	else if(p2.getIsCurrentProcess()) {
		return 1;
	}
    return p1.getBurst() - p2.getBurst();
  }
}