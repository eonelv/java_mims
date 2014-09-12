/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

import com.mims.swing.ctrl.SwingConst;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍş
 * 
 * @Date: 2011-8-27
 * 
 */
public class JFPopupMenuPainter extends AbstractComponentPainter
{

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
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
	 * @author ÀîÍş
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
		// TODO Auto-generated method stub

	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
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
		paint(g, comp, 1.0f);
	}

	public void _paint(Graphics g, JComponent c, float alpha)
	{
		boolean hasIcon = false;
		int iconWidth = 0;
		Component[] comps = c.getComponents();
		for (int i = 0; i < comps.length; i++)
		{
			AbstractButton b = (AbstractButton) comps[i];
			if (b.getIcon() != null)
			{
				hasIcon = true;
				iconWidth = 25;
				break;
			}
		}

		int w = c.getWidth();
		int h = c.getHeight();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		
		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		RenderingHints renderHints = g2.getRenderingHints();
		
		
		g2.setRenderingHints(rh);
		Color temp = SwingConst.TOOLBAR_BACKGROUND_SIDE;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		GradientPaint gp = new GradientPaint(0, h / 2, SwingConst.POPUP_MENU_ICON_BACKGROUND_1, iconWidth, h / 2,
				SwingConst.POPUP_MENU_ICON_BACKGROUND_2);
		
		if (hasIcon)
		{
			g2.setPaint(gp);
			g2.fillRect(0, 0, iconWidth, h);
		}
		temp = SwingConst.POPUP_MENU_BACKGROUND;
		gp = new GradientPaint(w / 2, 2, temp, w / 2, h,
				SwingConst.POPUP_MENU_BACKGROUND);
		g2.setPaint(gp);
		g2.fillRect(iconWidth, 0, w, h);
		
		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}

}
