/*     */ package e1.tools.asbuilder.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class DB
/*     */ {
/*     */   public static void main(String[] args)
/*     */   {
/*  18 */     System.out.println(getVersion("E:/build/sanguo07"));
/*     */   }
/*     */ 
/*     */   public static Map queryProjects(String path)
/*     */   {
/*  23 */     Map result = new HashMap();
/*  24 */     Connection con = getConn(path + "/eoneasbuilder.db");
/*  25 */     Statement sm = null;
/*  26 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  29 */       sm = con.createStatement();
/*  30 */       rs = sm.executeQuery("select * from eone_vb_game order by id;");
/*  31 */       while (rs.next())
/*     */       {
/*  33 */         result.put(Integer.valueOf(rs.getInt("id")), rs.getString("gname") + "    " + rs.getString("gpname"));
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  38 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  42 */       clear(con, sm, null);
/*     */     }
/*  44 */     return result;
/*     */   }
/*     */ 
/*     */   public static void updateBuilding(String path, String pid, boolean isBuilding)
/*     */   {
/*  49 */     Connection con = getConn(path + "/version.db");
/*  50 */     Statement sm = null;
/*     */     try
/*     */     {
/*  53 */       sm = con.createStatement();
/*  54 */       sm.execute("update t_vb_project set isBuilding = " + isBuilding + " where id = " + pid + ";");
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  58 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  62 */       clear(con, sm, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Map queryProject(String path, String pid, boolean isUpdate)
/*     */   {
/*  68 */     Map result = new HashMap();
/*  69 */     Connection con = getConn(path + "/version.db");
/*  70 */     Statement sm = null;
/*  71 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  74 */       sm = con.createStatement();
/*  75 */       if (isUpdate)
/*     */       {
/*  77 */         sm.execute("update t_vb_project set pnumber=pnumber+1 where id=" + pid + ";");
/*     */       }
/*  79 */       rs = sm.executeQuery("select * from t_vb_project where id = " + pid + ";");
/*  80 */       if (rs.next())
/*     */       {
/*  82 */         result.put("pname", rs.getString("pname"));
/*  83 */         result.put("pnameen", rs.getString("pname_en"));
/*  84 */         result.put("pvname", rs.getString("pvname"));
/*  85 */         result.put("pvnameen", rs.getString("pvname_en"));
/*  86 */         result.put("psharebase", rs.getString("psharebase"));
/*  87 */         result.put("plogname", rs.getString("plogname"));
/*  88 */         result.put("pmailto", rs.getString("pmailto"));
/*  89 */         result.put("pnumber", rs.getString("pnumber"));
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  98 */       clear(con, sm, rs);
/*     */     }
/* 100 */     return result;
/*     */   }
/*     */ 
/*     */   public static Connection getConn(String url)
/*     */   {
/* 105 */     Connection conn = null;
/*     */     try
/*     */     {
/* 108 */       Class.forName("org.sqlite.JDBC");
/* 109 */       conn = DriverManager.getConnection("jdbc:sqlite:" + url);
/*     */     }
/*     */     catch (ClassNotFoundException e)
/*     */     {
/* 113 */       e.printStackTrace();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 118 */       e.printStackTrace();
/*     */     }
/* 120 */     return conn;
/*     */   }
/*     */ 
/*     */   public static boolean createDB(String path) throws IOException
/*     */   {
/* 125 */     File file = new File(path + "/version.db");
/* 126 */     if (!file.exists())
/*     */     {
/* 128 */       file.createNewFile();
/*     */     }
/* 130 */     Connection con = getConn(file.getAbsolutePath());
/* 131 */     Statement sm = null;
/* 132 */     StringBuffer sqlCreateTable = new StringBuffer();
/*     */     try
/*     */     {
/* 135 */       sm = con.createStatement();
/* 136 */       sqlCreateTable.append("create table t_vb_project(id integer primary key, antfile varchar(100), ");
/* 137 */       sqlCreateTable.append("basepath varchar(100),");
/* 138 */       sqlCreateTable.append("svnlib varchar(200),");
/* 139 */       sqlCreateTable.append("svnmain varchar(200),");
/* 140 */       sqlCreateTable.append("pname varchar(100),");
/* 141 */       sqlCreateTable.append("pvname varchar(100),");
/* 142 */       sqlCreateTable.append("pbasepath varchar(100),");
/* 143 */       sqlCreateTable.append("psharebase varchar(200),");
/* 144 */       sqlCreateTable.append("plogname varchar(100),");
/* 145 */       sqlCreateTable.append("pmailto varchar(200)");
/* 146 */       sqlCreateTable.append(");");
/* 147 */       sm.execute(sqlCreateTable.toString());
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 151 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 155 */       clear(con, sm, null);
/*     */     }
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */   public static int getVersion(String url)
/*     */   {
/* 162 */     int result = -1;
/* 163 */     Connection con = getConn(url + "/.svn/wc.db");
/* 164 */     Statement sm = null;
/* 165 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 168 */       sm = con.createStatement();
/* 169 */       rs = sm.executeQuery("select revision from NODES;");
/* 170 */       if (rs.next())
/*     */       {
/* 172 */         result = rs.getInt("revision");
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 177 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 181 */       clear(con, sm, rs);
/*     */     }
/* 183 */     return result;
/*     */   }
/*     */ 
/*     */   public static void clear(Connection con, Statement sm, ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/* 190 */       if (rs != null)
/*     */       {
/* 192 */         rs.close();
/*     */       }
/* 194 */       if (sm != null)
/*     */       {
/* 196 */         sm.close();
/*     */       }
/* 198 */       if (con != null)
/*     */       {
/* 200 */         con.close();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 205 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\lv\Desktop\java\e1asbuilder_utils.jar
 * Qualified Name:     e1.tools.asbuilder.utils.DB
 * JD-Core Version:    0.6.0
 */