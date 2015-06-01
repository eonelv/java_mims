package e1.tools.suite.map;

import java.io.File;
import java.io.IOException;
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
			if (progress != null)
			{
				progress.notifyProgress(i+1, size, "");
			}
			file = files.get(i);
			if (file.getParentFile() == null)
			{
				continue;
			}
			if (file.getParentFile().getParentFile() == null)
			{
				continue;
			}
			if (!file.getParentFile().getParentFile().getName().equalsIgnoreCase("maps"))
			{
				continue;
			}
			
			fileName = file.getParentFile().getName();
			tempRoot = findRootFolder(file);
			if (tempRoot == null)
			{
				continue;
			}
			if (rootFolder == null)
			{
				rootFolder = tempRoot;
				outFolder = new File(rootFolder.getAbsolutePath() + "/out/maps/" + fileName);
			}
			else if (rootFolder.getAbsolutePath().equalsIgnoreCase(tempRoot.getAbsolutePath()))
			{
				rootFolder = tempRoot;
				outFolder = new File(rootFolder.getAbsolutePath() + "/out/maps/" + fileName);
			}
			targetFolder = new File(rootFolder.getAbsolutePath() + "/ui/deploy/maps/" + fileName);
			
			publishTPS(file);
			
			if (!targetFolder.exists())
			{
				targetFolder.mkdirs();
			}
			if (!outFolder.exists())
			{
				outFolder.mkdirs();
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
					copyFile(new File(atfFileName+".atf"), outFolder);
					copyFile(new File(atfFileName+".xml"), outFolder);
				} 
				catch (IOException e) 
				{
					logger.error(e.getMessage(), e);
				}
				
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
		String exe = SystemParams.PNG2ATF;
		
		String cmd = readCmd(folder);
		ExeAdapter.exeProcess(exe + " " + cmd);
	}
	
	private String readCmd(File folder)
	{
		return String.format("-c d -r -n 0,0 -i %1$s -o %2$s", folder.getAbsolutePath() + "/small.png", folder.getAbsolutePath() + "/small.atf");
	}
	
	private void buildTamf(String name) throws IOException
	{
//		DataInputStream in = new DataInputStream(new FileInputStream(new File(name + ".atf")));
//		DataInputStream in2 = new DataInputStream(new FileInputStream(new File(name + ".xml")));
//		
//		int textureSize = in.available();
//		int xmlSize = in2.available();
//		
//		byte[] bytes = new byte[textureSize];
//		in.read(bytes);
//		
//		byte[] bytes2 = new byte[xmlSize];
//		in2.read(bytes2);
//		
//		ByteBuffer buffer = ByteBuffer.allocate(textureSize + xmlSize + 4).order(ByteOrder.LITTLE_ENDIAN);
//		buffer.putInt(textureSize);
//		buffer.put(bytes);
//		buffer.put(bytes2);
//		
//		DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(name + ".tamf")));
//		out.write(buffer.array());
//		
//		in.close();
//		in2.close();
//		
//		out.close();
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
