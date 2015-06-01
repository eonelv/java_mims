package e1.tools.suite.tps2tamf;

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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import e1.tools.suite.IProgress;
import e1.tools.suite.SystemParams;
import e1.tools.utils.ExeAdapter;
import e1.tools.utils.FileCopyer;
import e1.tools.utils.FileUtils;

public class TPS2Tamf 
{
	private static Logger logger = Logger.getLogger(TPS2Tamf.class);
	private IProgress progress;
	
	public TPS2Tamf(IProgress progress)
	{
		this.progress = progress;
	}
	
	public void process(List<File> files)
	{
		int size = files.size();
		File file;
		File rootFolder = null;
		File tempRoot;
		File targetFolder = null;//TPS发布生成的文件目录
		String fileName;
		
		File[] atfFiles;
		File atfFile;
		int atfFileSize = 0;
		
		String atfFileName;
		
		File outFolder = null;
		if (progress != null)
		{
			progress.notifyProgress(0, size, "");
		}
		
		for (int i=0; i < size; i++)
		{
			file = files.get(i);
			fileName = file.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			tempRoot = findRootFolder(file);
			if (tempRoot == null)
			{
				continue;
			}
			if (rootFolder == null)
			{
				rootFolder = tempRoot;
				outFolder = new File(rootFolder.getAbsolutePath() + "/out/gui");
			}
			else if (rootFolder.getAbsolutePath().equalsIgnoreCase(tempRoot.getAbsolutePath()))
			{
				rootFolder = tempRoot;
				outFolder = new File(rootFolder.getAbsolutePath() + "/out/gui");
			}
			targetFolder = new File(rootFolder.getAbsolutePath() + "/ui/deploy/" + fileName);
			
			publishTPS(file);
			
			if (!targetFolder.exists())
			{
				continue;
			}
			FileUtils.setFilesWriteable(targetFolder);
			buildATF(targetFolder);
			
			atfFiles = targetFolder.listFiles();
			atfFileSize = atfFiles.length;
			
			
			for (int j=0; j < atfFileSize; j++)
			{
				atfFile = atfFiles[j];
				atfFileName = atfFile.getAbsolutePath();
				if (!atfFileName.endsWith(".atf"))
				{
					continue;
				}
				
				atfFileName = atfFileName.substring(0, atfFileName.length() - 4);
				try 
				{
					buildTamf(atfFileName);
					copyFile(new File(atfFileName+".tamf"), outFolder);
				} 
				catch (IOException e) 
				{
					logger.error(e.getMessage(), e);
				}
				
			}
			
			if (progress != null)
			{
				progress.notifyProgress(i+1, size, "");
			}
		}
	}
	
	private File findRootFolder(File file)
	{
		File parent = file.getParentFile();
		if (parent == null)
		{
			return null;
		}
		if (parent.getName().equalsIgnoreCase("resources"))
		{
			return parent;
		}
		return findRootFolder(parent);
	}
	
	private void publishTPS(File tpsFile)
	{
		String name = tpsFile.getAbsolutePath();
		String exe = SystemParams.TEXTURE_PACKER;
		ExeAdapter.exeProcess(exe + " " + name);
	}
	
	private void buildATF(File folder)
	{
		File[] files = folder.listFiles();
		File file = null;
		String exe = SystemParams.PNG2ATF;
		int size = files.length;
		String fileName = null;
		for (int i = 0; i < size; i++)
		{
			file = files[i];
			fileName = file.getName();
			if (!fileName.endsWith(".cmd") && !fileName.endsWith(".bat"))
			{
				continue;
			}
			String cmd = readCmd(file);
			if (cmd == null)
			{
				continue;
			}
			ExeAdapter.exeProcess(exe + " " + cmd);
		}
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
			int length = 0;
			List<String> tempPrams = new ArrayList<String>();
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
				length = args.length;
				
				tempPrams.clear();
				for (int i = 0; i < length; i++)
				{
					if (!args[i].equalsIgnoreCase(""))
					{
						tempPrams.add(args[i]);
					}
				}
				length = tempPrams.size();
				for (int i = 0; i < length; i++)
				{
					if (tempPrams.get(i).equalsIgnoreCase("-i"))
					{
						input = tempPrams.get(i+1);
						tempPrams.set(i+1, folder.getParentFile().getAbsolutePath() + "/" + input);
					}
					else if (tempPrams.get(i).equalsIgnoreCase("-o"))
					{
						input = tempPrams.get(i+1);
						tempPrams.set(i+1, folder.getParentFile().getAbsolutePath() + "/" + input);
					}
					if (i != 0)
					{
						sb.append(tempPrams.get(i)).append(" ");
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
	
	private void buildTamf(String name) throws IOException
	{
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
	
	private void copyFile(File src, File destFolder)
	{
		File targetFile = findTargetTamf(src, destFolder);
		if (targetFile == null)
		{
			targetFile = new File(destFolder.getAbsolutePath() + "/" + src.getName());
		}
		if (targetFile.exists() && !targetFile.canWrite())
		{
			FileUtils.setFilesWriteable(targetFile);
		}
		try 
		{
			FileCopyer.copyFile(src, targetFile);
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	private File findTargetTamf(File src, File destFolder)
	{
		File[] files = destFolder.listFiles();
		File result;
		File file;
		int size = files.length;
		for (int i=0; i < size; i++)
		{
			file = files[i];
			if (file.isDirectory())
			{
				result = findTargetTamf(src, file);
				if (result != null)
				{
					return result;
				}
			}
			else if (file.getName().equalsIgnoreCase(src.getName()))
			{
				return file;
			}
		}
		return null;
	}
}
