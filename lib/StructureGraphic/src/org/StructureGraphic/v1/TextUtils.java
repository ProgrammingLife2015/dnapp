/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.font.FontRenderContext;
/*    */ import java.awt.font.TextLayout;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.Rectangle2D.Double;
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
/*    */ public class TextUtils
/*    */ {
/*    */   public static final int fontSize = 12;
/*    */   public static final String fontName = "SansSerif";
/*    */   public static final int fontStyle = 0;
/*    */   public static final boolean antialias = true;
/*    */   public static final boolean fractionalMetrics = false;
/* 40 */   public static final Font font = new Font("SansSerif", 0, 12);
/* 41 */   public static final FontRenderContext fc = new FontRenderContext(null, true, false);
/*    */   
/*    */   public static Rectangle2D measureText(String text)
/*    */   {
/* 45 */     double width = 0.0D;double height = 0.0D;
/* 46 */     double minX = Double.MAX_VALUE;double minY = Double.MAX_VALUE;
/* 47 */     for (String line : text.split("\n")) {
/* 48 */       TextLayout layout = new TextLayout(line, font, fc);
/* 49 */       Rectangle2D rect = layout.getBounds();
/* 50 */       width = Math.max(width, rect.getWidth());
/* 51 */       height += rect.getHeight();
/* 52 */       minX = Math.min(minX, rect.getX());
/* 53 */       minY = Math.min(minX, rect.getY());
/*    */     }
/* 55 */     return new Rectangle2D.Double(minX, minY, width, height);
/*    */   }
/*    */   
/*    */   public static void drawCenteredText(Graphics2D g2, String text, float cx, float cy)
/*    */   {
/* 60 */     Rectangle2D r = measureText(text);
/* 61 */     float xOff = (float)(cx - r.getWidth() / 2.0D - r.getX());
/* 62 */     float YOff = (float)(cy - r.getHeight() / 2.0D - r.getY());
/* 63 */     for (String line : text.split("\n")) {
/* 64 */       TextLayout layout = new TextLayout(line, font, fc);
/* 65 */       layout.draw(g2, xOff + 0.0F, YOff);
/* 66 */       YOff = (float)(YOff + layout.getBounds().getHeight());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\TextUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */