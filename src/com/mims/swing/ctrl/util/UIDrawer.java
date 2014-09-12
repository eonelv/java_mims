/**
 * 
 */
package com.mims.swing.ctrl.util;

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
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.mims.swing.ctrl.SwingConst;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-8
 * 
 */
public class UIDrawer
{
	public static void drawBackGround(Graphics g, Color from, Color to,
			JComponent c)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		GradientPaint gp = new GradientPaint(c.getWidth() / 2, 2, from,
				c.getWidth() / 2, c.getHeight() / 2 + 4, to);
		g2.setPaint(gp);
		g2.fillRoundRect(3, 4, c.getWidth() - 3, c.getHeight() - 9, 5, 5);
	}

	public static void draw3DBorderFocus(Graphics g, int x, int y, int w, int h)
	{
		Color old = g.getColor();

		g.translate(x, y);
		
		Graphics2D g2 = (Graphics2D)g;
		Stroke stroke = new BasicStroke(1.5f);// 设置线宽为3.0
		g2.setStroke(stroke);
		
		g2.setColor(SwingConst.EDITOR_FOCUS_BORDER_COLOR);
		g2.drawRect(x + 1, y + 1, w - 2, h - 3);
		
		

		g2.translate(-x, -y);
		g2.setColor(old);
	}
	
	public static void draw3DBorderNormal(Graphics g, int x, int y, int w, int h)
	{
		Color old = g.getColor();

		g.translate(x, y);
		g.setColor(SwingConst.EDITOR_NORMAL_COLOR);
		g.drawRect(0, 0, w - 2, h - 2);
//		g.setColor(SwingConst.EDITOR_NORMAL_COLOR);
//		g.drawRoundRect(1, 1, w - 4, h - 4,3,3);
		g.translate(-x, -y);
		g.setColor(old);
	}
	
	public static void draw3DBorderDisable(Graphics g, int x, int y, int w, int h)
	{
		Color old = g.getColor();

		g.translate(x, y);
		g.setColor(SwingConst.EDITOR_DISABLE_COLOR);
		g.drawRoundRect(0, 0, w - 2, h - 2,3,3);
//		g.setColor(SwingConst.EDITOR_DISABLE_COLOR);
//		g.drawRoundRect(1, 1, w - 4, h - 4,3,3);
		g.translate(-x, -y);
		g.setColor(old);
	}
	
	public static void drawToolBarBackGround(Graphics g, int w, int h, float alpha){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHints(rh);
		/**
		 * 设置透明1.0为不透明
		 */
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		GradientPaint gp = new GradientPaint(0, h / 2, SwingConst.TOOLBAR_BACKGROUND_SIDE,
				w / 2, h, SwingConst.TOOLBAR_BACKGROUND_CENTER);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w / 2, h);
		
		gp = new GradientPaint(w / 2, h / 2,SwingConst.TOOLBAR_BACKGROUND_CENTER,
				w, h / 2, SwingConst.TOOLBAR_BACKGROUND_SIDE);
		g2.setPaint(gp);
		g2.fillRect(w / 2, 0, w / 2, h);
	}
}
