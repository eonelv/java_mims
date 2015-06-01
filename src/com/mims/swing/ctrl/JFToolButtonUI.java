package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import sun.swing.SwingUtilities2;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFToolButtonPainter;

public class JFToolButtonUI extends BasicButtonUI
{
	private IComponentPainter painter = null;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFToolButtonUI();
	}

	public void paint(Graphics g, JComponent c)
	{

		JFToolButton b = (JFToolButton) c;
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
//				UIDrawer.drawBackGround(g, Color.WHITE, SwingConst.BTN_MOUSEOVER_COLOR_TOP, b);
				painter.paintPressBackGroud(g, c);
				if (b.getPopCount() > 0)
				{
					g.setColor(Color.LIGHT_GRAY);
					g.drawLine(w - 14, 5, w - 14, h - 6);
				}
			} else
			{
//				UIDrawer.drawBackGround(g, SwingConst.BTN_MOUSEOVER_COLOR_TOP,
//						SwingConst.BTN_MOUSEOVER_COLOR_BOTTOM, b);
//
//				g.setColor(Color.WHITE);
//				g.drawRoundRect(3, 3, w - 4, h - 8, 5, 5);
//				if (b.getPopCount() > 0)
//				{
//					g.setColor(Color.WHITE);
//					g.drawLine(w - 14, 4, w - 14, h - 6);
//				}
				painter.paintOverBackGroud(g, c);
				if (b.getPopCount() > 0)
				{
					g.setColor(Color.WHITE);
					g.drawLine(w - 14, 5, w - 14, h - 6);
				}
			}
		}

		if (b.getPopCount() > 0)
		{
			Polygon a = new Polygon(new int[]
			{ w - 10, w - 6, w - 2 }, new int[]
			{ (h / 2 - 2), (h / 2 + 2), (h / 2 - 2) }, 3);
			g.fillPolygon(a);
		}
		Color old = g.getColor();
		super.paint(g, c);
		g.setColor(old);
	}

	protected void installDefaults(AbstractButton b)
	{
		super.installDefaults(b);
		b.addMouseListener(new MouseHandler());
		b.setOpaque(false);
	}
	
	@Override
	public void installUI(JComponent c)
	{
		super.installUI(c);
		JFToolButton b = (JFToolButton)c;
		if (b.getPainter() == null){
			painter = new JFToolButtonPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}

	protected void paintText(Graphics g, JComponent c, Rectangle textRect,
			String text)
	{
		JFToolButton b = (JFToolButton) c;
		ButtonModel model = b.getModel();
		
		boolean isArmed = model.isArmed();
		boolean isRollover = model.isRollover();
		if (isArmed || isRollover)
		{
			g.setColor(Color.BLACK);
		}else{
			g.setColor(Color.WHITE);
		}
		
		FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
		int mnemonicIndex = b.getDisplayedMnemonicIndex();

		int temp = textRect.x + getTextShiftOffset();
		if (b.getPopCount() > 0)
		{
			temp -= 6;
		}
		if (model.isEnabled())
		{
			SwingUtilities2.drawStringUnderlineCharAt(c, g, text,
					mnemonicIndex, temp, textRect.y + fm.getAscent()
							+ getTextShiftOffset());
		} else
		{
			g.setColor(b.getBackground().brighter());
			SwingUtilities2.drawStringUnderlineCharAt(c, g, text,
					mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
			g.setColor(b.getBackground().darker());
			SwingUtilities2.drawStringUnderlineCharAt(c, g, text,
					mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent()
							- 1);
		}
	}
	
	private class MouseHandler implements MouseListener
	{
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		public void mousePressed(MouseEvent e)
		{
			if (!(e.getSource() instanceof JFToolButton)){
				return;
			}
			JFToolButton btnTemp = (JFToolButton)e.getSource();
			if (btnTemp.getPopCount() > 0 && isClickRight(e, btnTemp)){
				btnTemp.getAssButton().show(btnTemp, 2, btnTemp.getHeight() - 4);
			}
		}
		
		private boolean isClickRight(MouseEvent e, JFToolButton btnTemp){
			int x = e.getX();
			int y = e.getY();
			
			int w = btnTemp.getWidth();
			int h = btnTemp.getHeight();
			if ((x > w - 10) && (x < w - 2) && (y > 8) && (y < h - 8)){
				return true;
			}
			return false;
		}
		
		public void mouseExited(MouseEvent e)
		{
			
		}
		
		public void mouseEntered(MouseEvent e)
		{
			
		}
		
		public void mouseClicked(MouseEvent e)
		{
			
		}
	}
}
