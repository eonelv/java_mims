/**
 * 
 */
package com.mims.swing.ctrl.event;

/**
 * 
 * ����:.<p>
 *
 * @author ���� 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface IDataChangeSupport
{
	public void fireDataChanged();
	
	 public void addDataChangeListener(DataChangeListener listenter);
}
