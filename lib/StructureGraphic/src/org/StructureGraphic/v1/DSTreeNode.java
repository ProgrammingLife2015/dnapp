package org.StructureGraphic.v1;

import java.awt.Color;

public abstract interface DSTreeNode
{
  public abstract DSTreeNode[] DSgetChildren();
  
  public abstract Object DSgetValue();
  
  public abstract Color DSgetColor();
}


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSTreeNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */