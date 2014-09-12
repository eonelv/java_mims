package e1.tools.asbuilder.utils;

import net.sf.json.JSONObject;

public class EJSON
{
	
	public static String ID = "id";
	public static String VERSION = "version";
	public static String SIZE = "size";
	public static String MD5 = "MD5";
	private JSONObject info;
	
	public EJSON(JSONObject info)
	{
		if (info == null)
		{
			this.info = new JSONObject();
		}
		else
		{
			this.info = info;
		}
	}
	
	static public EJSON fromObject(Object object)
	{
		JSONObject info = JSONObject.fromObject(object);
		return new EJSON(info);
	}
	public String toString()
	{
//		StringBuilder value = new StringBuilder();
//		value.append("{");
//		value.append("\"id\":");
//		value.append("\""+ getID() +"\"");
		String msg = info.toString(0);
		String a = msg.replaceAll("\\\\", "/");
		
		return info.toString(0);
	}
	
	public void setID(String id)
	{
		info.put("id", id);
	}
	
	public String getID()
	{
		return info.getString("id");
	}
	
	public void setVersion(int version)
	{
		info.put("version", version);
	}
	
	public int getVersion()
	{
		return info.getInt("version");
	}
	
	public void setSize(long size)
	{
		info.put("size", size);
	}
	
	public long getSize()
	{
		return info.getInt("size");
	}
	
	public void setMD5(String md5)
	{
		info.put("MD5", md5);
	}
	
	public String getMD5()
	{
		return info.getString("MD5");
	}
}
