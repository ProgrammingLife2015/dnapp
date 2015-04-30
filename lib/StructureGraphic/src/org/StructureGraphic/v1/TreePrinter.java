/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreePrinter
/*    */ {
/*    */   private static void print(DSTreeNode n, String indent)
/*    */   {
/* 32 */     System.out.println(indent + "val: " + n.DSgetValue());
/* 33 */     if ((n instanceof DSTreeCopy.DSSimpleGraphicalNode))
/*    */     {
/* 35 */       DSTreeCopy.DSSimpleGraphicalNode n2 = (DSTreeCopy.DSSimpleGraphicalNode)n;
/* 36 */       System.out.println(indent + "width: " + n2.DSgetWidth());
/* 37 */       System.out.println(indent + "height: " + n2.DSgetHeight());
/*    */     }
/*    */     
/* 40 */     indent = indent + "-";
/* 41 */     for (DSTreeNode child : n.DSgetChildren()) {
/* 42 */       print(child, indent);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void print(DSTreeNode n)
/*    */   {
/* 48 */     print(n, "");
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\TreePrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */