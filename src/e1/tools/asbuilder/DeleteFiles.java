package e1.tools.asbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class DeleteFiles 
{
	public static void main(String[] args) 
	{
		if ((args == null) || (args.length < 2)) 
		{
			System.out.println("JAVA::Too less params");
			return;
		}
		String path = args[0];
		String fileListParam = args[1];

		File fileList = new File(fileListParam);

		StringBuilder strFiles = new StringBuilder("");

		BufferedReader reader = null;
		String line = null;
		try 
		{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileList), "GB2312"));
			while ((line = reader.readLine()) != null) 
			{
				strFiles.append(line);
				strFiles.append("\r\n");
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		File dirs = new File(path);
		processFiles(dirs, strFiles.toString());
		System.out.println("");
		System.out.println("JAVA::ASBuilder delete temp files over!!!");
	}

	private static void processFiles(File file, String filterString) {
		String name = file.getName();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				processFiles(files[i], filterString);
			}

		} else if (name.indexOf(".swf") != -1) {
			if (filterString.indexOf(name) == -1) {
				file.setWritable(true);
				file.delete();
				System.out.println("JAVA:: delete file " + name);
			} else {
				System.out.println("JAVA:: File export " + name);
			}
		} else {
			file.setWritable(true);
			file.delete();
			System.out.println("JAVA:: delete file " + name);
		}
	}
}