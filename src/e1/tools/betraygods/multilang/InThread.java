package e1.tools.betraygods.multilang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class InThread extends Thread 
{
	private Process process;
	private CountDownLatch latch;
	
	public InThread(Process pro, CountDownLatch l) 
	{
		this.process = pro;
		this.latch = l;
	}
	
	public void run()
	{
		InputStream is = null;
		InputStreamReader iReader = null;
		BufferedReader bReader = null;
		try
		{
			is = process.getInputStream();
			iReader = new InputStreamReader(is);
			bReader = new BufferedReader(iReader);
			String line = "";
			StringBuilder sb = new StringBuilder();
			while((line = bReader.readLine()) != null)
			{
				sb.append(line);
			}
			System.out.println("Info: " + sb.toString());
			is.close();
			iReader.close();
			bReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		latch.countDown();
		
	}
}
