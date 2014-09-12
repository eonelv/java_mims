/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JComponent;

import com.mims.swing.ctrl.SwingConst;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-28
 * 
 */
public class JFButtonPainter4Win7 extends AbstractComponentPainter
{
	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 * 
	 * @Date：2011-8-28
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, SwingConst.BUTTON_COLOR_TOP, SwingConst.BUTTON_COLOR_CENTER,
				SwingConst.BUTTON_COLOR_BOTTOM, comp.hasFocus());
	}

	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 * 
	 * @Date：2011-8-28
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintOverBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintOverBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, SwingConst.BUTTON_COLOR_TOP_MOUSEOVER,SwingConst.BUTTON_COLOR_CENTER_MOUSEOVE,
				SwingConst.BUTTON_COLOR_BOTTOM_MOUSEOVE, false);
	}

	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 * 
	 * @Date：2011-8-28
	 * 
	 * @param g
	 * @param comp
	 * 
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintPressBackGroud(java.awt.Graphics,
	 *      javax.swing.JComponent)
	 */
	public void paintPressBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, SwingConst.BUTTON_COLOR_TOP_MOUSEPRESS,SwingConst.BUTTON_COLOR_CENTER_MOUSEPRESS,
				SwingConst.BUTTON_COLOR_BOTTOM_MOUSEPRESS, false);
	}

	private void paint(Graphics g, JComponent comp, Color top, Color center,
			Color bottom, boolean isDrawInnerBorder)
	{
		int w = comp.getWidth();
		int h = comp.getHeight();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;

		super.getDefaults(g2);

		g2.setRenderingHints(rh);
		/**
		 * 设置透明1.0为不透明
		 */
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));

		GradientPaint gp = new GradientPaint(w / 2, 0, top, w / 2, h / 2 + 4,
				center);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w - 1, h / 2);

		gp = new GradientPaint(w / 2, h / 2, center, w / 2, h - 1, bottom);
		g2.setPaint(gp);
		g2.fillRect(0, h / 2, w - 1, h / 2);

		g2.setColor(SwingConst.BUTTON_BORDER_COLOR);
		g2.drawRoundRect(0, 0, w - 1, h - 1, 5, 5);

		if (isDrawInnerBorder)
		{
			Stroke stroke = new BasicStroke(1.5f);// 设置线宽为3.0
			g2.setStroke(stroke);
			g2.setColor(SwingConst.BUTTON_INNER_BORDER_COLOR);
			g2.drawRoundRect(1, 1, w - 3, h - 3, 5, 5);
		}

		super.setDefaults(g2);
	}

	protected void _paint(Graphics g, JComponent comp, float alpha)
	{
		
	}

}
