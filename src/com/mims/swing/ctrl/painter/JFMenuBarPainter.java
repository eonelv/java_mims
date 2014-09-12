/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.mims.swing.ctrl.SwingConst;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-27
 * 
 */
public class JFMenuBarPainter extends AbstractComponentPainter
{

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 * 
	 * @Date£º2011-8-27
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, 1.0f);
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 * 
	 * @Date£º2011-8-27
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintOverBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintOverBackGroud(Graphics g, JComponent comp)
	{

	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 * 
	 * @Date£º2011-8-27
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintPressBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintPressBackGroud(Graphics g, JComponent comp)
	{

	}

	protected void _paint(Graphics g, JComponent c, float alpha)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		GradientPaint gp = new GradientPaint(c.getWidth() / 2,
				c.getHeight() / 2, SwingConst.MENUBAR_BACKGROUND_3,
				c.getWidth() / 2, c.getHeight() - 2,
				SwingConst.MENUBAR_BACKGROUND_4);
		g2.setPaint(gp);
		g2.fillRect(0, c.getHeight() / 2, c.getWidth(), c.getHeight() / 2);

		gp = new GradientPaint(c.getWidth() / 2, 0, SwingConst.MENUBAR_BACKGROUND_1,
				c.getWidth() / 2, c.getHeight() / 2 + 2,
				SwingConst.MENUBAR_BACKGROUND_2);
		g2.setPaint(gp);
		g2.fillRect(0, 0, c.getWidth(), c.getHeight() / 2);
	}
}
