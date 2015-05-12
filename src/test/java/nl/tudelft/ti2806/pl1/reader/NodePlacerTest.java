package nl.tudelft.ti2806.pl1.reader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodePlacerTest {

  private ArrayList<Integer> hdiff;
  private ArrayList<Integer> nodesatdepth;
  private int height = 60;
  
  @Before
  public void startUp() {
    nodesatdepth = new ArrayList<Integer>();
    nodesatdepth.add(2);
    nodesatdepth.add(1);
    hdiff = NodePlacer.heightDiff(nodesatdepth, height);
  }
  
  @After
  public void tearDown() {
    hdiff = null;
  }
  
  @Test
  public void heightDiffTest() {
    assertEquals(NodePlacer.getHeight(0, hdiff, nodesatdepth, height),-10);
    assertEquals(NodePlacer.getHeight(0, hdiff, nodesatdepth, height),10);
  }
  
  @Test
  public void getWidthTest() {
    assertTrue(NodePlacer.getWidth(50, 0, nodesatdepth.size())==0);
    assertTrue(NodePlacer.getWidth(50, 1, nodesatdepth.size())==25);
  }

}
