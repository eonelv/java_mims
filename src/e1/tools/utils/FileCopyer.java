package e1.tools.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileCopyer 
{
	public static int copyFile(File srcFile, File destFile) throws IOException 
	{
		return copyFile(srcFile, destFile, "", "", false);
	}
	
	public static int copyFile(File srcFile, File destFile, String postFix, String ext, boolean isSplitting) throws IOException 
	{
		if (srcFile == null) 
		{
			return 1;
		}
		
		String destParent = destFile.getParent();
		int byteread = 0;
		int byteReadTotal = 0;
		int partCount = 1;
	
		byte[] buffer = new byte[1024];
		
		InputStream inStream = new FileInputStream(srcFile); // 读入原文件
		if (!isSplitting)
		{
			String name = destFile.getName();
			name = name.substring(0, name.length() - ext.length());
			FileOutputStream fs = new FileOutputStream(new File(destParent + "/" + name + postFix + ext));
			
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
			String name = destFile.getName();
			name = name.substring(0, name.length() - ext.length());
			FileOutputStream fs = new FileOutputStream(new File(destParent + "/" + name + ".part" + partIndex + postFix + ext));
			while ((byteread = inStream.read(buffer)) != -1) 
			{
				if (isNeedNew)
				{
					fs = new FileOutputStream(new File(destParent + "/" + name + ".part" + partIndex + postFix + ext));
					partCount++;
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
		return partCount;
	}
	
	public static void writeFile(File file, byte[] datas) throws IOException
	{
		 DataOutputStream dataOutStream = new DataOutputStream(new FileOutputStream(file));
		 dataOutStream.write(datas);
		 dataOutStream.flush();
		 dataOutStream.close();
	}
}
