import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import reader.Reader;


public class GraphData {
  
  static Queue<Node> que;

  public static void main(String[] args) throws FileNotFoundException {
    Graph g = Reader.read("src/main/resources/nodes.txt", "src/main/resources/edges.txt");
    Viewer viewer = g.display();
    //viewer.enableAutoLayout();
    viewer.disableAutoLayout();
    Node first = null;
    for(Node n: g.getNodeSet()){
      if(first==null){
        first = n;
      }
      else if((Integer) n.getAttribute("start")<(Integer) first.getAttribute("start")){
        first = n;
      }
    }
    que = new LinkedList<Node>();
    que.add(first);
    while(!que.isEmpty()){
      depthLevel();
    }

    BreadthFirstIterator<Node> it = new BreadthFirstIterator(first);
    while(it.hasNext()){
      Node n = it.next();
      n.setAttribute("x",n.getAttribute("depth"));
    }
    
   
    /*
    for(Node n : g.getNodeSet()) {
      n.setAttribute("x", n.getAttribute("start"));
    }
    */
  }
  
  public static void depthLevel() {
    Node src = que.remove();
    Iterable<Edge> itedge = src.getEachLeavingEdge();
    Iterator<Edge> it = itedge.iterator();
    while(it.hasNext()) {
      Edge edg = it.next();
      if(edg.getNode0().getId()!=src.getId()){
        continue;
      }
      Node out = edg.getNode1();
      int ndepth = (Integer) src.getAttribute("depth") + 1;
      if(ndepth > (Integer) out.getAttribute("depth")){
        out.setAttribute("depth", ndepth);
        que.add(out);
        System.out.println("From " + edg.getNode0() + " To " + edg.getNode1() + " source " + src.getId());
        System.out.println(out.getAttribute("depth"));
      }
    }
  }
}