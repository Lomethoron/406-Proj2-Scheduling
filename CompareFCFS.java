import java.util.*;

class CompareFCFS implements Comparator<Process>{
  public CompareFCFS(){
    
  }
  
  public int compare (Process p1, Process p2){
	if(p1.getIsCurrentProcess()){
		return -1;
	}
	else if(p2.getIsCurrentProcess()) {
		return 1;
	}
    return p1.getArrival() - p2.getArrival();
  }
}