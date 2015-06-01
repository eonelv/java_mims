package e1.tools.suite.texture;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.swing.JProgressBar;

import e1.tools.suite.SystemParams;
import e1.tools.utils.ExeAdapter;

public class Png2AtfThread extends Thread
{
	private File rootFile;
	private JProgressBar progressBar;
	private String sourceRootFolder;
	private CountDownLatch countDown;
	
	public Png2AtfThread(File root, JProgressBar bar, String rootfolder, CountDownLatch down)
	{
		rootFile = root;
		progressBar = bar;
		sourceRootFolder = rootfolder;
		countDown = down;
	}
	
	public void run()
	{
		pngs2Atf(rootFile);
		countDown.countDown();
	}
	
	private void pngs2Atf(File file)
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
				pngs2Atf(files[i]);
			}
		}
		else if (file.getName().endsWith(".png"))
		{
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			
			String cmd = SystemParams.PNG2ATF + " -n 0,0 -i " + file.getAbsolutePath() + " -o " + sourceRootFolder + "\\dest\\" + fileName + ".atf";
			ExeAdapter.exeProcess(cmd);
			progressBar.setValue(progressBar.getValue() + 1);
			progressBar.setString((progressBar.getValue())+"/" + progressBar.getMaximum());
		}
	}

}
