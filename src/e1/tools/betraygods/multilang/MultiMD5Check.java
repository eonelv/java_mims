package e1.tools.betraygods.multilang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import e1.tools.suite.IProgress;
import e1.tools.utils.FileCopyer;
import e1.tools.utils.FileUtils;
import e1.tools.utils.MD5Utils;

public class MultiMD5Check 
{
	private static Logger logger = Logger.getLogger(MultiMD5Check.class);
	
	private String srcBasePath = null;
	private String destBasePath = null;
	
	private IProgress progress;
	private int currentProgressNum = 0;
	
	private String strFormatCalc = "计算文件总数";
	private String strFormatCalcMD5 = "计算MD5 %1$d / %2$d";
	private String strTotal = "总进度%1$d / %2$d";
	public static void main(String[] args) 
	{
		MultiMD5Check md5Check = new MultiMD5Check(null);
		md5Check.process(args[0], args[1], args[2]);
	}
	
	public MultiMD5Check(IProgress progress)
	{
		this.progress = progress;
	}
	
	public void process(String src, String dest, String copyTo)
	{
		srcBasePath = src;
		destBasePath = dest;
		Map<String, String> md5Src = new HashMap<String, String>();
		Map<String, String> md5Dest = new HashMap<String, String>();
		
		if (progress != null)
		{
			progress.notifyProgressTotal(0, 3, String.format(strTotal, 0, 3));
		}
		File filetemp = new File(src);
		progress.notifyProgress(0, 0, strFormatCalc);
		int count = FileUtils.calcFileCount(filetemp, 0);
		currentProgressNum = 0;
		calcMD5(srcBasePath, filetemp, md5Src, count);
		if (progress != null)
		{
			progress.notifyProgressTotal(1, 3, String.format(strTotal, 1, 3));
		}
		filetemp = new File(dest);
		progress.notifyProgress(0, 0, strFormatCalc);
		count = FileUtils.calcFileCount(filetemp, 0);
		currentProgressNum = 0;
		calcMD5(destBasePath, filetemp, md5Dest, count);
		if (progress != null)
		{
			progress.notifyProgressTotal(2, 3, String.format(strTotal, 2, 3));
		}
		
		Iterator<String> itDest = md5Dest.keySet().iterator();
		Iterator<String> itSrc = md5Src.keySet().iterator();
		String key = null;
		String strMd5Src = null;
		String strMd5Dest = null;
		List<String> diffFiles = new ArrayList<String>();
		while (itSrc.hasNext())
		{
			key = itSrc.next();
			strMd5Src = md5Src.get(key);
			strMd5Dest = md5Dest.get(key);
			
			if (strMd5Dest == null)
			{
				diffFiles.add(key);
				try 
				{
					File fileCopyTo = new File(copyTo+key);
					if (!fileCopyTo.getParentFile().exists())
					{
						fileCopyTo.getParentFile().mkdirs();
					}
					FileCopyer.copyFile(new File(srcBasePath + key), fileCopyTo);
				} 
				catch (IOException e) 
				{
					logger.error(e.getMessage(), e);
				}
				System.out.println(key);
			} 
			else if (!strMd5Src.equals(strMd5Dest))
			{
				diffFiles.add(key);
				try 
				{
					File fileCopyTo = new File(copyTo+key);
					if (!fileCopyTo.getParentFile().exists())
					{
						fileCopyTo.getParentFile().mkdirs();
					}
					FileCopyer.copyFile(new File(srcBasePath + key), fileCopyTo);
				} 
				catch (IOException e) 
				{
					logger.error(e.getMessage(), e);
				}
				System.out.println(key);
			}
		}
		if (progress != null)
		{
			progress.notifyProgressTotal(3, 3, String.format(strTotal, 3, 3));
		}
	}
	
	private void calcMD5(String base, File folder, Map<String, String> md5Result, int count)
	{
		if (folder.isDirectory())
		{
			File[] files = folder.listFiles();
			for (int i=0; i < files.length; i++)
			{
				calcMD5(base, files[i], md5Result, count);
			}
		}
		else
		{
			String MD5 = MD5Utils.getFileMD5(folder);
			md5Result.put(folder.getAbsolutePath().substring(base.length()), MD5);
			
			if (progress != null)
			{
				currentProgressNum++;
				progress.notifyProgress(currentProgressNum, count, String.format(strFormatCalcMD5, currentProgressNum, count));
			}
		}
	}

}
