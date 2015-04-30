/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.image.BufferedImage;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JScrollPane;
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
/*    */ public class DSImageView
/*    */   extends JFrame
/*    */ {
/*    */   public final BufferedImage im;
/*    */   private JLabel label;
/*    */   private JScrollPane scroll;
/*    */   
/*    */   public DSImageView(BufferedImage im)
/*    */   {
/* 30 */     this.im = im;
/* 31 */     initComponents();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void initComponents()
/*    */   {
/* 43 */     this.scroll = new JScrollPane();
/* 44 */     this.label = new JLabel();
/*    */     
/* 46 */     setDefaultCloseOperation(3);
/* 47 */     setTitle("Data Visualization");
/*    */     
/* 49 */     this.scroll.setBorder(null);
/*    */     
/* 51 */     this.label.setIcon(new ImageIcon(this.im));
/* 52 */     this.label.setHorizontalAlignment(0);
/* 53 */     this.label.setHorizontalTextPosition(0);
/* 54 */     this.scroll.setViewportView(this.label);
/*    */     
/* 56 */     getContentPane().add(this.scroll, "Center");
/*    */     
/* 58 */     pack();
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSImageView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */