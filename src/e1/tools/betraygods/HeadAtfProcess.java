package e1.tools.betraygods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import e1.tools.utils.FileCopyer;

public class HeadAtfProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		if (args.length < 6)
		{
			System.out.println("参数不正确，请确定输出输入目录");
//			return;
		}
		HeadAtfProcess processor = new HeadAtfProcess();
//		processor.srcPath = "C:\\Users\\lv\\Desktop\\monsterheadsrc";
//		processor.destPath = "C:\\Users\\lv\\Desktop\\monsterhead\\dest";//args[1];
//		processor.svnPath = "C:\\Users\\lv\\Desktop\\monsterhead\\svn";
//		processor.prefix = "monsterhead";
		
		processor.srcPath = args[0];
		processor.destPath = args[1];
		processor.svnPath = args[2];
		processor.prefix = args[3];
		processor.atfToolPath = args[4];
		processor.textureToolPath = args[5];
		
		try 
		{
			processor.process();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void process() throws IOException
	{
		File svn = new File(svnPath);
		System.out.println("SVNPATH::" + svnPath);
		if (!svn.exists())
		{
			svn.mkdirs();
			System.out.println("SVN File::" + svn.getAbsolutePath());
		}
		processes = new ArrayList<Process>();
		boolean isSuccess = readRelations();
		if (!isSuccess)
		{
			return;
		}
		File srcFile = new File(srcPath);
		processFile(srcFile);
		
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
		copyFilesToSvn();
		reportError();
	}
	
	private void processFile(File file) throws IOException
	{
		if (file.isFile())
		{
			if (file.getName().endsWith(".csv"))
			{
				return;
			}
			String name = file.getName();
			String shortName = name.substring(0, name.length() - 4);
			String destName = srcRelation.get(shortName);
			if (destName == null)
			{
				errorRelation.put(shortName, null);
				System.out.println("ERROR: png has no correct name!");
				return;
			}
			srcRelation.remove(shortName);
			File destFile = new File(destPath + "/" + destName + "/" + prefix + "-" + destName + ".png");
			if (!destFile.getParentFile().exists())
			{
				destFile.getParentFile().mkdirs();
			}
			FileCopyer.copyFile(file, destFile);
		}
		else
		{
			File[] files = file.listFiles();
			for (int i=0; i < files.length; i++)
			{
				processFile(files[i]);
			}
		}
	}
	
	private void processAtf() throws IOException
	{
		File destFile = new File(destPath);
		File[] files = destFile.listFiles();
		if (files == null || files.length == 0)
		{
			return;
		}
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
			
			InputStream fis = process.getInputStream();

		    InputStreamReader isr = new InputStreamReader(fis);

		    BufferedReader br = new BufferedReader(isr);
//		    String line = null;
//
//		    while ((line = br.readLine()) != null)
//		    {
//		        System.out.println(line);
//		    }
		    System.out.println("Atf: " + folder.getName() + " processed!");
		    br.close();
		    processes.add(process);
		}
	}
	
	private void processTexture() throws IOException
	{
		File destFile = new File(destPath);
		File[] files = destFile.listFiles();
		if (files == null || files.length == 0)
		{
			return;
		}
		File folder;
		String cmd;
		for (int i=0; i < files.length; i++)
		{
			folder = files[i];
			if (!folder.isDirectory())
			{
				continue;
			}
			cmd = textureToolPath + " --width 128 --height 128 --shape-padding 0 --border-padding 0  --padding 0 --no-trim --data " 
					+ folder.getParentFile().getAbsolutePath() + "/" + prefix + "-"
					+ folder.getName() 
					+ ".xml --format sparrow --sheet " 
					+ folder.getParentFile().getAbsolutePath() + "/" + prefix + "-"
					+ folder.getName() 
					+ ".png --opt RGBA8888 "
					+ folder.getAbsolutePath();
			System.out.println("textureToolPath" + cmd);
			Process process = Runtime.getRuntime().exec(cmd);
			
			InputStream fis = process.getInputStream();

		    InputStreamReader isr = new InputStreamReader(fis);

		    BufferedReader br = new BufferedReader(isr);
//		    String line = null;
//		    while ((line = br.readLine()) != null)
//		    {
//		        System.out.println(line);
//		    }
		    System.out.println("Texture: " + folder.getName() + " processed!");
		    br.close();
		    processes.add(process);
		}
	}
	
	private void copyFilesToSvn() throws IOException
	{
		File destFile = new File(destPath);
		File[] files = destFile.listFiles();
		if (files == null || files.length == 0)
		{
			return;
		}
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
	
	private void reportError()
	{
		File destFile = new File(svnPath + "/report.txt");
		if (!destFile.exists())
		{
			try 
			{
				destFile.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		Iterator<String> it = srcRelation.keySet().iterator();
		StringBuilder error = new StringBuilder();
		error.append("未找到指定文件：").append("\r\n");
		while (it.hasNext())
		{
			error.append(it.next()).append("\r\n");
		}
		it = errorRelation.keySet().iterator();
		error.append("\r\n");
		error.append("有文件未配置：").append("\r\n");
		while (it.hasNext())
		{
			error.append(it.next()).append("\r\n");
		}
		BufferedWriter writer = null;
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter((new FileOutputStream(destFile))));
			writer.write(error.toString());
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				writer.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	private boolean readRelations()
	{
		File srcFile = new File(srcPath + "/relation.csv");
		if (!srcFile.exists())
		{
			System.out.println("配置文件不存在:"+srcFile.getAbsolutePath());
			return false;
		}
		BufferedReader reader = null;
		String line;
		try 
		{
			reader = new BufferedReader(new InputStreamReader
					(new FileInputStream(srcFile), "gbk"));

			while ((line = reader.readLine()) != null) 
			{
				if (line == "")
				{
					continue;
				}
				String[] values = line.split(",");
				srcRelation.put(values[1], values[0]);
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e.printStackTrace();
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		} 
		finally 
		{
			try 
			{
				reader.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private List<Process> processes;
	public Map<String, String> srcRelation = new HashMap<String, String>();
	//有文件，没配置
	public Map<String, String> errorRelation = new HashMap<String, String>();
	public String srcPath = null;//配置文件，png源文件
	public String destPath = null;//atf、xml输出文件
	public String svnPath = null;//atf、xml输出文件
	public String prefix = null;//文件输出前缀
	public String atfToolPath = "E:/Projects/betraygods/resources/ui/deploy/png2atf.exe";//atf工具位置exe
	public String textureToolPath = "F:/Flash/software/TexturePacker/bin/TexturePacker.exe";//atf工具位置exe
}
