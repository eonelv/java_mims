/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.IScrollBarButtonPainter;
import com.mims.swing.ctrl.painter.JFScrollBarButtonPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-1
 * 
 */
public class JScrollBarButton extends BasicArrowButton implements IPainterVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545702191177874377L;
	
	private IScrollBarButtonPainter painter = null;
	
	public JScrollBarButton(int direction)
	{
		super(direction);
		painter = new JFScrollBarButtonPainter(direction);
	}
	
	
	public Dimension getMaximumSize()
	{
		return new Dimension(20, 20);
	}
	
	public void paint(Graphics g)
	{
		Color origColor;
		boolean isPressed, isEnabled;
		int w, h, size;

		w = getSize().width;
		h = getSize().height;
		origColor = g.getColor();
		isPressed = getModel().isPressed();
		boolean isOver = getModel().isArmed() || getModel().isRollover();
		isEnabled = isEnabled();
		boolean isOpaque = isOpaque();


		if (isOpaque)
		{
			painter.paintBackGroud(g, this);
		}

		if (isPressed)
		{
			painter.paintPressBackGroud(g, this);
		} 
		else if (isOver)
		{
			painter.paintOverBackGroud(g, this);
		}

		g.setColor(Color.BLACK);
		painter.paintTriangle(g, w, h, 4, direction, isEnabled);

		g.setColor(origColor);

	}

	
	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled)
	{
		Polygon a = null;
		switch (direction)
		{
		case SwingConstants.EAST:
			a = new Polygon(new int[]
			{ 8, 8, x - 4 }, new int[]
			{ y / 2 - 5, y / 2 + 5, y / 2 }, 3);
			break;
		case SwingConstants.WEST:
			break;
		case SwingConstants.SOUTH:
			a = new Polygon(new int[]
			{ 6, x / 2 + 1, x - 6 }, new int[]
			{ y / 2 - 2, y / 2 + 4, y / 2 - 2 }, 3);
			break;
		case SwingConstants.NORTH:
			break;
		default:
			a = new Polygon(new int[]
			{ x - 10, x - 6, x - 2 }, new int[]
			{ (y / 2 - 2), (y / 2 + 2), (y / 2 - 2) }, 3);
		}
		g.fillPolygon(a);

	}

	public Dimension getPreferredSize()
	{
		return new Dimension(15, 15);
	}
	
	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = (IScrollBarButtonPainter)painter;
	}
}
