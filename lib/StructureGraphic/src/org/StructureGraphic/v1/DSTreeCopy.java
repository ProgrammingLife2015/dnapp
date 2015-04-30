/*     */ package org.StructureGraphic.v1;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSTreeCopy
/*     */ {
/*  31 */   public static final Color DEFAULT_COLOR = Color.BLACK;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class DSSimpleGraphicalNode
/*     */     implements DSGraphicalTreeNode
/*     */   {
/*     */     public String value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Color color;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public DSTreeCopy.DSVTreeNode[] children;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int width;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int height;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public DSSimpleGraphicalNode(DSTreeNode n, int width, int height)
/*     */     {
/*  88 */       this.value = (n.DSgetValue() == null ? "null" : n.DSgetValue().toString());
/*  89 */       this.color = (n.DSgetColor() == null ? DSTreeCopy.DEFAULT_COLOR : n.DSgetColor());
/*     */       
/*  91 */       this.width = width;
/*  92 */       this.height = height;
/*     */     }
/*     */     
/*     */     public DSTreeNode[] DSgetChildren()
/*     */     {
/*  97 */       return this.children;
/*     */     }
/*     */     
/*     */     public Color DSgetColor()
/*     */     {
/* 102 */       return this.color;
/*     */     }
/*     */     
/*     */     public String DSgetValue()
/*     */     {
/* 107 */       return this.value;
/*     */     }
/*     */     
/*     */     public int DSgetWidth() {
/* 111 */       return this.width;
/*     */     }
/*     */     
/*     */     public int DSgetHeight() {
/* 115 */       return this.height;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DSVTreeNode extends DSTreeCopy.DSSimpleGraphicalNode
/*     */   {
/* 121 */     public static int HSPACE = 4; public static int VSPACE = 15;
/* 122 */     public static int WIDTH = 60; public static int HEIGHT = 25;
/*     */     public int[] centerXOffsets;
/*     */     
/*     */     public DSVTreeNode(DSTreeNode n, int width, int height)
/*     */     {
/* 127 */       super(width, height);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void DSpaint(Graphics2D g)
/*     */     {
/* 141 */       int xOff = (this.width - WIDTH) / 2;
/* 142 */       Color originalColor = g.getColor();
/* 143 */       g.setColor(this.color != null ? this.color : Color.BLACK);
/*     */       
/* 145 */       g.translate(xOff, 0);
/*     */       
/* 147 */       g.drawRect(0, 0, WIDTH, HEIGHT);
/* 148 */       if (this.value.length() > 0)
/*     */       {
/* 150 */         TextUtils.drawCenteredText(g, this.value, WIDTH / 2, HEIGHT / 2);
/*     */       }
/*     */       
/* 153 */       g.translate(-xOff, 0);
/*     */       
/* 155 */       g.setColor(originalColor);
/*     */       
/* 157 */       if (this.children.length != 0) {
/* 158 */         g.translate(0, HEIGHT + VSPACE);
/*     */         
/* 160 */         xOff = 0;
/*     */         
/* 162 */         for (DSVTreeNode child : this.children) {
/* 163 */           child.DSpaint(g);
/* 164 */           int temp; g.translate(temp = child.width + HSPACE, 0);
/* 165 */           xOff += temp;
/*     */         }
/*     */         
/* 168 */         g.translate(-xOff, 0);
/*     */         
/* 170 */         for (int i = 0; i < this.children.length; i++) {
/* 171 */           g.drawLine(this.width / 2, -VSPACE, this.centerXOffsets[i], 0);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 176 */         g.translate(0, -(HEIGHT + VSPACE));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static DSVTreeNode createVertical(DSTreeNode r) {
/* 182 */     if (r == null) { r = new DSTreeParser.DSSimpleNode("null");
/*     */     }
/* 184 */     int width = -DSVTreeNode.HSPACE;int height = 0;
/* 185 */     DSTreeNode[] children = r.DSgetChildren();
/* 186 */     DSVTreeNode[] rootChildren = new DSVTreeNode[children.length];
/* 187 */     int[] centerXOffsets = new int[children.length];
/* 188 */     for (int i = 0; i < rootChildren.length; i++) {
/* 189 */       rootChildren[i] = createVertical(children[i]);
/* 190 */       width += rootChildren[i].DSgetWidth() + DSVTreeNode.HSPACE;
/* 191 */       centerXOffsets[i] = (width - rootChildren[i].DSgetWidth() / 2);
/* 192 */       height = Math.max(height, rootChildren[i].DSgetHeight());
/*     */     }
/* 194 */     height += DSVTreeNode.HEIGHT;
/* 195 */     if (children.length != 0) {
/* 196 */       height += DSVTreeNode.VSPACE;
/*     */     }
/* 198 */     width = Math.max(width, DSVTreeNode.WIDTH);
/*     */     
/* 200 */     DSVTreeNode root = new DSVTreeNode(r, width, height);
/* 201 */     root.children = rootChildren;
/* 202 */     root.centerXOffsets = centerXOffsets;
/* 203 */     return root;
/*     */   }
/*     */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSTreeCopy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */