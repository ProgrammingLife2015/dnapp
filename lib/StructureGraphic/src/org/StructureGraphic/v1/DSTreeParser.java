/*     */ package org.StructureGraphic.v1;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Scanner;
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
/*     */ public class DSTreeParser
/*     */ {
/*     */   public static final class DSTreeStack
/*     */   {
/*     */     protected LinkedList<LinkedList<DSTreeParser.DSSimpleNode>> tree;
/*     */     
/*     */     public DSTreeStack()
/*     */     {
/*  41 */       this.tree = new LinkedList();
/*  42 */       startLevel();
/*     */     }
/*     */     
/*     */     public void startLevel() {
/*  46 */       this.tree.add(new LinkedList());
/*     */     }
/*     */     
/*     */     public void finishLevel() {
/*  50 */       LinkedList<DSTreeParser.DSSimpleNode> children = (LinkedList)this.tree.removeLast();
/*  51 */       DSTreeParser.DSSimpleNode parent = (DSTreeParser.DSSimpleNode)((LinkedList)this.tree.getLast()).getLast();
/*     */       
/*     */ 
/*  54 */       parent.children.addAll(children);
/*     */     }
/*     */     
/*     */     public void addNode(Object val) {
/*  58 */       ((LinkedList)this.tree.getLast()).add(new DSTreeParser.DSSimpleNode(val == null ? "null" : val.toString()));
/*     */     }
/*     */     
/*     */     public DSTreeNode getTree() {
/*  62 */       while (this.tree.size() > 1)
/*  63 */         finishLevel();
/*  64 */       if (((LinkedList)this.tree.getFirst()).size() > 1) {
/*  65 */         DSTreeParser.DSSimpleNode root = new DSTreeParser.DSSimpleNode("");
/*     */         
/*     */ 
/*  68 */         root.children.addAll((Collection)this.tree.getFirst());
/*  69 */         return root; }
/*  70 */       if (((LinkedList)this.tree.getFirst()).size() == 1) {
/*  71 */         return (DSTreeNode)((LinkedList)this.tree.getFirst()).getFirst();
/*     */       }
/*  73 */       return new DSTreeParser.DSSimpleNode("Empty...");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DSSimpleNode implements DSTreeNode
/*     */   {
/*     */     @DSValue
/*     */     public String value;
/*     */     @DSChildren(DSChildren.DSChildField.ITERABLE)
/*     */     public List<DSTreeNode> children;
/*     */     @DSColor
/*     */     public Color color;
/*     */     
/*     */     public DSSimpleNode(String val, Color c)
/*     */     {
/*  88 */       this.value = val;
/*  89 */       this.children = new LinkedList();
/*  90 */       this.color = c;
/*     */     }
/*     */     
/*     */     public DSSimpleNode(String val) {
/*  94 */       this(val, null);
/*     */     }
/*     */     
/*     */     public void add(DSTreeNode... children) {
/*  98 */       for (DSTreeNode child : children) {
/*  99 */         this.children.add(child);
/*     */       }
/*     */     }
/*     */     
/*     */     public DSTreeNode[] DSgetChildren() {
/* 104 */       DSTreeNode[] c = new DSTreeNode[this.children.size()];
/* 105 */       return (DSTreeNode[])this.children.toArray(c);
/*     */     }
/*     */     
/*     */     public Object DSgetValue() {
/* 109 */       return this.value;
/*     */     }
/*     */     
/*     */     public Color DSgetColor() {
/* 113 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static DSTreeNode from(Object o)
/*     */   {
/* 120 */     if ((o instanceof DSTreeNode)) {
/* 121 */       return (DSTreeNode)o;
/*     */     }
/*     */     
/* 124 */     StringBuffer val = new StringBuffer();
/* 125 */     Color color = null;
/* 126 */     LinkedList<DSTreeNode> children = new LinkedList();
/*     */     
/* 128 */     for (Field field : o.getClass().getFields()) {
/*     */       try {
/* 130 */         if (field.getAnnotation(DSValue.class) != null) {
/* 131 */           val.append(field.getName()).append(": ").append(field.get(o)).append("\n");
/* 132 */         } else if (field.getAnnotation(DSChildren.class) != null)
/*     */         {
/* 134 */           if (((DSChildren)field.getAnnotation(DSChildren.class)).value() == DSChildren.DSChildField.ARRAY) {
/* 135 */             for (Object child : (Object[])field.get(o)) {
/* 136 */               children.add(from(child));
/*     */             }
/* 138 */           } else if (((DSChildren)field.getAnnotation(DSChildren.class)).value() == DSChildren.DSChildField.ITERABLE) {
/* 139 */             for (Object child : (Iterable)field.get(o)) {
/* 140 */               children.add(from(child));
/*     */             }
/*     */           } else {
/* 143 */             children.add(from(field.get(o)));
/*     */           }
/* 145 */         } else if (field.getAnnotation(DSColor.class) != null) {
/* 146 */           color = (Color)field.get(o);
/*     */         }
/*     */       } catch (IllegalArgumentException ex) {
/* 149 */         System.err.println(ex.getMessage());
/*     */       } catch (IllegalAccessException ex) {
/* 151 */         System.err.println("Could not access " + field.getName() + "in " + field.getClass().getName());
/*     */       } catch (ClassCastException ex) {
/* 153 */         System.err.println("The annotation for " + field.getName() + "in " + field.getClass().getName() + " does not match it's type!");
/*     */       }
/*     */     }
/*     */     
/* 157 */     DSSimpleNode s = new DSSimpleNode(val.toString());
/* 158 */     s.children.addAll(children);
/* 159 */     s.color = color;
/* 160 */     return s;
/*     */   }
/*     */   
/*     */   public static DSTreeNode parseSimpleMultiline(String text)
/*     */   {
/* 165 */     return parseSimpleMultiline(new Scanner(text));
/*     */   }
/*     */   
/*     */   public static DSTreeNode parseSimpleMultiline(File file) throws FileNotFoundException {
/* 169 */     return parseSimpleMultiline(new Scanner(file));
/*     */   }
/*     */   
/*     */   public static DSTreeNode parseSimpleMultiline(Scanner sc)
/*     */   {
/* 174 */     LinkedList<Integer> depths = new LinkedList();
/* 175 */     DSTreeStack st = new DSTreeStack();
/*     */     
/* 177 */     if (!sc.hasNextLine()) {
/* 178 */       return new DSSimpleNode("Empty");
/*     */     }
/* 180 */     while (sc.hasNextLine()) {
/* 181 */       String temp = sc.nextLine();String line = temp.trim();
/* 182 */       int spaceCount = temp.length() - line.length();
/* 183 */       if (depths.isEmpty())
/* 184 */         depths.addLast(Integer.valueOf(spaceCount)); else
/* 185 */         while (((Integer)depths.getLast()).intValue() > spaceCount) {
/* 186 */           st.finishLevel();
/* 187 */           depths.removeLast();
/*     */         }
/* 189 */       if (((Integer)depths.getLast()).intValue() < spaceCount) {
/* 190 */         st.startLevel();
/* 191 */         depths.addLast(Integer.valueOf(spaceCount));
/*     */       }
/* 193 */       st.addNode(line);
/*     */     }
/*     */     
/* 196 */     return st.getTree();
/*     */   }
/*     */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSTreeParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */