import java.util.*;

class CompareRR implements Comparator<Process>{
  public CompareRR(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p1.getPriority() - p2.getPriority();
  }
}