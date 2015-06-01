package com.mims.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNodeInfo implements Serializable, Comparable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ID = null;
	
	private String parentID = null;
	
	private String name = null;
	
	private String uiName = null;
	
	private Integer level = null;
	
	private String className = null;
	
	private int model = 0;
	
	private String uiParam = null;
	
	private List<TreeNodeInfo> children = new ArrayList<TreeNodeInfo>();

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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public List<TreeNodeInfo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeInfo> children) {
		this.children = children;
	}

	public int compareTo(Object arg0) 
	{
		TreeNodeInfo info = (TreeNodeInfo)arg0;
		return info.getID().compareToIgnoreCase(ID);
	}
	
	
}
