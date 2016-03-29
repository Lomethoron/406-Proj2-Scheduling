import java.util.*;

class CompareSRTF implements Comparator<Process>{
  public CompareSRTF(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p1.getRemaining() - p2.getRemaining();
  }
}