/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.mims.swing.ctrl.JFScrollBar;
import com.mims.swing.ctrl.SwingConst;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-31
 * 
 */
public class JFScrollBarPainter extends AbstractComponentPainter implements IScrollBarPainter
{
	protected static final int DECREASE_HIGHLIGHT = 1;
	protected static final int INCREASE_HIGHLIGHT = 2;

	private void paint(Graphics g, JComponent comp, float alpha, Rectangle rect,
			boolean isOver, boolean isDraged)
	{

		JFScrollBar bar = (JFScrollBar) comp;
		int direction = bar.getOrientation();
		int w = comp.getWidth();
		int h = comp.getHeight();
		int x = 0;
		int y = 0;
		
		if (isOver){
			alpha = 0.6f;
		}
		if (isDraged){
			alpha = 1.0f;
		}

		if (rect != null)
		{
			w = rect.width;
			h = rect.height;
			x = rect.x;
			y = rect.y;
		}
		g.translate(x, y);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		
		super.getDefaults(g2);

		g2.setRenderingHints(rh);
		/**
		 * 设置透明1.0为不透明
		 */
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));

		Color c1 = new Color(233, 247, 253);
		Color c2 = new Color(181, 228, 247);
		Color c3 = new Color(111, 202, 240);
		Color c4 = new Color(102, 186, 221);
		GradientPaint gp1 = new GradientPaint(0, h / 2, c1, w / 2, h / 2,
				c2);
		GradientPaint gp2 = new GradientPaint(w / 2, h / 2, c3, w, h / 2,
				c4);

		if (direction == Adjustable.HORIZONTAL)
		{
			gp1 = new GradientPaint(w / 2, 0, c1, w / 2, h / 2,
					c2);
			gp2 = new GradientPaint(w / 2, h / 2, c3, w / 2, h,
					c4);
		}
		if (direction == Adjustable.HORIZONTAL)
		{
			g2.setPaint(gp1);
			g2.fillRect(0, 0, w, h / 2 + 1);
			g2.setPaint(gp2);
			g2.fillRect(0, h / 2 + 1, w, h / 2);
			
		}else{
			g2.setPaint(gp1);
			g2.fillRect(0, 0, w / 2 + 1, h);
			g2.setPaint(gp2);
			g2.fillRect(w / 2 + 1, 0, w / 2, h);
		}
		g2.setColor(SwingConst.MENUBAR_BACKGROUND_1);
		g2.drawRoundRect(0, 0, w, h, 5, 5);

		g.translate(-x, -y);
		
		super.setDefaults(g2);
	}

	public void paintBackGroud(Graphics g, JComponent comp)
	{

	}

	public void paintOverBackGroud(Graphics g, JComponent comp)
	{

	}

	public void paintPressBackGroud(Graphics g, JComponent comp)
	{

	}

	public void paintThumb(Graphics g, JComponent c, Rectangle rect,boolean isOver,boolean isDraged)
	{
		paint(g, c, 0.4f, rect, isOver, isDraged);
	}

	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds,
			int highLight)
	{
		g.setColor(c.getBackground());
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,
				trackBounds.height);

		if (highLight == DECREASE_HIGHLIGHT)
		{
			paintDecreaseHighlight(g);
		} else if (highLight == INCREASE_HIGHLIGHT)
		{
			paintIncreaseHighlight(g);
		}
	}

	private void paintDecreaseHighlight(Graphics g)
	{

	}

	private void paintIncreaseHighlight(Graphics g)
	{
	}

	protected void _paint(Graphics g, JComponent comp, float alpha)
	{
		
	}
}
