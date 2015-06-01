package e1.tools.suite.multilang;

import java.util.concurrent.CountDownLatch;

import e1.tools.betraygods.multilang.MultiMD5Check;
import e1.tools.suite.IProgress;

public class ResourceMD5CompareThread extends Thread 
{
	private IProgress progress;
	private MultiMD5Check check;
	private CountDownLatch countDown;
	private String src;
	private String dest;
	private String copyTo;
	
	public ResourceMD5CompareThread(IProgress progress, String src, String dest, String copyTo, CountDownLatch countDown)
	{
		this.progress = progress;
		this.countDown = countDown;
		this.src = src;
		this.dest = dest;
		this.copyTo = copyTo;
	}
	
	public void run()
	{
		check = new MultiMD5Check(progress);
		check.process(src, dest, copyTo);
		countDown.countDown();
	}
}
