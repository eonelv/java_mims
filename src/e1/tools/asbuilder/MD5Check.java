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
import e1.tools.utils.FileCopyer;
import e1.tools.utils.MD5Utils;

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
		try 
		{
			MD5Check.process(path, targetPath, isUpdateReslist, reslistPath, -1);
		} 
		catch (IOException e) 
		{
			System.out.println("error::" + e.getMessage());
			System.out.println(e);
		}
		System.out.println("ASBuilder::End");
	}

	public void process(String path, String targetPath, boolean isUpdate, String reslistPath, int buildversion) throws IOException 
	{
		this.basePath = path;
		File parent = new File(this.basePath);
		JSONObject newFileMD5s = new JSONObject();
		JSONObject oldFileMD5s = null;
		
		System.out.println("SysBuildMsg=4. begin calc MD5");
		calMD5(parent, newFileMD5s, buildversion);

		System.out.println("SysBuildMsg=4. read old MD5");
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
		
		//如果是内网版本，需要将MD5对比的结果跟原来保存的更新列表合并（每次发布外网，内网的更新列表会清空）
		if (!isUpdate) 
		{
			JSONObject innerJson = readOldMD5(reslistPath + "/reslist_srv_inner.json");
			
			merge(innerJson, result);
			result = innerJson;
			String innerMD5 = result.toString();
			reWriteSrvResList(innerMD5, reslistPath + "/reslist_srv_inner.json");
		}
		
		merge(oldFileMD5s, result);
		moveFiles(result, targetPath, isUpdate, buildversion);
		
//		result = changeName2Amaf(oldFileMD5s);
		
		System.out.println("SysBuildMsg=5. update version file");
		/*
		 * 如果是发布版本，需要生成reslist到编译环境及发布包中
		 */
		String reslistMD5 = oldFileMD5s.toString();
		if (isUpdate) 
		{
			reWriteSrvResList(reslistMD5, reslistPath + "/reslist_srv.json");
			reWriteSrvResList("{}", reslistPath + "/reslist_srv_inner.json");
			reWriteSrvResListToClient(reslistMD5, targetPath, buildversion);
		}
//		else
//		{
//			reWriteSrvResList(reslistMD5, reslistPath + "/reslist_srv_inner.json");
//		}
		reWriteClientResList(oldFileMD5s, targetPath, isUpdate, buildversion);
		writeUpdateResList(result, targetPath, isUpdate, buildversion);
		System.out.println("SysBuildMsg=final. generatting main application");
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
	
	private void calcFileCount(JSONObject newFileMD5s, String msg)
	{
		Iterator<String> it = newFileMD5s.keySet().iterator();
		int count = 0;
		String key;
		while (it.hasNext()) 
		{
			key = (String) it.next();
			count++;
		}
		System.out.println(msg + "   " + count);
	}
	
	/*
	 * 由于此时生成的版本文件，还没有eamf文件，需要提前将版本文件中的键值改为eamf. 
	 * @param newFileMD5s
	 * @return
	 */
//	private JSONObject changeName2Amaf(JSONObject newFileMD5s)
//	{
//		@SuppressWarnings("unchecked")
//		Iterator<String> it = newFileMD5s.keySet().iterator();
//		JSONObject tempValue;
//		String id;
//		String key;
//		JSONObject tempJsons= new JSONObject();
//		while (it.hasNext()) 
//		{
//			key = (String) it.next();
//			tempValue = (JSONObject)newFileMD5s.getJSONObject(key);
//			id = tempValue.getString(EJSON.ID);
////			if (id.endsWith("json"))
////			{
////				id = id.replace("json", "eamf");
////			}
//			tempValue.put(EJSON.ID, id);
//			tempJsons.put(id, tempValue);
//		}
//		return tempJsons;
//	}
	
	private void writeUpdateResList(JSONObject result, String resourcelistPath, boolean isUpdate, int buildversion) throws IOException
	{
		String fileNameXml = resourcelistPath + "/";
		if (isUpdate)
		{
			fileNameXml += ("updatereslist_v" + buildversion + ".xml");
		}
		else
		{
			fileNameXml += "updatereslist.xml";
		}
		File fileXml = new File(fileNameXml);
		if (!fileXml.getParentFile().exists())
		{
			fileXml.getParentFile().mkdir();
		}
		if (!fileXml.exists())
		{
			fileXml.createNewFile();
		}
		BufferedWriter writerXml = null;
		StringBuilder reslstXml = new StringBuilder();
		String key = null;
		JSONObject item = null;

		Iterator<String> itXml = result.keySet().iterator();

		reslstXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		reslstXml.append("\n");
		reslstXml.append("<resources>\n");
		
		while (itXml.hasNext())
		{
			key = itXml.next();
			item = (JSONObject)result.get(key);
			reslstXml.append("<resource id=\"");
			reslstXml.append(item.getString(EJSON.ID));
			reslstXml.append("\" version=\"");
			reslstXml.append(item.getInt(EJSON.VERSION));
			reslstXml.append("\" size=\"");
			reslstXml.append(item.getInt(EJSON.SIZE));
			reslstXml.append("\" />\n");
		}
		reslstXml.append("</resources>");
		try 
		{
			writerXml = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileXml), "utf-8"));
			writerXml.write(reslstXml.toString());
			writerXml.flush();
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

	private void reWriteSrvResList(String result, String reslistName) 
	{
		File file = new File(reslistName);
		BufferedWriter writer = null;
		
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.write(result);
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
	
	private void reWriteSrvResListToClient(String result, String targetPath, int version) throws IOException 
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

		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "utf-8"));
		writer.write(result);
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
	private void moveFiles(JSONObject result, String targetPath, boolean isUpdate, int buildversion) throws IOException 
	{
		Iterator<String> it = result.keySet().iterator();

		File targetFile;
		String targetFileFullName;
		String simpleName;
		String extName;
		int index;
		JSONObject tempValue;
		File parent;
		String version;
		
		System.out.println("SysBuildMsg=4. begin copy file total " + result.size());
		while (it.hasNext()) 
		{
			String key = (String) it.next();
			tempValue = (JSONObject)result.getJSONObject(key);
			File file = new File(this.basePath + "/" + key);

			if (!file.exists())
			{
				System.out.println("Move file::not exist " + file.getName());
				continue;
			}
			index = key.lastIndexOf(".");
			simpleName = key.substring(0, index);
			extName = key.substring(index, key.length());
			
			if (isUpdate && buildversion != -1)
			{
				version = "_v" + tempValue.getInt(EJSON.VERSION);
				targetFileFullName = targetPath + "/" + simpleName + version + extName;
			}
			else
			{
				version = "";
				targetFileFullName = targetPath + "/" + simpleName + extName;
			}
			targetFile = new File(targetFileFullName);
			parent = targetFile.getParentFile();
			if (!parent.exists()) 
			{
				parent.mkdirs();
			}
			FileCopyer.copyFile(file, targetFile, version, extName, false);
		}
	}

	private JSONObject readOldMD5(String fileName) 
	{
		File file = new File(fileName);
		if (!file.exists()) 
		{
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
