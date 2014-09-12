/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFSeparatorPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-9-16
 * 
 */
public class JFSeparatorUI extends ComponentUI
{
	private IComponentPainter painter = null;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFSeparatorUI();
	}
	
	public void installUI(JComponent c)
	{
		IPainterVisitor b = (IPainterVisitor) c;
		if (b.getPainter() == null)
		{
			painter = new JFSeparatorPainter();
			b.setPainter(painter);
		} else
		{
			painter = (JFSeparatorPainter) b.getPainter();
		}
	}

	public void paint(Graphics g, JComponent c)
	{
		painter.paintBackGroud(g, c);
	}
}
