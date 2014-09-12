/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JCheckBoxMenuItem;

import com.mims.swing.ctrl.painter.IComponentPainter;


/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-4
 * 
 */
public class JFCheckBoxMenuItem extends JCheckBoxMenuItem implements IPainterVisitor
{
	
	private IComponentPainter painter;
	
	private static final long serialVersionUID = -7434192989590336223L;
	
	private static final String uiClassID = "JFCheckBoxMenuItemUI";

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	public JFCheckBoxMenuItem(String name){
		super(name);
	}

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}
}