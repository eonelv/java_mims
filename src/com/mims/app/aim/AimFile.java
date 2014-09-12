package com.mims.app.aim;

import java.io.Serializable;

public class AimFile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AimFileHeader header;
	
	private AimFileBody body;

	public AimFileHeader getHeader()
	{
		return header;
	}

	public void setHeader(AimFileHeader header)
	{
		this.header = header;
	}

	public AimFileBody getBody()
	{
		return body;
	}

	public void setBody(AimFileBody body)
	{
		this.body = body;
	}
	
	public static AimFile getFile()
	{
		AimFile file = new AimFile();
		AimFileHeader header = new AimFileHeader();
		AimFileBody body = new AimFileBody();
		file.setHeader(header);
		file.setBody(body);
		return file;
	}
	
	@Override
	public String toString()
	{
		return "Header: "  + "\r\n" + header + "\r\n" + 
				"Body: "  + "\r\n" + body;
	}
	
}
