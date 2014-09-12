/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-9-12
 * 
 */
public class DatePickerEdtor implements IDatePickerEdtor
{

	private JFTextField editor = null;
	
	private Object oldData = null;
	
	public DatePickerEdtor(){
		editor = new JFTextField();
	}
	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-12
	 *
	 * @return
	 *
	 * @see com.mims.swing.ctrl.IDatePickerEdtor#getEditorComponent()
	 */
	public JComponent getEditorComponent()
	{
		return editor;
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-12
	 *
	 * @param l
	 *
	 * @see com.mims.swing.ctrl.IDatePickerEdtor#addActionListener(java.awt.event.ActionListener)
	 */
	public void addActionListener(ActionListener l)
	{
		editor.addActionListener(l);
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-12
	 *
	 * @param l
	 *
	 * @see com.mims.swing.ctrl.IDatePickerEdtor#removeActionListener(java.awt.event.ActionListener)
	 */
	public void removeActionListener(ActionListener l)
	{
		editor.removeActionListener(l);
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-12
	 *
	 *
	 * @see com.mims.swing.ctrl.IDatePickerEdtor#setData()
	 */
	public void setData(Object data)
	{
		editor.setUserObject(data);
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-12
	 *
	 * @return
	 *
	 * @see com.mims.swing.ctrl.IDatePickerEdtor#getData()
	 */
	public Object getData()
	{
		return editor.getUserObject();
	}

}
