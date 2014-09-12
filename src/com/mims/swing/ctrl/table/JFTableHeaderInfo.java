package com.mims.swing.ctrl.table;

public class JFTableHeaderInfo
{
	private String number;
	
	private String name;
	
	private int align = 0;
	
	private int width = 80;

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAlign()
	{
		return align;
	}

	public void setAlign(int align)
	{
		this.align = align;
	}
	
	
	
	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
