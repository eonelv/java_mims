package com.mims.app.aim;

import java.io.Serializable;
import java.util.Date;

public class AimFileHeader implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date createDate = new Date();
	
	private String productName = "账号管理";

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	@Override
	public String toString()
	{
		return "软件名称: " + productName + AimConst.KEY_TABLE+ " 创建日期: " + createDate;
	}
	
	

	

}
