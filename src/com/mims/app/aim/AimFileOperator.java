/**
 * 
 */
package com.mims.app.aim;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-7-19
 * 
 */
public class AimFileOperator
{

	public static AimFile readAimFile(String path, String fileName) throws IOException, ClassNotFoundException
	{
		File file = new File(path + "/" + fileName);
		return readAimFile(file);
	}
	
	public static AimFile readAimFile(File file) throws IOException, ClassNotFoundException
	{

		FileInputStream istream = new FileInputStream(file); // 输入流
		ObjectInputStream pr = new ObjectInputStream(istream); // 绑定
		AimFile aimFile  = (AimFile)pr.readObject(); // 读入序列化的类
		istream.close();
		return aimFile;
	}
	
	public static void writeFile(File file, AimFile aimFile) throws IOException
	{
		FileOutputStream fs = new FileOutputStream(file);
		ObjectOutputStream os =  new ObjectOutputStream(fs);
		
//		AimFileHeader header = new AimFileHeader();
//		header.setCreateDate(new Date());
//		
//		AimFileBody body = new AimFileBody();
//		AccountInfo account = new AccountInfo();
//		account.setName("mandy");
//		account.setType("www.sina.com");
//		account.setPassword("023023");
//		body.getAccountInfos().add(account);
//		
//		account = new AccountInfo();
//		account.setName("李威");
//		account.setType("www.qq.com");
//		account.setPassword("023023");
//		body.getAccountInfos().add(account);
//		
//		AimFile aimFile = new AimFile();
//		aimFile.setHeader(header);
//		aimFile.setBody(body);
		
		os.writeObject(aimFile);
		os.close();
	}
	
	public static void writeFile(String path, AimFile aimFile) throws IOException
	{
		File file = new File(path);
		writeFile(file,aimFile);
	}
	
	public static void writeFile(String path, String fileName,AimFile aimFile) throws IOException
	{
		File file = new File(path + "/" + fileName);
		writeFile(file,aimFile);
	}
	
	public static void writeString(ObjectOutputStream stream, String property, int[] bits, double doubleBit, long longBit) throws IOException
	{
		byte [] typeBytes = property.getBytes(); 
		int totalSize = typeBytes.length;
		int pos = 0;
		byte [] tempByte = null;
		
		stream.writeInt(totalSize);
		for (int i=0;i<bits.length;i++) 
		{ 
			if (totalSize - pos >= bits[i]) 
			{ 
				tempByte = new byte[bits[i]];
				System.arraycopy(typeBytes, pos, tempByte, 0, tempByte.length); 
				pos += bits[i];
				stream.write(tempByte);
			}
			else
			{
				tempByte = new byte[totalSize - pos];
				System.arraycopy(typeBytes, pos, tempByte, 0, tempByte.length);
				pos = totalSize;
				stream.write(tempByte);
				break;
			}
			if (i%2==0)
			{
				stream.writeDouble(doubleBit);
			}
			else
			{
				stream.writeLong(longBit);
			}
		}
		if (pos < totalSize)
		{
			tempByte = new byte[totalSize - pos];
			System.arraycopy(typeBytes, pos, tempByte, 0, tempByte.length);
			stream.write(tempByte);
		}
	}
	
	public static String readString(ObjectInputStream stream, int[] bits) throws IOException, ClassNotFoundException
	{
		String value = null;
		int totalSize = stream.readInt();
		byte [] typeBytes = new byte[totalSize];
		int pos = 0;
		byte [] tempByte = null;
		for (int i=0;i<bits.length;i++) 
		{ 
			if (totalSize - pos >= bits[i]) 
			{ 
				tempByte = new byte[bits[i]];
				stream.read(tempByte);
				System.arraycopy(tempByte, 0, typeBytes, pos, tempByte.length); 
				pos += bits[i];
			}
			else
			{
				if (pos < totalSize)
				{
					tempByte = new byte[totalSize - pos];
					stream.read(tempByte);
					System.arraycopy(tempByte, 0, typeBytes, pos, tempByte.length);
				}
				value = new String(typeBytes);
				return value;
			}
			if (i%2==0)
			{
				stream.readDouble();
			}
			else
			{
				stream.readLong();
			}
		}
		if (pos < totalSize)
		{
			tempByte = new byte[totalSize - pos];
			stream.read(tempByte);
			System.arraycopy(tempByte, 0, typeBytes, pos, tempByte.length);
		}
		value = new String(typeBytes);
		return value;
	}
}
