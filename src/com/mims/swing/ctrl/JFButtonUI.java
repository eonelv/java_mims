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

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFButtonPainter4Win7;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-14
 * 
 */
public class JFButtonUI extends BasicButtonUI
{
	
	private IComponentPainter painter = null;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFButtonUI();
	}

	public void paint(Graphics g, JComponent c)
	{

		Color old = g.getColor();
		
		JFButton b = (JFButton) c;
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
//				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//		                RenderingHints.VALUE_ANTIALIAS_ON);
//				Graphics2D g2 = (Graphics2D)g;
//				g2.setRenderingHints(rh);
//				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
//				GradientPaint gp = new GradientPaint(c.getWidth()/2, 2, Color.WHITE, c.getWidth()/2
//		                                             , c.getHeight() / 2 + 4
//		                                             , SwingConst.BTN_MOUSEOVER_COLOR_TOP);
//				g2.setPaint(gp);
//				g2.fillRoundRect(1, 1, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
				
				painter.paintPressBackGroud(g, c);
				
			} else
			{

				painter.paintOverBackGroud(g, c);
//				RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//		                RenderingHints.VALUE_ANTIALIAS_ON);
//				Graphics2D g2 = (Graphics2D)g;
//				g2.setRenderingHints(rh);
//				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
//				GradientPaint gp = new GradientPaint(c.getWidth()/2, 2, SwingConst.BTN_MOUSEOVER_COLOR_TOP, c.getWidth()/2
//		                                             , c.getHeight() / 2 + 4
//		                                             , SwingConst.BTN_MOUSEOVER_COLOR_BOTTOM);
//				g2.setPaint(gp);
//				g2.fillRoundRect(1, 1, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
//				
//				g.setColor(Color.WHITE);
//				g.drawRoundRect(1, 1, w - 2, h - 2, 5, 5);
				
			}
		}
		else
		{
			painter.paintBackGroud(g, c);
		}

		
		super.paint(g, c);
		g.setColor(old);
	}
	
	private void drawBackGround(Graphics g, int w, int h)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
		
		GradientPaint gp = new GradientPaint(w / 2, h / 2 + 3, SwingConst.TOOLBAR_BACKGROUND_SIDE,
				w / 2, h - 2, Color.WHITE);
		g2.setPaint(gp);
		g2.fillRect(0, h / 2, w, h);
		

		gp = new GradientPaint(w  / 2, 2, Color.WHITE,
				w / 2, h / 2 - 3, SwingConst.TOOLBAR_BACKGROUND_SIDE);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h / 2);
		
		gp = new GradientPaint(0, h / 2, Color.WHITE,
				5, h / 2, SwingConst.TOOLBAR_BACKGROUND_SIDE);
		g2.setPaint(gp);
		g2.fillRect(0, 6, 4, h - 12);
		
		gp = new GradientPaint(w - 6, h / 2, SwingConst.TOOLBAR_BACKGROUND_SIDE,
				w, h / 2, Color.WHITE);
		g2.setPaint(gp);
		g2.fillRect(w - 4, 6, 4, h - 12);
		
		g2.setColor(SwingConst.TOOLBAR_BACKGROUND_SIDE);
		g2.drawRoundRect(0, 0, w-1, h-1, 5, 5);
	}

	protected void installDefaults(AbstractButton b)
	{
		super.installDefaults(b);
		b.setOpaque(false);
	}
	
	@Override
	public void installUI(JComponent c)
	{
		super.installUI(c);
		JFButton b = (JFButton)c;
		if (b.getPainter() == null){
			painter = new JFButtonPainter4Win7();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}
}
