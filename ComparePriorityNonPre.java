import java.util.*;

class ComparePriorityNonPre implements Comparator<Process>{
  public ComparePriorityNonPre(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p1.getPriority() - p2.getPriority();
  }
}