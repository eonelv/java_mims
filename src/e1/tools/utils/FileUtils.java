package e1.tools.utils;

import java.io.File;

public class FileUtils 
{
	static public void main(String[] args)
	{
		setFilesWriteable(new File("E:\\Projects\\bg_korea\\resources\\ui\\project"));
	}
	
	static public int calcFileCount(File file, int count)
	{
		if (file == null)
		{
			return 0;
		}
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			int length = files.length;
			for (int i = 0; i < length; i++)
			{
				count = calcFileCount(files[i], count);
			}
		}
		else
		{
			count += 1;
		}
		return count;
	}	
	
	static public void setFilesWriteable(File file)
	{
		if (file == null)
		{
			return;
		}
		String name = file.getName();
		
		boolean out = name.equalsIgnoreCase("item") || name.equalsIgnoreCase("monsterhead") || name.equalsIgnoreCase("skill") || name.equalsIgnoreCase("soul");
		
		if (out && file.isDirectory())
		{
			return;
		}
		
		file.setWritable(true);
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				setFilesWriteable(files[i]);
			}
		}
	}
	
	static public void clearFiles(File file)
	{
		if (file.isFile())
		{
			file.setWritable(true);
			file.delete();
			return;
		}
		File[] files = file.listFiles();
		int size = files.length;
		for (int i=0; i < size; i++)
		{
			clearFiles(files[i]);
		}
	}
}
