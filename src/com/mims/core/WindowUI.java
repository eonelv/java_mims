/**
 * 
 */
package com.mims.core;

import java.awt.Dimension;
import java.util.List;

import com.mims.swing.ctrl.JFMenu;
import com.mims.swing.ctrl.JFMenuBar;
import com.mims.swing.dialog.JFDialog;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-7-23
 * 
 */
public class WindowUI extends JFDialog
{
	
	private JFMenuBar bar = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindowUI(IUIHelper ui)
	{
		super(UIUtils.getWindowForComponent(ui.getUIObject()), ui);
		
		setPreferredSize(new Dimension(500, 300));
		setSize(500, 300);
		//新打开的窗口位置根据父对象确定
		setLocationRelativeTo(ui.getUIObject());
		
		bar = new JFMenuBar();
		
		List menus = ui.getMenu();
		for (int i = 0; i < menus.size(); i++){
			bar.add((JFMenu)menus.get(i));
		}
		
		setJMenuBar(bar);
		
		
	}
}
