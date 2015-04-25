package reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import exceptions.InvalidFileFormatException;
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
          throw new InvalidFileFormatException("Missing some information to create this node");
        }
        String content = sc.nextLine();
        int id;
        int start;
        int end;
        try {
          id = Integer.parseInt(n[0]);
        }
        catch(Exception e) {
          throw new InvalidFileFormatException("The id should be an integer");
        }
        try {
          start = Integer.parseInt(n[2]);
          end = Integer.parseInt(n[3]);
        }
        catch (Exception e ) {
          throw new InvalidFileFormatException("The start and end reference should be integers");
        }
        if(end-start!=content.length()){
          throw new InvalidFileFormatException("Size of Node content doesn't match with its reference size");
        }
        ArrayList<String> sources = new ArrayList<String>(Arrays.asList(n[1].split(",")));
        Node node = new Node(id,sources,start,end,content);
        nodes.add(node);
      }
      else {
        throw new InvalidFileFormatException("Every new node line should start with >");
      }
    }
    return new Graph(nodes);
  }
}
