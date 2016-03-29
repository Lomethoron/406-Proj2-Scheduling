import java.util.*;

class ComparePriority implements Comparator<Process>{
  public ComparePriority(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p2.getPriority() - p1.getPriority();
  }
}