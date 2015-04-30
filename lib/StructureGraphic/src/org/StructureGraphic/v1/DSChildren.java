/*    */ package org.StructureGraphic.v1;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
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
/*    */ @Retention(RetentionPolicy.RUNTIME)
/*    */ public @interface DSChildren
/*    */ {
/*    */   DSChildField value();
/*    */   
/*    */   public static enum DSChildField
/*    */   {
/* 30 */     SINGLE,  ARRAY,  ITERABLE;
/*    */     
/*    */     private DSChildField() {}
/*    */   }
/*    */ }


/* Location:              D:\TU Delft Local\ContextWorkspace\ContextPL1\lib\StructureGraphic.jar!\org\StructureGraphic\v1\DSChildren.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */