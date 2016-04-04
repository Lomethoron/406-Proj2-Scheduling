import java.util.*;

class CompareWSRTF implements Comparator<Process>{
  public CompareWSRTF(){
    
  }
  
  public int compare (Process p1, Process p2){
    //int remDiff = p1.getRemaining() - p2.getRemaining();
    int priorDiff = p2.getPriority() - p1.getPriority();
    return p1.getRemaining() - (p2.getRemaining()+priorDiff);
  }
}