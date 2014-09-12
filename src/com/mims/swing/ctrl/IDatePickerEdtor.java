/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍş
 * 
 * @Date: 2011-9-12
 * 
 */
public interface IDatePickerEdtor
{
	public JComponent getEditorComponent();

	public void addActionListener(ActionListener l);

	public void removeActionListener(ActionListener l);

	public void setData(Object data);

	public Object getData();

}
