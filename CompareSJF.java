import java.util.*;

class CompareSJF implements Comparator<Process>{
  public CompareSJF(){
    
  }
  
  public int compare (Process p1, Process p2){
    return p1.getBurst() - p2.getBurst();
  }
}