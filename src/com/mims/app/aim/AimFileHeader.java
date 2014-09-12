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
	
	private String productName = "�˺Ź���";

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
		return "�������: " + productName + AimConst.KEY_TABLE+ " ��������: " + createDate;
	}
	
	

	

}
