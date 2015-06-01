package e1.tools.utils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import e1.tools.betraygods.multilang.ErrThread;
import e1.tools.betraygods.multilang.InThread;

public class ExeAdapter 
{
	public static void exeProcess(String exe)
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
			e.printStackTrace();
		}
		
		
		try 
		{
			countDown.await(120*1000, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
