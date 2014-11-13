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
import java.util.Iterator;

import net.sf.json.JSONObject;
import e1.tools.asbuilder.utils.EJSON;
import e1.tools.asbuilder.utils.MD5Utils;
import e1.tools.utils.FileCopyer;

public class MD5Check 
{
	private String basePath = null;

	public static void main(String[] args) 
	{
		String path = args[0];
		String targetPath = args[1];
		boolean isUpdateReslist = args[2].equalsIgnoreCase("1");
		String reslistPath = args[3];
		
		MD5Check MD5Check = new MD5Check();
		try {
			MD5Check.process(path, targetPath, isUpdateReslist, reslistPath, -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void process(String path, String targetPath, boolean isUpdate, String reslistPath, int buildversion) throws IOException 
	{
		this.basePath = path;
		File parent = new File(this.basePath);
		JSONObject newFileMD5s = new JSONObject();
		JSONObject oldFileMD5s = null;
		System.out.println("SysBuildMsg=4. begin calc MD5");
		calMD5(parent, newFileMD5s, buildversion);
//		if (isUpdate)
//		{
//			oldFileMD5s = readOldMD5(reslistPath + "/reslist_srv.json");
//		}
//		else
//		{
//			oldFileMD5s = readOldMD5(reslistPath + "/reslist_srv_inner.json");
//		}
		oldFileMD5s = readOldMD5(reslistPath + "/reslist_srv.json");
		boolean isSuccess = oldFileMD5s != null;
		

		System.out.println("SysBuildMsg=4. comparing file's MD5");
		JSONObject result = new JSONObject();
		if (isSuccess) 
		{
			Iterator<String> it = newFileMD5s.keySet().iterator();

			JSONObject oldValue;
			JSONObject newValue;
			String key;
			String oldKey;
			/*
			 * 对比所有文件的MD5，有差异的存放在result中
			 */
			while (it.hasNext()) 
			{
				key = (String) it.next();
				newValue = (JSONObject)newFileMD5s.get(key);
				
				//由于服务端保存的版本文件的key是eamf，所以这里需要替换json为eamf
//				if (key.endsWith("json"))
//				{
//					oldKey = key.replace("json", "eamf");
//					System.out.println(key + "为json文件，不做处理");
//					continue;
//				}
//				else
//				{
//					oldKey = key;
//				}
				oldValue = (JSONObject)oldFileMD5s.get(key);
				if (oldValue == null) 
				{
					result.put(key, newValue);
				} 
				else 
				{
					if (newValue.getString(EJSON.MD5).equalsIgnoreCase(oldValue.getString(EJSON.MD5))) 
					{
						continue;
					}
					newValue.put(EJSON.VERSION, buildversion);
					result.put(key, newValue);
				}
			}
		} 
		else 
		{
			result = newFileMD5s;
		}
		merge(oldFileMD5s, result);
		moveFiles(result, targetPath, isUpdate, buildversion);
		
		result = changeName2Amaf(oldFileMD5s);
		
		System.out.println("SysBuildMsg=5. update version file");
		/*
		 * 如果是发布版本，需要生成reslist到编译环境及发布包中
		 */
		if (isUpdate) 
		{
			reWriteSrvResList(result, reslistPath + "/reslist_srv.json");
			reWriteSrvResListToClient(result, targetPath, buildversion);
		}
		else
		{
			reWriteSrvResList(result, reslistPath + "/reslist_srv_inner.json");
		}
		reWriteClientResList(result, targetPath, isUpdate, buildversion);
		System.out.println("SysBuildMsg=final. genaratting main application");
	}
	
	/*
	 * 合并之后，返回的是所有文件的完整的MD5集合，用于生产版本文件.
	 *   
	 * @param oldFileMD5s
	 * @param result
	 */
	private void merge(JSONObject oldFileMD5s, JSONObject result)
	{
		@SuppressWarnings("unchecked")
		Iterator<String> it = (Iterator<String>)result.keys();
		String key;
		JSONObject temp;
		while (it.hasNext())
		{
			key = it.next();
			temp = (JSONObject)result.get(key);
			oldFileMD5s.put(key, temp);
		}
	}
	
	/*
	 * 由于此时生成的版本文件，还没有eamf文件，需要提前将版本文件中的键值改为eamf. 
	 * @param newFileMD5s
	 * @return
	 */
	private JSONObject changeName2Amaf(JSONObject newFileMD5s)
	{
		@SuppressWarnings("unchecked")
		Iterator<String> it = newFileMD5s.keySet().iterator();
		JSONObject tempValue;
		String id;
		String key;
		JSONObject tempJsons= new JSONObject();
		while (it.hasNext()) 
		{
			key = (String) it.next();
			tempValue = (JSONObject)newFileMD5s.getJSONObject(key);
			id = tempValue.getString(EJSON.ID);
			if (id.endsWith("json"))
			{
				id = id.replace("json", "eamf");
			}
			tempValue.put(EJSON.ID, id);
			tempJsons.put(id, tempValue);
		}
		return tempJsons;
	}
	
	private void reWriteClientResList(JSONObject result, String resourcelistPath, boolean isUpdate, int buildversion) throws IOException 
	{
		String fileName = resourcelistPath + "/";
		String fileNameXml = resourcelistPath + "/";
		String amfName = resourcelistPath + "/";
		if (isUpdate)
		{
			fileName += ("reslist_v" + buildversion + ".json");
			fileNameXml += ("reslist_v" + buildversion + ".xml");
			amfName += ("reslist_v" + buildversion + ".eamf");
		}
		else
		{
			fileName += "reslist.json";
			fileNameXml += "reslist.xml";
			amfName += "reslist.eamf";
		}
		File file = new File(fileName);
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdir();
		}
		if (!file.exists())
		{
			file.createNewFile();
		}
		BufferedWriter writer = null;
		StringBuilder reslst = new StringBuilder();

		Iterator<String> it = result.keySet().iterator();
		String key;
		JSONObject item;
		while (it.hasNext())
		{
			key = it.next();
			item = (JSONObject)result.get(key);
			item.remove(EJSON.MD5);
		}
		reslst.append(result.toString());
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.write(reslst.toString());
			writer.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CreateAMF createAMF = new CreateAMF();
		try 
		{
			createAMF.create(file, amfName);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
//		//XML
//		File fileXml = new File(fileNameXml);
//		if (!file.getParentFile().exists())
//		{
//			file.getParentFile().mkdir();
//		}
//		if (!file.exists())
//		{
//			file.createNewFile();
//		}
//		BufferedWriter writerXml = null;
//		StringBuilder reslstXml = new StringBuilder();
//
//		Iterator<String> itXml = result.keySet().iterator();
//
//		reslstXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//		reslstXml.append("\n");
//		reslstXml.append("<resources>\n");
//		while (itXml.hasNext())
//		{
//			key = itXml.next();
//			item = (JSONObject)result.get(key);
//			reslstXml.append("<resource id=\"");
//			reslstXml.append(item.getString(EJSON.ID));
//			reslstXml.append("\" version=\"");
//			reslstXml.append(item.getInt(EJSON.VERSION));
//			reslstXml.append("\" size=\"");
//			reslstXml.append(item.getInt(EJSON.SIZE));
//			reslstXml.append("\" />\n");
//		}
//		reslstXml.append("</resources>");
//		try 
//		{
//			writerXml = new BufferedWriter(new OutputStreamWriter(
//					new FileOutputStream(fileXml), "utf-8"));
//			writerXml.write(reslstXml.toString());
//			writerXml.flush();
//		} 
//		catch (UnsupportedEncodingException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
	}

	private void reWriteSrvResList(JSONObject result, String reslistName) 
	{
		File file = new File(reslistName);
		BufferedWriter writer = null;
		StringBuilder reslst = new StringBuilder();

		reslst.append(result.toString());
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.write(reslst.toString());
			writer.flush();
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void reWriteSrvResListToClient(JSONObject result, String targetPath, int version) throws IOException 
	{
		String fileName = targetPath + "/";
		fileName += ("reslist_srv_v" + version + ".json");
		File file = new File(fileName);
		
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdir();
		}
		if (!file.exists())
		{
			file.createNewFile();
		}

		System.out.println("DEBUG::" + fileName);
		BufferedWriter writer = null;
		StringBuilder reslst = new StringBuilder();

		reslst.append(result.toString());
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "utf-8"));
		writer.write(reslst.toString());
		writer.flush();
	}

	/*
	 * 移动文件到指定目录，为后面打包版本做准备.
	 * 
	 * @param result 需要移动的文件
	 * @param targetPath 目标输出路径
	 * @param isUpdate 是否更新版本文件，内网版本不更新
	 */
	@SuppressWarnings("unchecked")
	private void moveFiles(JSONObject result, String targetPath, boolean isUpdate, int buildversion) 
	{
		Iterator<String> it = result.keySet().iterator();

		File targetFile;
		String targetFileFullName;
		String simpleName;
		String extName;
		int index;
		JSONObject tempValue;
		File parent;
		
		System.out.println("SysBuildMsg=4. begin copy file total " + result.size());
		while (it.hasNext()) 
		{
			String key = (String) it.next();
			tempValue = (JSONObject)result.getJSONObject(key);
			File file = new File(this.basePath + "/" + key);
//			System.out.println("开始复制文件" + file.getName());
			index = key.lastIndexOf(".");
			simpleName = key.substring(0, index);
			extName = key.substring(index, key.length());
			
			if (isUpdate && buildversion != -1)
			{
				targetFileFullName = targetPath + "/" + simpleName + "_v" + tempValue.getInt(EJSON.VERSION) + extName;
			}
			else
			{
				targetFileFullName = targetPath + "/" + simpleName + extName;
			}
			targetFile = new File(targetFileFullName);
			parent = targetFile.getParentFile();
			if (!parent.exists()) 
			{
				parent.mkdirs();
			}
			FileCopyer.copyFile(file, targetFile);
//			System.out.println("复制文件完毕" + file.getName());
		}
	}

	private JSONObject readOldMD5(String fileName) 
	{
		File file = new File(fileName);
		if (!file.exists()) 
		{
//			try 
//			{
//				file.createNewFile();
//			} 
//			catch (IOException e) 
//			{
//				e.printStackTrace();
//			}
			return new JSONObject();
		}
		BufferedReader reader = null;
		JSONObject json;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try 
		{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));

			while ((line = reader.readLine()) != null) 
			{
				sb.append(line);
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
		json = JSONObject.fromObject(sb.toString());
		return json;
	}

	private void calMD5(File file, JSONObject fileMD5s, int buildversion) 
	{
		if (file.isDirectory()) 
		{
			if (file.getName().equalsIgnoreCase(".svn")) 
			{
				return;
			}
			if (file.getName().equalsIgnoreCase("src")) 
			{
				return;
			}
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) 
			{
				calMD5(files[i], fileMD5s, buildversion);
			}
		} 
		else 
		{
			if (file.getName().startsWith("reslist")) 
			{
				return;
			}
			if (file.getName().endsWith(".name")) 
			{
				return;
			}
			if (file.getName().endsWith(".fla")) 
			{
				return;
			}
			String MD5 = MD5Utils.getFileMD5(file);
			String filePath = file.getAbsolutePath();
			String relPath = filePath.substring(this.basePath.length()+1);
			relPath = relPath.replaceAll("\\\\", "/");
			JSONObject json = new JSONObject();
			json.put(EJSON.ID, relPath);
			json.put(EJSON.MD5, MD5);
			json.put(EJSON.SIZE, file.length());
			json.put(EJSON.VERSION, buildversion);
			
			fileMD5s.put(relPath, json);
		}
	}
}
