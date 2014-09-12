package e1.tools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileCopyer 
{
	public static void copyFile(File srcFile, File destFile) 
	{
		if (srcFile == null) 
		{
			return;
		}
		try 
		{
			int byteread = 0;
			
			InputStream inStream = new FileInputStream(srcFile); // 读入原文件
			FileOutputStream fs = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) 
			{
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}
}
