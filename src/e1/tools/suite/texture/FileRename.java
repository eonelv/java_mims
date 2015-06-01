package e1.tools.suite.texture;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.mims.core.log.LogSystem;

import e1.tools.suite.ToolSuite;
import e1.tools.suite.IProgress;
import e1.tools.suite.SystemParams;
import e1.tools.utils.ExeAdapter;
import e1.tools.utils.FileCopyer;
import e1.tools.utils.FileUtils;

public class FileRename 
{
	private static Logger logger = LogSystem.getLogger(FileRename.class);
	
	private String rootPath;
	private String pathRenameTo;
	private IProgress progress;
	private int currentProgressNum = 0;
	private boolean isDXT5 = false;
	
	public static void main(String[] args) 
	{
		System.out.println("********************************************");
		System.out.println("params args[0] is root path that contains the file will be rename. ");
		System.out.println("params args[1] is name format like 'item-#.png'. # will be replaced by the old name.");
		System.out.println("params args[2] is path rename to.");
		if (args.length < 2)
		{
			System.out.println("params is not match!");
			return;
		}
		FileRename fileRename = new FileRename(null);
		String renameTo = "";
		if (args.length > 2)
		{
			renameTo = args[2];
		}
		fileRename.process(args[0], args[1], renameTo, true);
	}
	
	public FileRename(IProgress progress)
	{
		this.progress = progress;
	}
	
	public void process(String path, String format, String renameTo, boolean isDXT5)
	{
		format = format.replace("#", "%1$s");
		if (!renameTo.equalsIgnoreCase(""))
		{
			pathRenameTo = renameTo;
		}
		else
		{
			pathRenameTo = path;
		}
		this.isDXT5 = isDXT5;
		rootPath = path;
		File rootFile = new File(path);
		int count = FileUtils.calcFileCount(rootFile, 0);
		rename(rootFile, format, count);
	}
	
	private void rename(File file, String format, int count)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			int fileLength = files.length;
			for (int i=0; i < fileLength; i++)
			{
				rename(files[i], format, count);
			}
		}
		else if (file.getName().endsWith(".png"))
		{
			String name = file.getName();
			int index = name.lastIndexOf('.');
			
			format += ".atf";
			name = name.substring(0, index);
			String absloutePath = file.getParentFile().getAbsolutePath();
			String relativePath = absloutePath.substring(rootPath.length(), absloutePath.length());
			
			String cmd = SystemParams.PNG2ATF;
			if (isDXT5)
			{
				cmd += " -c d -r";
			}
			cmd += " -n 0,0 -i " + file.getAbsolutePath() + " -o " + pathRenameTo + "/" + relativePath + "/" + String.format(format, name);
			ExeAdapter.exeProcess(cmd);
			currentProgressNum++;
			if (progress != null)
			{
				progress.notifyProgress(currentProgressNum, count, "");
			}
		}
		else
		{
			String name = file.getName();
			int index = name.lastIndexOf('.');
			String ext = name.substring(index);
			
			format += ext;
			name = name.substring(0, index);
			String absloutePath = file.getParentFile().getAbsolutePath();
			String relativePath = absloutePath.substring(rootPath.length(), absloutePath.length());
			try 
			{
				FileCopyer.copyFile(file, new File(pathRenameTo + "/" + relativePath + "/" + String.format(format, name)));
			} 
			catch (IOException e) 
			{
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
			currentProgressNum++;
			if (progress != null)
			{
				progress.notifyProgress(currentProgressNum, count, "");
			}
		}
	}

}
