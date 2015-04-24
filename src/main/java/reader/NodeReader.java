package reader;
import java.util.ArrayList;
import java.util.Scanner;

import graph.*;
public class NodeReader {
  
  public Graph ReadNodes(Scanner sc) {
    ArrayList<Node> nodes = new ArrayList<Node>();
    while(sc.hasNext()){
      String nextnode = sc.nextLine();
      if(nextnode.charAt(0)=='>'){
        nextnode = nextnode.substring(1);
        String[] n = nextnode.replace("\\s", "").split("\\|");
      }
      else {
        //TODO some error or message?
      }
    }
    return new Graph(nodes);
  }
}
