package e1.tools.suite.texture;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.swing.JProgressBar;

import e1.tools.suite.SystemParams;
import e1.tools.utils.ExeAdapter;

public class SpriteSheetThread extends Thread 
{
	private File rootFile;
	private JProgressBar progressBar;
	private String sourceRootFolder;
	private CountDownLatch countDown;
	
	public SpriteSheetThread(File root, JProgressBar bar, String rootfolder, CountDownLatch down)
	{
		rootFile = root;
		progressBar = bar;
		sourceRootFolder = rootfolder;
		countDown = down;
	}
	
	public void run()
	{
		toSheet(rootFile);
		countDown.countDown();
	}
	
	private void toSheet(File file)
	{
		if (file == null)
		{
			return;
		}
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			int length = files.length;
			for (int i = 0; i < length; i++)
			{
				toSheet(files[i]);
			}
		}
		else if (file.getName().endsWith(".png"))
		{
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			
			String cmd = SystemParams.TEXTURE_PACKER + " %1$s --format sparrow --shape-padding 0 --border-padding 0  --padding 0 --opt RGBA8888 --sheet %2$s --data %3$s";
			cmd = String.format(cmd, file.getAbsolutePath(), sourceRootFolder + "/dest/" + fileName + ".png", sourceRootFolder + "/dest/" + fileName + ".xml");
			ExeAdapter.exeProcess(cmd);
			progressBar.setValue(progressBar.getValue() + 1);
			progressBar.setString((progressBar.getValue())+"/" + progressBar.getMaximum());
		}
	}
}
