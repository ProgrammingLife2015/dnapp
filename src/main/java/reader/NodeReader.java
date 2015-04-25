package reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import graph.*;
/**
 * 
 * @author Marissa, Mark
 * @date 25-04-2015
 * Lets you read the Nodes from the provided data.
 */
public class NodeReader {
  
  /**
   * 
   * @param sc Scanner from which contains the Node information.
   * @return Returns a Graph containing all the Nodes, but no Edges.
   */
  public static Graph ReadNodes(Scanner sc) {
    ArrayList<Node> nodes = new ArrayList<Node>();
    while(sc.hasNextLine()){
      String nextnode = sc.nextLine();
      if(nextnode.charAt(0)=='>'){
        nextnode = nextnode.substring(1);
        String[] n = nextnode.split("\\s\\|\\s");
        if(n.length!=4) {
          //TODO some error
        }
        String content = sc.nextLine();
        //TODO some error if not int
        int id = Integer.parseInt(n[0]);
        int start = Integer.parseInt(n[2]);
        int end = Integer.parseInt(n[3]);
        ArrayList<String> sources = new ArrayList(Arrays.asList(n[1].split(",")));
        Node node = new Node(id,sources,start,end,content);
        nodes.add(node);
      }
      else {
        //TODO some error or message?
      }
    }
    return new Graph(nodes);
  }
}
