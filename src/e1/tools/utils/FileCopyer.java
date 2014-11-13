package e1.tools.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileCopyer 
{
	public static void copyFile(File srcFile, File destFile) throws IOException 
	{
		copyFile(srcFile, destFile.getParent(), "", "", false);
	}
	
	public static void copyFile(File srcFile, String destParent, String postFix, String ext, boolean isSplitting) throws IOException 
	{
		if (srcFile == null) 
		{
			return;
		}
		
		int byteread = 0;
		int byteReadTotal = 0;
	
		byte[] buffer = new byte[1024];
		
		InputStream inStream = new FileInputStream(srcFile); // 读入原文件
		if (!isSplitting)
		{
			FileOutputStream fs = new FileOutputStream(new File(destParent + "/" + srcFile.getName()));
			
			while ((byteread = inStream.read(buffer)) != -1) 
			{
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
		}
		else
		{
			int partIndex = 0;
			boolean isNeedNew = false;
			String name = srcFile.getName();
			name = name.substring(0, name.length() - ext.length());
			FileOutputStream fs = new FileOutputStream(new File(destParent + "/" + name + ".part" + partIndex + postFix + ext));
			while ((byteread = inStream.read(buffer)) != -1) 
			{
				if (isNeedNew)
				{
					fs = new FileOutputStream(new File(destParent + "/" + name + ".part" + partIndex + postFix + ext));
					isNeedNew = false;
				}
				fs.write(buffer, 0, byteread);
				byteReadTotal += 1;
				if (byteReadTotal == 900)
				{
					byteReadTotal = 0;
					fs.flush();
					fs.close();
					partIndex++;
					isNeedNew = true;
				}
			}
			fs.flush();
			fs.close();
			inStream.close();
		}
	}
	
	public static void writeFile(File file, byte[] datas) throws IOException
	{
		 DataOutputStream dataOutStream = new DataOutputStream(new FileOutputStream(file));
		 dataOutStream.write(datas);
		 dataOutStream.flush();
		 dataOutStream.close();
	}
}
