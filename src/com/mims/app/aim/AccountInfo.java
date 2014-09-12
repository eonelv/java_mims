package com.mims.app.aim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AccountInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;
	
	private String name;
	
	private String password;
	
	private int[] typeBits = new int[]{3,4,5};
	
	private int[] nameBits = new int[]{2,1,1};
	
	private int[] passBits = new int[]{1,2,1};

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "’À∫≈¿‡–Õ: " + type + AimConst.KEY_TABLE + "’À∫≈√˚≥∆: " + name +
			AimConst.KEY_TABLE + "’À∫≈√‹¬Î: " + password;
	}

	private void write(ObjectOutputStream stream, String property, int[] bits, double doubleBit, long longBit) throws IOException
	{
		AimFileOperator.writeString(stream, property, bits, doubleBit, longBit);
	}
	private void writeObject(ObjectOutputStream stream) throws IOException 
	{ 
		stream.defaultWriteObject();
		write(stream, type, typeBits, 1d, 2l);
		write(stream, name, nameBits, 3d, 4l);
		write(stream, password, passBits, 5d, 6l);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		type = read(stream, typeBits);
		name = read(stream, nameBits);
		password = read(stream, passBits);
	}
	
	private String read(ObjectInputStream stream, int[] bits) throws IOException, ClassNotFoundException
	{
		return AimFileOperator.readString(stream, bits);
	}
}
