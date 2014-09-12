package com.mims.app.aim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AimFileBody implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int accountSize = 0;

	private List accountInfos = new ArrayList();

	public List getAccountInfos()
	{
		return accountInfos;
	}

	public void setAccountInfos(List accountInfos)
	{
		this.accountInfos = accountInfos;
	}
	
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (Object info : accountInfos)
		{
			AccountInfo account = (AccountInfo)info;
			sb.append(account.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
//	private void writeObject(ObjectOutputStream stream) throws IOException 
//	{ 
//		stream.defaultWriteObject();
//		accountSize = accountInfos.size();
//		stream.writeInt(accountSize);
//		for(int i=0; i<accountSize; i++)
//		{
//			stream.writeObject(accountInfos.get(i));
//		}
//		
//	}
//
//	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
//	{
//		stream.defaultReadObject();
//		accountSize = stream.readInt();
//		for (int i = 0; i < accountSize; i++)
//		{
//			accountInfos.add(stream.readObject());
//		}
//		
//	}
	
}
