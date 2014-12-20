package e1.tools.asbuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import e1.tools.asbuilder.utils.DB;
import e1.tools.utils.FileCopyer;

public class CreateEnv {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		CreateEnv createEnv = new CreateEnv();
		try 
		{
			createEnv.process(args);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void process(String[] args) throws IOException
	{
		String dbPath = args[0];
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		Connection con = DB.getConn(dbPath + "/version.db");
		Statement sm = null;
		ResultSet rs = null;
		Map<String, String> projectInfo = null;
		try 
		{
			sm = con.createStatement();
			
			rs = sm.executeQuery("select pname_en, pvname_en, svnbase from t_vb_project where pnumber=1 and isClient=1;");
			
			while (rs.next()) 
			{
				projectInfo = new HashMap<String, String>();
				projectInfo.put("pname_en", rs.getString("pname_en"));
				projectInfo.put("pvname_en", rs.getString("pvname_en"));
				projectInfo.put("svnbase", rs.getString("svnbase"));
				results.add(projectInfo);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			DB.clear(con, sm, rs);
		}
		
		Iterator<Map<String, String>> it = results.iterator();
		String envPath = null;
		File envDir = null;
		File envTemplete = null;
		while (it.hasNext())
		{
			projectInfo = it.next();
			envPath = dbPath + "build/" + projectInfo.get("pname_en") + "/" + projectInfo.get("pvname_en");
			envDir = new File(envPath);
			if (!envDir.exists())
			{
				envDir.mkdirs();
			}
			//复制环境模板
			envTemplete = new File(dbPath + "build/" + projectInfo.get("pname_en") + "/template");
			if (!envTemplete.exists())
			{
				continue;
			}
			copyEnvTemplate(envTemplete, envPath);
			replaceSVNPath(envPath, projectInfo.get("svnbase"));
		}
	}
	
	private void copyEnvTemplate(File srcDir, String destDir) throws IOException
	{
		if (srcDir.isDirectory())
		{
			File[] files = srcDir.listFiles();
			int length = files.length;
			for (int i=0; i < length; i++)
			{
				copyEnvTemplate(files[i], destDir);
			}
		}
		else
		{
			File destFile = new File(destDir + "/" + srcDir.getName());
			FileCopyer.copyFile(srcDir, destFile);
		}
	}
	
	private void replaceSVNPath(String envPath, String svnBase)
	{
		File file = new File(envPath + "/setpath.bat");
		if (!file.exists()) 
		{
			return;
		}
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try 
		{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));

			while ((line = reader.readLine()) != null) 
			{
				line = line.replaceAll("@", svnBase);
				sb.append(line).append("\n");
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
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		catch (IOException e) 
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
		finally 
		{
			try 
			{
				reader.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		BufferedWriter writer = null;
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write(sb.toString());
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				writer.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	

}
