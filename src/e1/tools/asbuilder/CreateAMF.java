package e1.tools.asbuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import e1.tools.lzma.sevenzip.compression.lzma.Encoder;
import e1.tools.utils.FileCopyer;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;

public class CreateAMF 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println(args[0]);
		CreateAMF amf = new CreateAMF();
		try {
			amf.process(args[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("AMF Process finished!");
	}
	
	private SerializationContext context =  new  SerializationContext(); 
	private List<File> files = new ArrayList<File>();
	private Encrypt encrypt;
	private DataOutputStream dataPack;
	
	public void process(String srcFolderName) throws Exception
	{
		initEncrypt();
		
		File srcFolder = new File(srcFolderName);
		collFiles(srcFolder);
		processFiles(files, srcFolderName);
	}
	
	private void initEncrypt()
	{
		encrypt = new Encrypt();
		encrypt.InitEncrypt(93,  32,  179, 50,  42,  116, 197, 86);
	}
	
	public void create(File srcFile, String fileName) throws Exception
	{
		initEncrypt();
		
		Object srcJson = readFile(srcFile);
		createAMFFile(srcJson, encrypt, new File(fileName));
	}
	
	private void collFiles(File file)
	{
		if (file.isDirectory())
		{
			File[] tempfiles = file.listFiles();
			int fileLength = tempfiles.length;
			for (int i = 0; i < fileLength; i++)
			{
				collFiles(tempfiles[i]);
			}
		}
		else if (file.getName().endsWith(".json"))
		{
			files.add(file);
		}
	}
	
	private void processFiles(List<File> files, String outputPath) throws Exception
	{
		Iterator<File> itFiles = files.iterator();
		File srcFile;
		String newFileFullName;
		String oldFileName;
		
		Object srcJson = null;
		
		File packFile = new File(outputPath + "/respack.amp");
		dataPack = new DataOutputStream(new FileOutputStream(packFile));
		System.out.println("SysBuildMsg=3. begin create amf file. total " + files.size());
		while (itFiles.hasNext())
		{
			srcFile = itFiles.next();
			srcJson = readFile(srcFile);
			
			oldFileName = srcFile.getName();
			newFileFullName = srcFile.getParent() + "/" + oldFileName.substring(0, oldFileName.lastIndexOf(".")) + ".eamf";
			File file = new File(newFileFullName);
		
			if (!file.exists())
			{
				boolean createSuccess;
				
				createSuccess = file.createNewFile();
				
				if (!createSuccess)
				{
					System.out.println("Create file faield");
					return;
				}
			}
			byte[] input = createAMFFile(srcJson, encrypt, file);
			
			byte[] nameBytes = file.getName().getBytes("utf-8");
			dataPack.writeInt(nameBytes.length);
			dataPack.write(nameBytes);
			dataPack.writeInt(input.length);
			dataPack.write(input);
		}
		dataPack.flush();
		dataPack.close();
	}
	
	public byte[] createAMFFile(Object srcJson, Encrypt encrypt, File file) throws IOException
	{
		Amf3Output out = null;
		out = new Amf3Output(context);
		
		ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
		   
	    DataOutputStream dataOutStream = new DataOutputStream(byteOutPut);
	   
	    out.setOutputStream(dataOutStream);   
	    out.writeObject(srcJson);   
	   
	    dataOutStream.flush();
	   
	    byte[] input = byteOutPut.toByteArray();
	    input = doCompressLzma(input);
		encrypt.DoEncrypt(input, 0, input.length, false);
		FileCopyer.writeFile(file, input);
		return input;
	}
	
	/**
	 * GZIP方式压缩.
	 * 
	 * @param input 原始的字节数组.
	 * @return
	 * @throws IOException
	 */
//	private byte[] doCompressGzip(byte[] input) throws IOException
//	{
//		Deflater compresser = new Deflater(8);
//		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
//		compresser.setInput(input);
//		compresser.finish();
//		int compressedDataLength = 0;
//		
//		byte[] buf = new byte[1024];   
//		while(!compresser.finished())
//	    {       
//			compressedDataLength = compresser.deflate(buf);
//			o.write(buf,0,compressedDataLength);
//	    }
//		   
//		return o.toByteArray();
//	}
	
	private byte[] doCompressLzma(byte[] input) throws IOException
	{
		Encoder encoder = new Encoder();
		encoder.SetNumFastBytes(8);
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(input.length);
		boolean eos = false;
		encoder.SetEndMarkerMode(eos);
		encoder.WriteCoderProperties(outStream);
		long fileSize;
		if (eos)
			fileSize = -1;
		else
			fileSize = input.length;
		for (int i = 0; i < 8; i++)
		{
			outStream.write((int)(fileSize >>> (8 * i)) & 0xFF);
		}
		encoder.Code(inputStream, outStream, -1, -1, null);
		outStream.flush();
		outStream.close();
		inputStream.close();
		   
		return outStream.toByteArray();
	}
	
	private Object readFile(File file) throws Exception 
	{
		Object json = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		BufferedReader reader = null; 
		try 
		{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

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
		try 
		{
			JSONObject jsonObject = JSONObject.fromObject(sb.toString());
			Map<Object, Object> resultJson = new HashMap<Object, Object>();
			getJsonObject(jsonObject, resultJson);
			json = resultJson;
		}
		catch (JSONException e)
		{
			try 
			{
				String content = sb.toString();
				int index = content.indexOf("[");
				int lastIndex = content.lastIndexOf("]");
				content = content.substring(index + 1, lastIndex);
				String []arr = content.split(",");
				int nameLength = arr.length;
				String name;
				for (int i = 0; i < nameLength; i++)
				{
					name = arr[i];
					arr[i] = name.substring(1, name.length() - 1);
				}
				json = arr;
			}
			catch (Exception ep)
			{
				throw new Exception(ep.toString() + file.getName());
			}
			
		}
		return json;
	}
	
	private void getJsonObject(JSONObject jsonObject, Map<Object, Object> resultJson)
	{
		Iterator it = jsonObject.keys();
		Object value;
		Object key;
		while (it.hasNext())
		{
			key = it.next();
			value = jsonObject.get(key);
			
			if (value instanceof JSONObject)
			{
				Map<Object, Object> tempResult = new HashMap<Object, Object>();
				getJsonObject((JSONObject)value, tempResult);
				resultJson.put(key, tempResult);
			}
			else if (value instanceof JSONArray)
			{
				JSONArray array = (JSONArray)value;
				getJsonArray(array, key, resultJson);
			}
			else 
			{
				resultJson.put(key, value);
			}
		}
	}
	
	private void getJsonArray(JSONArray array, Object key, Map<Object, Object> resultJson)
	{
		Iterator itArray = array.iterator();
		
		List<Object> tempField = new ArrayList<Object>(); 
		
		Object arrayItem;
		while (itArray.hasNext())
		{
			arrayItem = itArray.next();
			if (arrayItem instanceof JSONObject)
			{
				Map<Object, Object> tempResult = new HashMap<Object, Object>();
				getJsonObject((JSONObject)arrayItem, tempResult);
				tempField.add(tempResult);
			}
			else
			{
				tempField.add(arrayItem);
			}
		}
		resultJson.put(key, tempField.toArray());
	}

}
