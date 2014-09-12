/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JMenuBar;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-9
 * 
 */
public class JFMenuBar extends JMenuBar implements IPainterVisitor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8353701602464360488L;
	
	private IComponentPainter painter;
	
	private final String uiClassID = "JFMenuBarUI";

//	static
//	{
//		UIManager.getDefaults().put("JFMenuBarUI",
//				"com.mims.swing.ctrl.JFMenuBarUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
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
