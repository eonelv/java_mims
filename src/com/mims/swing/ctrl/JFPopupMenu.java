/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JPopupMenu;
import javax.swing.border.Border;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-13
 * 
 */
public class JFPopupMenu extends JPopupMenu implements IPainterVisitor
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5641005057978409443L;
	
	private static final String uiClassID = "JFPopupMenuUI";
	
	private IComponentPainter painter;

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	protected void paintBorder(Graphics g) {    
        if (isBorderPainted()) {
            super.paintBorder(g);
        }
    }
	
	@Override
	public Border getBorder()
	{
		return null;
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
