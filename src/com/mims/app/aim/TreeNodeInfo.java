package com.mims.app.aim;

import java.io.Serializable;

public class TreeNodeInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name = null;
	
	private String uiName = null;
	
	private Integer level = null;
	
	private String className = null;
	
	private int model = 0;
	
	private String uiParam = null;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}
	
	
	public int getModel()
	{
		return model;
	}

	public void setModel(int model)
	{
		this.model = model;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public String getUiParam()
	{
		return uiParam;
	}

	public void setUiParam(String uiParam)
	{
		this.uiParam = uiParam;
	}

	public String getUiName()
	{
		return uiName;
	}

	public void setUiName(String uiName)
	{
		this.uiName = uiName;
	}
}
