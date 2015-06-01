package e1.tools.suite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemParams 
{
	private static Logger logger = Logger.getLogger(SystemParams.class);
	
	public static String PNG2ATF = "E:\\Projects\\bg_vietnam\\resources\\ui\\deploy\\png2atf.exe";
	public static String TEXTURE_PACKER = "F:/Flash/software/TexturePacker/bin/TexturePacker.exe";
	public static String FONT_NAME = "宋体";
	
	public static boolean loadConfig()
	{
		URL url = MenuUI.class.getClassLoader().getResource("cfg.properties");
		
		File file;
		try 
		{
			file = new File(url.toURI());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			
			Properties properties = new Properties();
			properties.load(reader);
			
			SystemParams.PNG2ATF = properties.getProperty("PNG2ATF");
			SystemParams.TEXTURE_PACKER = properties.getProperty("TEXTURE_PACKER");
			return true;
		} 
		catch (URISyntaxException e) 
		{
			logger.error(e.getMessage(), e);
		}
		catch (UnsupportedEncodingException e) 
		{
			logger.error(e.getMessage(), e);
		} 
		catch (FileNotFoundException e) 
		{
			logger.error(e.getMessage(), e);
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
		return false;
	}
}
