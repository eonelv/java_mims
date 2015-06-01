package e1.tools.betraygods.multilang;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import e1.tools.utils.FileCopyer;

public class MultiLang 
{
	private static Logger logger = Logger.getLogger(MultiLang.class);
	
	private String path;
	static final int FLAG_PUBLISH_TEXTURE = 0x1;
	static final int FLAG_BUILD_ATF = 0x2;
	static final int FLAG_BUILD_TMAF = 0x4;
	static final int FLAG_COPY_FILE = 0x8;
	static final int FLAG_COPY_MAP_FILE = 0x10;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		MultiLang app = new MultiLang();
		Integer step = Integer.parseInt(args[3]);
		app.process(args[0], args[1], args[2], step);
	}
	
	public void process(String path, String exe, String exeTexture, int step)
	{	
		this.path = path;
		setFilesWriteable(new File(path + "/ui/deploy"));
		if ((step & FLAG_PUBLISH_TEXTURE) == FLAG_PUBLISH_TEXTURE)
		{
			publicTexturePngs(exe, new File(path + "/ui/export"));
		}
		if ((step & FLAG_BUILD_ATF) == FLAG_BUILD_ATF)
		{
			buildTextures(exeTexture, new File(path + "/ui/deploy"));
			buildMapTextures(exeTexture, new File(path + "/ui/deploy/maps"));
		}
		if ((step & FLAG_BUILD_TMAF) == FLAG_BUILD_TMAF)
		{
			buildTamfs(new File(path + "/ui/deploy"));
		}
		
		setFilesWriteable(new File(path + "/out/gui"));
		setFilesWriteable(new File(path + "/out/maps"));
		
		if ((step & FLAG_COPY_FILE) == FLAG_COPY_FILE)
		{
			copyFiles();
		}
		if ((step & FLAG_COPY_MAP_FILE) == FLAG_COPY_MAP_FILE)
		{
			copyMapFiles();
		}
		System.out.println("Complete !");
	}
	
	private void setFilesWriteable(File file)
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
	
	private void publicTexturePngs(String exe, File folder)
	{
		if (folder.isDirectory())
		{
			File[] files = folder.listFiles();
			
			for (int i = 0; i < files.length; i++)
			{
				publicTexturePngs(exe, files[i]);
			}
			
		}
		else if (folder.getName().endsWith(".tps"))
		{
			String name = folder.getAbsolutePath();
			exeProcess(exe + " " + name);
		}
	}
	
	private void exeProcess(String exe)
	{
		ErrThread errThread;
		InThread inThread;
		
		CountDownLatch countDown = new CountDownLatch(2);
		
		Process process;
		try 
		{
			process = Runtime.getRuntime().exec(exe);
			process.getOutputStream().close();
			errThread = new ErrThread(process, countDown);
			inThread = new InThread(process, countDown);
			
			errThread.start();
			inThread.start();
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
		
		
		try 
		{
			countDown.await(10000, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) 
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	private void buildTextures(String exe, File folder)
	{
		if (folder.isDirectory())
		{
			File[] files = folder.listFiles();
			
			for (int i = 0; i < files.length; i++)
			{
				buildTextures(exe, files[i]);
			}
		}
		else if (folder.getName().endsWith(".cmd") || folder.getName().endsWith(".bat"))
		{
			String cmd = readCmd(folder);
			if (cmd == null)
			{
				return;
			}
			exeProcess(exe + " " + cmd);
		}
	}
	
	private void buildMapTextures(String exe, File folder)
	{
		File[] files = folder.listFiles();
		String exeCmd = null;
		for (int i=0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				String cmd = buildMapCmd(files[i]);
				exeCmd = exe + " " + cmd;
				
				exeProcess(exeCmd);
			}
		}
	}
	
	private String buildMapCmd(File folder)
	{
		String cmd = "-c d -r -n 0,0 -i ";
		cmd = cmd + folder.getAbsolutePath() + "/small.png";
		cmd = cmd + " -o " + folder.getAbsolutePath() + "/small.atf";
		return cmd;
	}
	
	private String readCmd(File folder)
	{
		if (folder.getParentFile().getName() == "maps")
		{
			return null;
		}
		InputStream is = null;
		Reader iReader = null;
		BufferedReader bReader = null;
		try 
		{
			is = new FileInputStream(folder);
			iReader = new InputStreamReader(is);
			bReader = new BufferedReader(iReader);
			String line;
			String input;
			while ((line = bReader.readLine()) != null)
			{
				int index = line.indexOf("png2atf");
				if (index == -1)
				{
					continue;
				}
				line = line.substring(index);
				String[] args = line.split(" ");
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < args.length; i++)
				{
					if (args[i].equalsIgnoreCase("-i"))
					{
						input = args[i+1];
						args[i+1] = folder.getParentFile().getAbsolutePath() + "/" + input;
					}
					else if (args[i].equalsIgnoreCase("-o"))
					{
						input = args[i+1];
						args[i+1] = folder.getParentFile().getAbsolutePath() + "/" + input;
					}
					if (i != 0)
					{
						sb.append(args[i]).append(" ");
					}
				}
				return sb.toString();
			}
		} 
		catch (FileNotFoundException e) 
		{
			logger.error(e.getMessage(), e);
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			try 
			{
				is.close();
				iReader.close();
				bReader.close();
			} 
			catch (IOException e) 
			{
				logger.error(e.getMessage(), e);
			}
			
		}
		return null;
	}
	
	private void buildTamfs(File path)
	{
		if (path == null)
		{
			return;
		}
		
		String fileName = path.getAbsolutePath();
		if (path.isDirectory())
		{
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				buildTamfs(files[i]);
			}
		}
		else if (fileName.endsWith(".atf"))
		{
			try 
			{
				buildTamf(fileName.substring(0, fileName.length() - 4));
			} 
			catch (IOException e) 
			{
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private void buildTamf(String name) throws IOException
	{
		System.out.println("STEP::buildTamf--" + name);
		DataInputStream in = new DataInputStream(new FileInputStream(new File(name + ".atf")));
		DataInputStream in2 = new DataInputStream(new FileInputStream(new File(name + ".xml")));
		
		int textureSize = in.available();
		int xmlSize = in2.available();
		
		byte[] bytes = new byte[textureSize];
		in.read(bytes);
		
		byte[] bytes2 = new byte[xmlSize];
		in2.read(bytes2);
		
		ByteBuffer buffer = ByteBuffer.allocate(textureSize + xmlSize + 4).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(textureSize);
		buffer.put(bytes);
		buffer.put(bytes2);
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(name + ".tamf")));
		out.write(buffer.array());
		
		in.close();
		in2.close();
		
		out.close();
		
		
	}
	
	private void copyMapFiles()
	{
		File dest = new File(this.path + "/out/maps");
		File src = new File(this.path + "/ui/deploy/maps");
		copyMapFile(src.getAbsolutePath(), dest, src);
	}
	
	private void copyMapFile(String baseSrc, File dest, File src)
	{
		if (src.isDirectory())
		{
			File[] files = src.listFiles();
			for (int i=0; i < files.length; i++)
			{
				copyMapFile(baseSrc, dest, files[i]);
			}
		}
		else
		{
			try 
			{
				String path = src.getAbsolutePath();
				path = path.substring(baseSrc.length()+1);
				FileCopyer.copyFile(src, new File(dest.getAbsolutePath() + "/" + path));
			} 
			catch (IOException e) 
			{
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private void copyFiles()
	{
		File dest = new File(this.path + "/out/gui");
		File src = new File(this.path + "/ui/deploy");
		Map<String, String> destFiles = new HashMap<String, String>();
		listFiles(dest, destFiles);
		copyFile(src, destFiles);
	}
	
	private void copyFile(File src, Map<String, String> destFiles)
	{
		if (src.isDirectory())
		{
			File[] files = src.listFiles();
			for (int i=0; i < files.length; i++)
			{
				copyFile(files[i], destFiles);
			}
			return;
		}
		String dest = destFiles.get(src.getName());
		if (dest == null)
		{
			return;
		}
		try 
		{
			FileCopyer.copyFile(src, new File(dest));
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	private void listFiles(File dest, Map<String, String> destFiles)
	{
		if (dest.isDirectory())
		{
			File[] files = dest.listFiles();
			for (int i=0; i < files.length; i++)
			{
				listFiles(files[i], destFiles);
			}
			
		}
		else
		{
			destFiles.put(dest.getName(), dest.getAbsolutePath());
		}
	}
	
//	private void copyFiles()
//	{
//		System.out.println("STEP::copyFiles");
//		exeRuntime(path + "/A3_Texturepkgsyn.cmd");
//	}

}