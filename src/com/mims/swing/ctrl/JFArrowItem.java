/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-15
 * 
 */
public class JFArrowItem extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2665051958299369495L;

	private boolean isMouseEnter = false;

	private JComponent item;

	public JFArrowItem(JComponent btn)
	{
		this.item = btn;
		setOpaque(false);
		addMouseListener(new MouseHandler());
		setLayout(null);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(30, 20);
	}

	public Dimension getMinimumSize()
	{
		return new Dimension(30, 20);
	}

	protected void paintComponent(Graphics g)
	{
		paintBackGround(g);
		JFToolButton toolBtn = (JFToolButton) item;

		String text = toolBtn.getText();
		Icon icon = toolBtn.getIcon();

		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(toolBtn.getFont());
		g2.drawString(text, 35, getHeight() / 2 + 4);

		if (icon != null)
		{
			int x;
			int y;
			x = 15 - icon.getIconWidth() / 2;
			y = getHeight() / 2 - icon.getIconHeight() / 2;
			icon.paintIcon(this, g2, x, y);
		}
	}

	private void paintBackGround(Graphics g)
	{
		int w = getWidth();
		int h = getHeight();

		GradientPaint gp = null;
		Graphics2D g2 = (Graphics2D) g;
		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		RenderingHints renderHints = g2.getRenderingHints();
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (isMouseEnter)
		{
			// Color old = g.getColor();
			// int w = getWidth();
			// int h = getHeight();
			// RenderingHints rh = new
			// RenderingHints(RenderingHints.KEY_ANTIALIASING,
			// RenderingHints.VALUE_ANTIALIAS_ON);
			// Graphics2D g2 = (Graphics2D)g;
			// g2.setRenderingHints(rh);
			// g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
			// GradientPaint gp = new GradientPaint(w/2, 2,
			// SwingConst.POPUP_MENU_ICON_BACKGROUND_1, w/2
			// , h / 2 + 4
			// , Color.RED);
			// g2.setPaint(gp);
			// g2.fillRoundRect(33, 2, w - 34, h - 3, 5, 5);
			//
			// gp = new GradientPaint(0, h / 2,
			// SwingConst.POPUP_MENU_ICON_BACKGROUND_1, 0
			// , h
			// , SwingConst.POPUP_MENU_ICON_BACKGROUND_2);
			// g2.setPaint(gp);
			// g2.fillRoundRect(0, 0, 34, h, 5, 5);
			//
			// g.setColor(old);
			

			g2.setRenderingHints(rh);
			/**
			 * 设置透明1.0为不透明
			 */
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1.0f));

			gp = new GradientPaint(w / 2, h / 2, SwingConst.BUTTON_COLOR_2,
					w / 2, h, SwingConst.BUTTON_COLOR_2);

			gp = new GradientPaint(w / 2, 2, SwingConst.TOOL_BUTTON_COLOR_TOP,
					w / 2, h / 2 - 2, SwingConst.TOOL_BUTTON_COLOR_BOTTOM);
			g2.setPaint(gp);
			g2.fillRect(1, 1, w - 2, h);

			g2.setColor(SwingConst.TOOL_BUTTON_COLOR_BORDER);
			g2.drawRoundRect(1, 0, w - 2, h - 1, 3, 3);

		} else
		{
			gp = new GradientPaint(0, h / 2,
					SwingConst.POPUP_MENU_ICON_BACKGROUND_1, 34, h / 2,
					SwingConst.POPUP_MENU_ICON_BACKGROUND_2);
			g2.setPaint(gp);
			g2.fillRect(0, 0, 31, h);
		}

		if (item instanceof JFToolButton
				&& ((JFToolButton) item).getPopCount() > 0)
		{
			g2.setRenderingHints(rh);
			
			int size = 8;
			Polygon a = new Polygon(new int[]
			{ w - 20 + size / 2, w - 20 + size / 2, w - 20 + size }, new int[]
			{ h / 2 - size / 2 - 1, h / 2 + size / 2 + 1, h / 2 }, 3);
			g2.setColor(Color.BLACK);
			g2.fillPolygon(a);
		}

		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}

	@Override
	public boolean isVisible()
	{
		return item.isVisible();
	}

	public JComponent getItem()
	{
		return item;
	}

	private class MouseHandler extends MouseAdapter
	{

		public void mouseClicked(MouseEvent e)
		{
			isMouseEnter = false;
			SwingUtilities.getWindowAncestor(JFArrowItem.this).hide();
			repaint();
			((JFToolButton) item).doClick();
		}

		public void mouseEntered(MouseEvent e)
		{
			if (item instanceof JSeparator)
			{
				return;
			}

			isMouseEnter = true;
			repaint();
			if (item instanceof JFToolButton
					&& ((JFToolButton) item).getPopCount() > 0)
			{
				JFPopupMenu menu = (JFPopupMenu) ((JFToolButton) item)
						.getAssButton();
				menu.setInvoker(item);

				Point itemP = getLocationOnScreen();
				Dimension screemsize = Toolkit.getDefaultToolkit()
						.getScreenSize();

				if (itemP.x + getWidth() + menu.getPreferredSize().getWidth() <= screemsize.width)
				{
					menu.setLocation(itemP.x + getWidth(), itemP.y);
				} else
				{
					menu.setLocation(itemP.x - menu.getWidth(), itemP.y);
				}

				menu.setVisible(true);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void mouseExited(MouseEvent e)
		{
			isMouseEnter = false;
			repaint();
		}
	}
}
