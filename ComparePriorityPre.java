import java.util.*;

class ComparePriorityPre implements Comparator<Process>{
  public ComparePriorityPre(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p1.getPriority() - p2.getPriority();
  }
}