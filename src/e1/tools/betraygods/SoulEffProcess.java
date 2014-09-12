package e1.tools.betraygods;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import e1.tools.utils.FileCopyer;

public class SoulEffProcess 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		if (args.length < 5)
		{
			System.out.println("参数不正确，请确定输出输入目录");
//			return;
		}
		SoulEffProcess processor = new SoulEffProcess();
		String srcPath = args[0];
		String destPath = args[1];
		processor.svnPath = args[2];
		processor.atfToolPath = args[3];
		processor.textureToolPath = args[4];
		
		
//		String srcPath = "E:/Projects/betraygods/resources/ui/export/effect2d/兽魂";
//		String destPath = "C:/Users/lv/Desktop/soul/test/";
//		processor.svnPath = "C:/Users/lv/Desktop/soul/svn1/";

		processor.process(srcPath, destPath);
	}
	
	public String svnPath;
	private String srcPath;
	private String destPath;
	private File _srcFile;
	private File _destFile;
	public String atfToolPath = "E:/Projects/betraygods/resources/ui/deploy/png2atf.exe";//atf工具位置exe
	public String textureToolPath = "F:/Flash/software/TexturePacker/bin/TexturePacker.exe";//atf工具位置exe
	
	public void process(String srcPath, String destPath)
	{
		processes = new ArrayList<Process>();
		this.srcPath = srcPath;
		this.destPath = destPath;
		File file = new File(srcPath);
		_srcFile = file;
		_destFile = new File(destPath);
		if (!file.exists())
		{
			System.out.println("source folder not exist");
			return;
		}
		innerProcessFiles(file);
		try 
		{
			processTexture();
			try
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			Iterator<Process> it = processes.iterator();
			while (it.hasNext())
			{
				it.next().destroy();
			}
			processes.clear();
			processAtf();
			try
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			it = processes.iterator();
			while (it.hasNext())
			{
				it.next().destroy();
			}
			processes.clear();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		copyFilesToSvn();
	}
	
	private void processAtf() throws IOException
	{
		File[] files = _destFile.listFiles();
		File folder;
		String cmd;
		String ext;
		for (int i=0; i < files.length; i++)
		{
			folder = files[i];
			if (folder.isDirectory())
			{
				continue;
			}
			ext = folder.getAbsolutePath().substring(folder.getAbsolutePath().lastIndexOf('.')+1);

			if (!ext.equalsIgnoreCase("png"))
			{
				continue;
			}
			cmd = atfToolPath + " -c d -r -n 0,0 -i " 
				+ folder.getParentFile().getAbsolutePath()
				+ "/"
				+folder.getName() 
				+ " -o "
				+ folder.getParentFile().getAbsolutePath()
				+ "/"
				+ folder.getName().substring(0, folder.getName().length() - 4)
				+ ".atf";
			Process process = Runtime.getRuntime().exec(cmd);
//			InputStream fis = process.getInputStream();
//
//		    InputStreamReader isr = new InputStreamReader(fis);

		    processes.add(process);
//		    BufferedReader br = new BufferedReader(isr);
//		    String line = null;
//		    while ((line = br.readLine()) != null)
//		    {
//		        System.out.println(line);
//		    }
		}
	}
	
	private void processTexture() throws IOException
	{
		File[] files = _destFile.listFiles();
		File folder;
		String cmd;
		for (int i=0; i < files.length; i++)
		{
			folder = files[i];
			if (!folder.isDirectory())
			{
				continue;
			}
			cmd = textureToolPath + " --width 256 --height 256 --data " 
					+ folder.getParentFile().getAbsolutePath() + "/soul-"
					+ folder.getName() 
					+ ".xml --format sparrow --sheet " 
					+ folder.getParentFile().getAbsolutePath() + "/soul-"
					+ folder.getName() 
					+ ".png --opt RGBA8888 "
					+ folder.getAbsolutePath();
			Process process = Runtime.getRuntime().exec(cmd);
			processes.add(process);
//			InputStream fis = process.getInputStream();
//
//		    InputStreamReader isr = new InputStreamReader(fis);
//
//		    BufferedReader br = new BufferedReader(isr);
//		    String line = null;
//
//		    while ((line = br.readLine()) != null)
//		    {
//		        System.out.println(line);
//		    }
		}
	}
	
	private void copyFilesToSvn()
	{
		File destFile = new File(destPath);
		File[] files = destFile.listFiles();

		File srcFile = null;

		for (int i=0; i < files.length; i++)
		{
			srcFile = files[i];
			if (srcFile.isDirectory())
			{
				continue;
			}
			if (srcFile.getName().endsWith(".png"))
			{
				continue;
			}
			destFile = new File(svnPath + "/" + srcFile.getName());
			if (!destFile.getParentFile().exists())
			{
				destFile.getParentFile().mkdirs();
			}
			FileCopyer.copyFile(files[i], destFile);
		}
	}
	
	private void innerProcessFiles(File file)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				innerProcessFiles(files[i]);
			}
		}
		else
		{
			File destFile;
			String relativePath;
			relativePath = file.getAbsolutePath();
			relativePath = relativePath.substring(srcPath.length());
			String destName = getDestName(file);
			if (destName == null)
			{
				return;
			}
			destFile = new File(destPath + getDestName(file));
			if (!destFile.getParentFile().exists())
			{
				destFile.getParentFile().mkdirs();
			}
			FileCopyer.copyFile(file, destFile);
		}
	}
	
	private String getDestName(File file)
	{
		String fileName = file.getName();
		int indexColor = -1;
		int index = -1;
		
		String pname = file.getParentFile().getName();
		String ppname = file.getParentFile().getParentFile().getName();
		String ext = ".png";
		
		for (int i=0; i < colors.length; i++)
		{
			if (colors[i].equalsIgnoreCase(pname))
			{
				indexColor = i;
				break;
			}
		}
		
		for (int i=0; i < names.length; i++)
		{
			if (names[i].equalsIgnoreCase(ppname))
			{
				index = i;
				break;
			}
		}
		fileName = fileName.substring(fileName.length() - 6, fileName.length() - 4);
		int fileIndex = new Integer(fileName);
		fileIndex += 1;
		if (fileIndex < 10)
		{
			fileName = "0" + fileIndex;
		}
		else
		{
			fileName = "" + fileIndex;
		}
		if (indexColor == -1 || index == -1)
		{
			fileName = null;
		}
		else
		{
			int parent = ids[index] / 100;
			int type = (ids[index] * 100 + indexColor);
			fileName = type + "/" + ("soul-" + type + "-" + fileName + ext);
		}
		return fileName;
	}

	private List<Process> processes;
	public static String[] names = 
	{
		"白虎魂（地）", "白虎魂（圣）", "白虎魂（天）", 
		"混沌魂（地）", "混沌魂（圣）", "混沌魂（天）", 
		"夔牛魂（地）", "夔牛魂（圣）", "夔牛魂（天）",
		"麒麟魂（地）", "麒麟魂（圣）", "麒麟魂（天）",
		"青龙魂（地）", "青龙魂（圣）", "青龙魂（天）",
		"饕餮魂（地）", "饕餮魂（圣）", "饕餮魂（天）",
		"玄武魂（地）", "玄武魂（圣）", "玄武魂（天）",
		"重明魂（地）", "重明魂（圣）", "重明魂（天）",
		"朱雀魂（地）", "朱雀魂（圣）", "朱雀魂（天）"
	};
	
	public static int[] ids = 
	{
		111, 311, 211,
		114, 314, 214,
		122, 322, 222,
		123, 323, 223,
		115, 315, 215,
		124, 324, 224,
		113, 313, 213,
		121, 321, 221,
		112, 312, 212
	};
	
	public static String[] colors = 
	{
		"", "绿", "蓝", "紫", "橙", "", "", "金"
	};
}
