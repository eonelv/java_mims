/**
 * 
 */
package com.mims.swing.ctrl.event;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface IDataChangeSupport
{
	public void fireDataChanged();
	
	 public void addDataChangeListener(DataChangeListener listenter);
}
