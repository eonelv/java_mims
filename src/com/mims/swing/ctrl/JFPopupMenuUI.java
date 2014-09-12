/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFPopupMenuPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-13
 * 
 */
public class JFPopupMenuUI extends BasicPopupMenuUI
{
	
	private IComponentPainter painter;
	
	public static ComponentUI createUI(JComponent x)
	{
		return new JFPopupMenuUI();
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		c.setOpaque(false);
		IPainterVisitor b = (IPainterVisitor)c;
		if (b.getPainter() == null){
			painter = new JFPopupMenuPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}
	
	public void paint(Graphics g, JComponent c)
	{
		
		painter.paintBackGroud(g, c);
//		int w = c.getWidth();
//		int h = c.getHeight();
//
//		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setRenderingHints(rh);
//		Color temp = SwingConst.BTN_MOUSEOVER_COLOR_TOP;
//		temp = Color.WHITE;
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
//		GradientPaint gp = new GradientPaint(w /2, 2,temp , w/2
//                                             , h
//                                             , SwingConst.TOOLBAR_BACKGROUND);
//		g2.setPaint(gp);
//		g2.fillRect(0, 0, w, h);
	}
}
