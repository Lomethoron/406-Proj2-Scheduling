import java.util.*;

class ComparePriorityNonPre implements Comparator<Process>{
  public ComparePriorityNonPre(){
    
  }
  
  public int compare (Process p1, Process p2){
	if(p1.getIsCurrentProcess()){
		return -1;
	}
	else if(p2.getIsCurrentProcess()) {
		return 1;
	}
    return p2.getPriority() - p1.getPriority();
  }
}