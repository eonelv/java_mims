/**
 * 
 */
package com.mims.swing.ctrl.win;

import javax.swing.JComponent;

/**
 * 描述: 实现本接口的控件都可以增加到WinToolBar上.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-9-16
 * 
 */
public interface WinToolBarResource
{
	public JComponent getSource();
	
	public String getText();

}
