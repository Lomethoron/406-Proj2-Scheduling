import java.util.*;

class CompareFCFS implements Comparator<Process>{
  public CompareFCFS(){
    
  }
  
  public CompareTo (Process p1, Process p2){
    return p1.getArrival() - p2.getArrival();
  }
}