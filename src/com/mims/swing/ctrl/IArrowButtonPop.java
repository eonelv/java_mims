/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-12
 * 
 */
public interface IArrowButtonPop
{
	public int getSize();
	
	public IArrowButtonPop getArrowPop();
	
	public void show(JComponent comp, int x ,int y);
	
	public void add(JFToolButton btn);
	
	public void add(JFToolButton btn, int pos);
	
	public JDialog getDialog();
	
	public Component [] getComponents();
	
	public void removeAll();
	
	public void remove(Component comp);
}
