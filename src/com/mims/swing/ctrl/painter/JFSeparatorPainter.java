/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class JFSeparatorPainter extends AbstractComponentPainter
{

	public void paintBackGroud(Graphics g, JComponent comp)
	{
		paint(g,comp,1.0f);
	}

	public void paintOverBackGroud(Graphics g, JComponent comp)
	{
		
	}

	public void paintPressBackGroud(Graphics g, JComponent comp)
	{
		
	}

	protected void _paint(Graphics g, JComponent comp, float alpha)
	{
		Graphics2D g2 = (Graphics2D)g;
		int w = comp.getWidth();
		int h = comp.getHeight();
		
//		g2.setColor(Color.LIGHT_GRAY);
//		g2.drawLine(1, h / 2 - 2 , w - 2, h / 2 - 2);
//		g2.drawLine(1, h / 2 + 1, w - 2, h / 2 + 1);
//		
		g2.setColor(Color.GRAY);
		g2.drawLine(0, h / 2, w, h / 2);
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawLine(1, h / 2 + 1, w - 2, h / 2 + 1);

	}

	
}
