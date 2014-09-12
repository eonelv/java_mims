/*    */ package e1.tools.asbuilder;
/*    */ 
/*    */ import e1.tools.asbuilder.utils.DB;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class QueryProject
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 16 */     Map projects = DB.queryProjects(args[0]);
/*    */ 
/* 18 */     Iterator it = projects.keySet().iterator();
/*    */ 
/* 21 */     while (it.hasNext())
/*    */     {
/* 23 */       Integer id = (Integer)it.next();
/* 24 */       String pname = (String)projects.get(id);
/* 25 */       System.out.println(id + "锛�" + pname);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\lv\Desktop\java\e1asbuilder_utils.jar
 * Qualified Name:     e1.tools.asbuilder.QueryProject
 * JD-Core Version:    0.6.0
 */