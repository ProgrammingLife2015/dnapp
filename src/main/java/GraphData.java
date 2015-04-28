import java.io.FileNotFoundException;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import org.graphstream.graph.BreadthFirstIterator;

import reader.Reader;


public class GraphData {

  public static void main(String[] args) throws FileNotFoundException {
    Graph g = Reader.read("src/main/resources/nodes.txt", "src/main/resources/edges.txt");
    Viewer viewer = g.display();
    viewer.enableAutoLayout();
    //viewer.disableAutoLayout();
    Node first = null;
    for(Node n: g.getNodeSet()){
      if(first==null){
        first = n;
      }
      else if((Integer) n.getAttribute("start")<(Integer) first.getAttribute("start")){
        first = n;
      }
    }
    Iterable<Edge> itedge = first.getEachLeavingEdge();
    Iterator<Edge> it = itedge.iterator();
    while(it.hasNext()) {
      Edge edg = it.next();
      Node out = edg.getNode1();
      Node in = edg.getNode0();
      int ndepth = (Integer) in.getAttribute("depth") + 1;
      if(ndepth > (Integer) out.getAttribute("depth")){
        out.setAttribute("depth", ndepth);
      }
      System.out.println(edg.getNode1() + ""  + out.getAttribute("depth"));
    }
    /*
    BreadthFirstIterator<Node> it = new BreadthFirstIterator(first);
    while(it.hasNext()){
      Node n = it.next();
      n.setAttribute("x",it.getDepthOf(n));
      System.out.println(n.getId() + " " + it.getDepthOf(n));
    }
    */
   
    /*
    for(Node n : g.getNodeSet()) {
      n.setAttribute("x", n.getAttribute("start"));
    }
    */
  }
}