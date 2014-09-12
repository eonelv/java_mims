/**
 * 
 */
package com.mims.swing.ctrl.event;

import java.awt.AWTEvent;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class DataChangeEvent extends AWTEvent
{

	private Object oldValue;
	
	private Object newValue;
	
	public DataChangeEvent(Object source, int id, Object oldData, Object newData){
		this(source, id);
		oldValue = oldData;
		newValue = newData;
	}
	
	public DataChangeEvent(Object source, int id)
	{
		super(source, id);
	}

	public Object getOldValue()
	{
		return oldValue;
	}



	public void setOldValue(Object oldValue)
	{
		this.oldValue = oldValue;
	}



	public Object getNewValue()
	{
		return newValue;
	}



	public void setNewValue(Object newValue)
	{
		this.newValue = newValue;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -1655441806774127015L;

}
