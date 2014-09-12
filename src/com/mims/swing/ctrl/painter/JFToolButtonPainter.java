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
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-8-27
 * 
 */
public class JFToolButtonPainter extends AbstractComponentPainter
{

	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-8-27
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, 0.7f);
	}

	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-8-27
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintOverBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintOverBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, 1.0f);

	}

	/**
	 * @描述：.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-8-27
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintPressBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintPressBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, 0.8f);
	}
	
	protected void _paint(Graphics g, JComponent comp, float alpha){
		int w = comp.getWidth();
		int h = comp.getHeight();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHints(rh);
		/**
		 * 设置透明1.0为不透明
		 */
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		
		GradientPaint gp = new GradientPaint(w / 2, h / 2 ,
				SwingConst.TOOL_BUTTON_COLOR_BOTTOM, w / 2, h - 2, SwingConst.TOOL_BUTTON_COLOR_BOTTOM);
//		g2.setPaint(gp);
//		g2.fillRect(3, h / 2 - 2, w - 4, h / 2 - 2);

		gp = new GradientPaint(w / 2, 2, SwingConst.TOOL_BUTTON_COLOR_TOP, w / 2, h / 2 - 2,
				SwingConst.TOOL_BUTTON_COLOR_BOTTOM);
		g2.setPaint(gp);
		g2.fillRect(2, 3, w - 3, h - 5);

		g2.setColor(SwingConst.TOOL_BUTTON_COLOR_BORDER);
		g2.drawRoundRect(1, 2, w - 2, comp.getHeight() - 4, 3, 3);

	}


}
