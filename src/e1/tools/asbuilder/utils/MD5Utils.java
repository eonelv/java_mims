/*    */ package e1.tools.asbuilder.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.security.DigestInputStream;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ public class MD5Utils
/*    */ {
/*    */   public static String getFileMD5(File fileName)
/*    */   {
/* 15 */     int bufferSize = 262144;
/* 16 */     FileInputStream fInputStream = null;
/* 17 */     DigestInputStream digestInputStream = null;
/*    */     try
/*    */     {
/* 21 */       MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 22 */       fInputStream = new FileInputStream(fileName);
/* 23 */       digestInputStream = new DigestInputStream(fInputStream, messageDigest);
/* 24 */       byte[] buffers = new byte[bufferSize];
/* 25 */       while (digestInputStream.read(buffers) > 0);
/* 27 */       messageDigest = digestInputStream.getMessageDigest();
/*    */ 
/* 29 */       byte[] resultBytes = messageDigest.digest();
/* 30 */       String str = byteArrayToHex(resultBytes);
/*    */       return str;
/*    */     }
/*    */     catch (NoSuchAlgorithmException e)
/*    */     {
/* 36 */       e.printStackTrace();
/*    */     }
/*    */     catch (FileNotFoundException e)
/*    */     {
/* 41 */       e.printStackTrace();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 46 */       e.printStackTrace();
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 52 */         digestInputStream.close();
/* 53 */         fInputStream.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 57 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */ 
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */   private static String byteArrayToHex(byte[] bytes)
/*    */   {
/* 66 */     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/* 67 */     char[] resultChars = new char[bytes.length * 2];
/* 68 */     int index = 0;
/* 69 */     byte[] arrayOfByte = bytes; int j = bytes.length; for (int i = 0; i < j; i++) { byte b = arrayOfByte[i];
/*    */ 
/* 71 */       resultChars[(index++)] = hexDigits[(b >>> 4 & 0xF)];
/* 72 */       resultChars[(index++)] = hexDigits[(b & 0xF)];
/*    */     }
/* 74 */     return new String(resultChars);
/*    */   }
/*    */ }
