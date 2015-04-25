package reader;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import exceptions.InvalidFileFormatException;

@RunWith(Parameterized.class)
public class EdgeReaderBadWeatherTest {

  private static String edge1 = "a 5";
  private static String edge2 = "5 a";
  private static String edge3 = "1";
  private static String edge4 = "1 4 5";
  
  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {edge1},
        {edge2},
        {edge3},
        {edge4}
    });
  }
  
  private String edge;
  
  public EdgeReaderBadWeatherTest(String edge) {
    this.edge = edge;
  }
  
  @Test(expected=InvalidFileFormatException.class)
  public void invalidStringsTest() {
    Scanner sc = new Scanner(edge);
    EdgeReader.readEdges(sc);
  }
}
