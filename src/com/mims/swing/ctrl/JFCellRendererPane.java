/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-23
 * 
 */
public class JFCellRendererPane extends CellRendererPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 121355897455663743L;

	public void paintComponent(Graphics g, Component c, Container p, int x,
			int y, int w, int h, boolean shouldValidate)
	{
		if (c == null)
		{
			if (p != null)
			{
				Color oldColor = g.getColor();
				g.setColor(Color.RED);
				g.fillRect(x, y, w, h);
				g.setColor(oldColor);
			}
			return;
		}
		if (c.getParent() != this)
		{
			this.add(c);
		}

		c.setBounds(x + 1, y + 1, w, h);

		if (shouldValidate)
		{
			c.validate();
		}

		boolean wasDoubleBuffered = false;
		if ((c instanceof JComponent) && ((JComponent) c).isDoubleBuffered())
		{
			wasDoubleBuffered = true;
			((JComponent) c).setDoubleBuffered(false);
		}

		Graphics cg = g.create(x, y, w, h);
		try
		{
			cg.setColor(Color.RED);
			c.paint(cg);
		} finally
		{
			cg.dispose();
		}

		if (wasDoubleBuffered && (c instanceof JComponent))
		{
			((JComponent) c).setDoubleBuffered(true);
		}

		c.setBounds(-w, -h, 0, 0);
	}

	/**
	 * Calls this.paintComponent(g, c, p, x, y, w, h, false).
	 */
	public void paintComponent(Graphics g, Component c, Container p, int x,
			int y, int w, int h)
	{
		paintComponent(g, c, p, x, y, w, h, false);
	}

	/**
	 * Calls this.paintComponent() with the rectangles x,y,width,height fields.
	 */
	public void paintComponent(Graphics g, Component c, Container p, Rectangle r)
	{
		paintComponent(g, c, p, r.x, r.y, r.width, r.height);
	}
}
