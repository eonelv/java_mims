/*    */ package e1.tools.asbuilder;
/*    */ 
/*    */ import e1.tools.asbuilder.utils.DB;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class CreateDB
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 17 */       DB.createDB(args[0]);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 21 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\lv\Desktop\java\e1asbuilder_utils.jar
 * Qualified Name:     e1.tools.asbuilder.CreateDB
 * JD-Core Version:    0.6.0
 */