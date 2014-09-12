/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.mims.swing.ctrl.JFToolBar;
import com.mims.swing.ctrl.SwingConst;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-8-27
 * 
 */
public class JFArrowButtonPainer extends AbstractComponentPainter
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
		if (comp.getParent() instanceof JFToolBar){
			paint4ToolBar(g, comp, alpha);
		}
		else{
			paintNormal(g, comp, alpha);
		}
	}
	
	/**
	 * 
	 * @描述：按h/2两种颜色渐变.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-8-26
	 *
	 * @param g
	 * @param comp
	 * @param alpha
	 */
	private void paintNormal(Graphics g, JComponent comp, float alpha){
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
				SwingConst.BUTTON_COLOR_2, w / 2, h - 2, Color.WHITE);
		g2.setPaint(gp);
		g2.fillRect(0, h / 2, w - 1, h / 2);

		gp = new GradientPaint(w / 2, 0, Color.WHITE, w / 2, h / 2 - 2,
				SwingConst.BUTTON_COLOR_2);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w - 1, h / 2);
		
		//左右颜色
		gp = new GradientPaint(0, h / 2, Color.WHITE,
				5, h / 2, SwingConst.BUTTON_COLOR_2);
		g2.setPaint(gp);
		g2.fillRect(0, 4, 4, h - 8);
		
		gp = new GradientPaint(w - 6, h / 2, SwingConst.BUTTON_COLOR_2,
				w, h / 2, Color.WHITE);
		g2.setPaint(gp);
		g2.fillRect(w - 5, 4, 4, h - 8);
		
		
		/**
		 * 设置线条宽度
		 */
//		Stroke stroke=new BasicStroke(1.0f);//设置线宽为3.0
//		g2.setStroke(stroke);
		
		g2.setColor(SwingConst.BUTTON_COLOR_1);
		g2.drawRoundRect(0, 0, comp.getWidth() - 1, comp.getHeight() - 1, 3, 3);
		
		
	}
	
	private void paint4ToolBar(Graphics g, JComponent comp, float alpha){
		int w = comp.getWidth();
		int h = comp.getHeight();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D)g;
		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		
		g2.setRenderingHints(rh);
		/**
		 * 设置透明1.0为不透明
		 */
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		
		GradientPaint gp = new GradientPaint(w / 2, h / 2 ,
				SwingConst.BUTTON_COLOR_2, w / 2, h - 2, SwingConst.BUTTON_COLOR_2);
		g2.setPaint(gp);
		g2.fillRect(0, h / 2 - 2, w - 1, h / 2 - 2);

		gp = new GradientPaint(w / 2, 2, Color.WHITE, w / 2, h / 2 - 2,
				SwingConst.BUTTON_COLOR_2);
		g2.setPaint(gp);
		g2.fillRect(0, 4, w - 1, h / 2 - 2);

		g2.setColor(SwingConst.BUTTON_COLOR_1);
		g2.drawRoundRect(0, 3, comp.getWidth() - 1, comp.getHeight() - 7, 3, 3);
		
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}

}
