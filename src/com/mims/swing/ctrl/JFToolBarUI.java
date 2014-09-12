/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFToolBarPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-9
 * 
 */
public class JFToolBarUI extends BasicToolBarUI
{
	private IComponentPainter painter;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFToolBarUI();
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		c.setOpaque(false);
		IPainterVisitor b = (IPainterVisitor)c;
		if (b.getPainter() == null){
			painter = new JFToolBarPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}
	
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		painter.paintBackGroud(g, c);
	}
}
