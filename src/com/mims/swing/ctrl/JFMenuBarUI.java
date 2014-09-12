/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFMenuBarPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-9
 * 
 */
public class JFMenuBarUI extends BasicMenuBarUI 
{
	private IComponentPainter painter;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFMenuBarUI();
	}

	protected void installDefaults()
	{
		super.installDefaults();
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		c.setOpaque(false);
		IPainterVisitor b = (IPainterVisitor)c;
		if (b.getPainter() == null){
			painter = new JFMenuBarPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}

	public void update(Graphics g, JComponent c)
	{
		super.update(g, c);
		painter.paintBackGroud(g, c);
	}


	
}
