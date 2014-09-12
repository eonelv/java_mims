/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-14
 * 
 */
public class JFTabbedPaneButtonUI extends BasicButtonUI
{
	public static ComponentUI createUI(JComponent c)
	{
		return new JFTabbedPaneButtonUI();
	}

	public void paint(Graphics g, JComponent c)
	{

		Color old = g.getColor();
		
		JFTabbedPaneButton b = (JFTabbedPaneButton) c;
		ButtonModel model = b.getModel();

		boolean isArmed = model.isArmed();
		boolean isPress = model.isPressed();
		boolean isRollover = model.isRollover();
		int w = b.getWidth();
		int h = b.getHeight();
		
		
		if (isArmed || isRollover)
		{
			if (isPress)
			{
				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHints(rh);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
				GradientPaint gp = new GradientPaint(c.getWidth()/2, 2, Color.WHITE, c.getWidth()/2
		                                             , c.getHeight() / 2 + 4
		                                             , SwingConst.BTN_MOUSEOVER_COLOR_TOP);
				g2.setPaint(gp);
				g2.fillRoundRect(1, 1, c.getWidth() - 1, c.getHeight() - 6, 5, 5);
				
				
			} else
			{

				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHints(rh);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
				GradientPaint gp = new GradientPaint(c.getWidth()/2, 2, SwingConst.BTN_MOUSEOVER_COLOR_TOP, c.getWidth()/2
		                                             , c.getHeight() / 2 + 4
		                                             , SwingConst.BTN_MOUSEOVER_COLOR_BOTTOM);
				g2.setPaint(gp);
				g2.fillRoundRect(1, 1, c.getWidth() - 1, c.getHeight() - 6, 5, 5);
				
				g.setColor(Color.WHITE);
				g.drawRoundRect(1, 1, w - 2, h - 7, 5, 5);
				
			}
		}
		else
		{
//			drawBackGround(g, w, h);
		}

		
		super.paint(g, c);
		g.setColor(old);
	}
	
//	private void drawBackGround(Graphics g, int w, int h)
//	{
//		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setRenderingHints(rh);
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
//		
//		GradientPaint gp = new GradientPaint(w / 2, h / 2, SwingConst.TOOLBAR_BACKGROUND,
//				w / 2, h - 2, Color.WHITE);
//		g2.setPaint(gp);
//		g2.fillRect(0, h / 2, w, h);
//		
//
//		gp = new GradientPaint(w  / 2, 2, Color.WHITE,
//				w / 2, h / 2, SwingConst.TOOLBAR_BACKGROUND);
//		g2.setPaint(gp);
//		g2.fillRect(0, 0, w, h / 2);
//		
//		g2.setColor(Color.LIGHT_GRAY);
//		g2.drawRoundRect(0, 0, w-1, h-1, 5, 5);
//	}

	protected void installDefaults(AbstractButton b)
	{
		super.installDefaults(b);
		b.setOpaque(false);
	}
}
