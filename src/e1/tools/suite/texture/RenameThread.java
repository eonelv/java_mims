package e1.tools.suite.texture;

import java.util.concurrent.CountDownLatch;

import javax.swing.JProgressBar;

import e1.tools.suite.IProgress;

public class RenameThread extends Thread implements IProgress
{
	private JProgressBar progressBar;
	private CountDownLatch countDown;
	private FileRename fileRename;
	private String path;
	private String format;
	private String renameTo;
	private boolean isDXT5 = false;
	
	public RenameThread(JProgressBar bar, String path, String format, String renameTo, CountDownLatch countDown, boolean isDXT5)
	{
		this.countDown = countDown;
		progressBar = bar;
		this.fileRename = new FileRename(this);
		this.path = path;
		this.format = format;
		this.renameTo = renameTo;
		this.isDXT5 = isDXT5;
	}
	
	public void run()
	{
		fileRename.process(path, format, renameTo, isDXT5);
		countDown.countDown();
	}

	public void notifyProgress(int value, int count, String text) 
	{
		progressBar.setValue(value);
		progressBar.setString(value+ "/" + count);
	}

	public void notifyProgressTotal(int value, int max, String text) 
	{
		
	}
}
