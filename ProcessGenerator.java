import java.util.*;
import java.io.*;

public class ProcessGenerator {
  //private int max = 50;
  
  public static void main(String[] args){
    new ProcessGenerator(Integer.parseInt(args[0]),Integer.parseInt(args[1]),args[2]);
  }
  
  public ProcessGenerator() {
    System.out.println("You forgot your arguments!! arg0 = number of processes, arg1 = max value, arg2 = output filename");
  }
  
  public ProcessGenerator(int count, int max, String filename){
    try {
    PrintWriter writer = new PrintWriter(filename, "UTF-8");
    
    int pid = 100;
    Random rand = new Random();
    
    for(int i=0;i<count;i++){
      int thisPid = pid+i;
      int arrival_time = rand.nextInt(max);
      int priority = rand.nextInt(max);
      int burst_time = rand.nextInt(max);
      writer.println("" + thisPid + ", " + burst_time + ", " + arrival_time + ", " + priority);
    }
    
    writer.close();
    } catch (Exception e){
      
    }
  }
}