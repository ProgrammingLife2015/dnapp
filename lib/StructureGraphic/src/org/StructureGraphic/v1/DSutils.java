/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.image.BufferedImage;
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
/*    */ public class DSutils
/*    */ {
/*    */   public static BufferedImage fromDS(Object val, int rectWidth, int rectHeight)
/*    */   {
/* 35 */     int oldW = DSTreeCopy.DSVTreeNode.HEIGHT;
/* 36 */     int oldH = DSTreeCopy.DSVTreeNode.WIDTH;
/*    */     
/* 38 */     DSTreeCopy.DSVTreeNode.WIDTH = rectWidth;
/* 39 */     DSTreeCopy.DSVTreeNode.HEIGHT = rectHeight;
/*    */     
/* 41 */     DSPaintable paintMe = DSTreeCopy.createVertical(DSTreeParser.from(val));
/* 42 */     BufferedImage im = new BufferedImage(paintMe.DSgetWidth() + 1, paintMe.DSgetHeight() + 1, 2);
/*    */     
/* 44 */     Graphics2D g2 = (Graphics2D)im.getGraphics();
/* 45 */     g2.setColor(Color.black);
/* 46 */     g2.setFont(Font.getFont("Monospaced"));
/* 47 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 48 */     paintMe.DSpaint(g2);
/*    */     
/* 50 */     DSTreeCopy.DSVTreeNode.WIDTH = oldW;
/* 51 */     DSTreeCopy.DSVTreeNode.HEIGHT = oldH;
/*    */     
/* 53 */     return im;
/*    */   }
/*    */   
/*    */   public static void show(Object val, int rectWidth, int rectHeight) {
/* 57 */     new DSImageView(fromDS(val, rectWidth, rectHeight)).setVisible(true);
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSutils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */