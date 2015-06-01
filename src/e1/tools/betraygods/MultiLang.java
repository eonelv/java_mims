package e1.tools.betraygods;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MultiLang 
{
	
	private String path;
	
	private TimerTaskA task;
	private Timer timer;
	
	public static boolean gotoNext = false;
	
	private Map<String, ProcessInfo> fileDatas = new HashMap<String, ProcessInfo>();
	List<EXEThread> threads = new ArrayList<EXEThread>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MultiLang app = new MultiLang();
		Integer step = Integer.parseInt(args[2]);
		app.process(args[0], args[1], step);
	}
	
	public void process(String path, String exe, int step)
	{	
		this.path = path;
		setFilesWriteable(new File(path + "/ui/deploy"));
		if ((step & 1) == 1)
		{
			publishTexturePng(exe);
		}
		if ((step & 2) == 2)
		{
			buildTexture();
		}
		if ((step & 8) == 8)
		{
			buildTamfs(new File(path + "/ui/deploy"));
		}
		
		setFilesWriteable(new File(path + "/out/gui"));
		if ((step & 16) == 16)
		{
			copyFiles();
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
			System.out.println("Now processing file: " + name);
			EXEThread thread = new EXEThread(name, exe + " " + folder.getAbsolutePath(), fileDatas);
			ProcessInfo info = new ProcessInfo();
			info.name = name;
			info.period = System.currentTimeMillis();
			
			fileDatas.put(info.name, info);
			
			threads.add(thread);
		}
	}
	
	private void publishTexturePng(String exe)
	{
//		exeRuntime(path + "/A1_PublishTexture.cmd");
		String EXE = exe;
		File folder = new File(path + "/ui/export");
//		File[] files = folder.listFiles();
//		List<EXEThread> threads = new ArrayList<EXEThread>();
		
		publicTexturePngs(EXE, folder);
//		for (int i = 0; i < files.length; i++)
//		{
//			File file = files[i];
//			
//			if (file.getName().endsWith(".tps"))
//			{
//				System.out.println("Now processing file: " + file.getName());
//				EXEThread thread = new EXEThread(file.getName(), EXE + " " + file.getAbsolutePath(), fileDatas);
//				ProcessInfo info = new ProcessInfo();
//				info.name = file.getName();
//				info.period = System.currentTimeMillis();
//				
//				fileDatas.put(info.name, info);
//				
//				threads.add(thread);
////				thread.start();
////				if(fileDatas.size() >= 25)
////				{
////					break;
////				}
//			}
//		}
		timer = new Timer();
		task = new TimerTaskA(fileDatas);
		timer.schedule(task, 10000, 1000);
		for (int i = 0; i < threads.size(); i++)
		{
			threads.get(i).start();
		}
		
		
		while (!fileDatas.isEmpty())
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void buildTexture()
	{
		System.out.println("STEP::buildTexture");
		exeRuntime(path + "/A2_build.cmd");
	}
	
	private void exeRuntime(String exe)
	{
		Process process;
		try {
			process = Runtime.getRuntime().exec(exe);
			
			//通过cmd程序执行cmd命令
	        //process.waitFor();
	        //读取屏幕输出
	        BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        
	        String line;
	        while ((line = strCon.readLine()) != null) 
	        {
	            System.out.println(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				e.printStackTrace();
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
	
	private void copyFiles()
	{
		System.out.println("STEP::copyFiles");
		exeRuntime(path + "/A3_Texturepkgsyn.cmd");
	}

}

class TimerTaskA extends TimerTask
{
	private Map<String, ProcessInfo> fileDatas;
	
	public TimerTaskA(Map<String, ProcessInfo> datas)
	{
		fileDatas = datas;
	}
	
	public void run() 
	{
		Iterator<String> it = fileDatas.keySet().iterator();
		List<String> keys = new ArrayList<String>();
		while (it.hasNext())
		{
			String key = it.next();
			ProcessInfo info = fileDatas.get(key);
			if (System.currentTimeMillis() - info.period > 30000)
			{
				if (info.process != null)
				{
					info.process.destroy();
				}
				keys.add(key);
			}
		}
		
		for (int i = 0; i < keys.size(); i++)
		{
			fileDatas.remove(keys.get(i));
		}
	}
}

class EXEThread extends Thread
{
	private String exe;
	private String fileName;
	
	private Map<String, ProcessInfo> datas;
	public EXEThread(String fileName, String exe, Map<String, ProcessInfo> datas)
	{
		this.exe = exe;
		this.fileName = fileName;
		this.datas = datas;
	}
	
	public void run()
	{
		exeRuntime(exe);
	}
	
	private void exeRuntime(String exe)
	{
		Process process;
		try 
		{
			process = Runtime.getRuntime().exec(exe);
			ProcessInfo info = new ProcessInfo();
			info.name = fileName;
			info.process = process;
			info.period = System.currentTimeMillis();
			
			datas.put(fileName, info);
			
			//通过cmd程序执行cmd命令
	        //process.waitFor();
	        //读取屏幕输出
	        BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        
	        String line;
	        while ((line = strCon.readLine()) != null) 
	        {
	        	info = datas.get(fileName);
	        	info.period = System.currentTimeMillis();
	        	datas.put(fileName, info);
	            System.out.println(line);
	        }
	        datas.remove(fileName);
		} 
		catch (IOException e) 
		{
			datas.remove(fileName);
			e.printStackTrace();
		}
	}
}

class ProcessInfo
{
	public long period;
	public String name;
	public Process process;
}
