/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
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
/*    */ public class SimpleNode
/*    */ {
/*    */   @DSValue
/*    */   public String value;
/*    */   @DSChildren(DSChildren.DSChildField.ITERABLE)
/*    */   public List<SimpleNode> children;
/*    */   @DSColor
/*    */   public Color color;
/*    */   
/*    */   public SimpleNode(String val, Color c)
/*    */   {
/* 37 */     this.value = val;
/* 38 */     this.children = new LinkedList();
/* 39 */     this.color = c;
/*    */   }
/*    */   
/*    */   public SimpleNode(String val) {
/* 43 */     this(val, null);
/*    */   }
/*    */   
/*    */   public void add(SimpleNode... children) {
/* 47 */     for (SimpleNode child : children) {
/* 48 */       this.children.add(child);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\SimpleNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */