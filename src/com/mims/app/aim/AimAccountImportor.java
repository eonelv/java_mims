/**
 * 
 */
package com.mims.app.aim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * √Ë ˆ:.<p>
 *
 * @author ¿ÓÕ˛ 
 *
 * @Date: 2011-7-22
 * 
 */
public class AimAccountImportor
{
	
	public static AimFile imports(String path) throws IOException
	{
		return imports(new File(path));
	}
	
	public static AimFile imports(File file) throws IOException
	{
		BufferedReader stream = new BufferedReader(new FileReader(file)); //  ‰»Î¡˜
		
		AccountInfo info = null;
		String line;
		AimFile aimFile = AimFile.getFile();
		while ((line = stream.readLine()) != null)
		{
			info = new AccountInfo();
			if (AimUtils.isNull(line))
			{
				continue;
			}
			String[] temps = line.trim().split("\\|");
			if (temps.length != 3)
			{
				continue;
			}
			info.setType(temps[0]);
			info.setName(temps[1]);
			info.setPassword(temps[2]);
			
			aimFile.getBody().getAccountInfos().add(info);
		}
		stream.close();
		
		return aimFile;
	}
	
	public static AimFile imports(String path, String fileName) throws IOException, ClassNotFoundException
	{
		File file = new File(path + "/" + fileName);
		return imports(file);
	}
	
}
