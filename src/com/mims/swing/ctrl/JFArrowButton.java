/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFArrowButtonPainer;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-10
 * 
 */
public class JFArrowButton extends BasicArrowButton implements IPainterVisitor
{

	private IArrowButtonPop arrowPop = null;

	private IComponentPainter painter = null;

	public JFArrowButton(int direction)
	{
		super(direction);
		arrowPop = new DefaultArrowPop();
		addMouseListener(new MouseHandler());
		setOpaque(false);
		
		painter = new JFArrowButtonPainer();
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

		int x;
		int y;

		if (isOpaque)
		{
//			drawBackGround(g, w, h);
			painter.paintBackGroud(g, this);
			x = 0;
			y = 0;
		} else
		{
			x = 3;
			y = 4;
		}

		if (isPressed)
		{
//			RenderingHints rh = new RenderingHints(
//					RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_ON);
//			Graphics2D g2 = (Graphics2D) g;
//			g2.setRenderingHints(rh);
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//					1.0f));
//			GradientPaint gp = new GradientPaint(w / 2, 2, Color.WHITE, w / 2,
//					h / 2 + 4, SwingConst.BTN_MOUSEOVER_COLOR_TOP);
//			g2.setPaint(gp);
//			g2.fillRoundRect(x, y, w - x, h - 2 * y, 5, 5);
			painter.paintPressBackGroud(g, this);
		} else if (isOver)
		{
//			RenderingHints rh = new RenderingHints(
//					RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_ON);
//			Graphics2D g2 = (Graphics2D) g;
//			g2.setRenderingHints(rh);
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//					1.0f));
//			GradientPaint gp = new GradientPaint(w / 2, 2,
//					SwingConst.BTN_MOUSEOVER_COLOR_TOP, w / 2, h / 2 + 4,
//					SwingConst.BTN_MOUSEOVER_COLOR_BOTTOM);
//			g2.setPaint(gp);
//			g2.fillRoundRect(x + 1, y + 1, w - x - 1, h - 2 * y - 1, 5, 5);
//			g.setColor(Color.WHITE);
//			g.drawRoundRect(x, y, w - x - 1, h - 2 * y - 1, 5, 5);
			painter.paintOverBackGroud(g, this);
		}

		// If there's no room to draw arrow, bail
		if (h < 5 || w < 5)
		{
			g.setColor(origColor);
			return;
		}

		if (isPressed)
		{
			g.translate(1, 1);
		}

		// Draw the arrow
		size = Math.min((h - 4) / 3, (w - 4) / 3);
		size = Math.max(size, 2);

		g.setColor(Color.BLACK);
		paintTriangle(g, w, h, 0, direction, isEnabled);

		// Reset the Graphics back to it's original settings
		if (isPressed)
		{
			g.translate(-1, -1);
		}
		g.setColor(origColor);

	}

	private void drawBackGround(Graphics g, int w, int h)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));

		GradientPaint gp = new GradientPaint(w / 2, h / 2,
				SwingConst.TOOLBAR_BACKGROUND_SIDE, w / 2, h - 2, Color.WHITE);
		g2.setPaint(gp);
		g2.fillRect(0, h / 2, w, h / 2);

		gp = new GradientPaint(w / 2, 2, Color.WHITE, w / 2, h / 2,
				SwingConst.TOOLBAR_BACKGROUND_SIDE);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h / 2);

		g2.setColor(Color.LIGHT_GRAY);
		g2.drawRoundRect(0, 0, w - 1, h - 1, 5, 5);
	}

	public void paintTriangle(Graphics g, int w, int h, int size,
			int direction, boolean isEnabled)
	{
		Polygon a = null;
		int s = 4;
		switch (direction)
		{
		case SwingConstants.EAST:
			a = new Polygon(new int[]
			{ w / 2 - s + 2, w / 2 - s + 2, w / 2 + s - 1}, new int[]
			{ h / 2 - s, h / 2 + s, h / 2}, 3);
			break;
		case SwingConstants.WEST:
			a = new Polygon(new int[]
			{ w / 2 + s, w / 2 + s, w / 2 - s}, new int[]
			{ h / 2 - s, h / 2 + s, h / 2}, 3);
			break;
		case SwingConstants.SOUTH:
			a = new Polygon(new int[]
			{ w / 2 - s, w / 2 + s, w / 2}, new int[]
			{ h / 2 - s + 2, h / 2 - s + 2, h / 2 + s - 1}, 3);
			break;
		case SwingConstants.NORTH:
			a = new Polygon(new int[]
			{ w / 2 - s, w / 2 + s, w / 2}, new int[]
			{ h / 2 + s - 1 , h / 2 + s - 1, h / 2 - s + 1}, 3);
			break;
		default:
			a = new Polygon(new int[]
			{ w / 2 - s, w / 2 - s, w / 2 + s}, new int[]
			{ h / 2 - s, h / 2 + s, h / 2}, 3);
		}
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		g2.fillPolygon(a);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3802557225196336733L;

	public Dimension getMaximumSize()
	{
		return new Dimension(20, 20);
	}

	public IArrowButtonPop getIArrowPop()
	{
		return arrowPop;
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(15, 15);
	}

	public void add(JFToolButton btn)
	{
		arrowPop.add(btn);
	}

	public void add(JFToolButton btn, int pos)
	{
		arrowPop.add(btn, pos);
	}

	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			JFArrowButton arrow = (JFArrowButton) e.getSource();

			if (arrow.getParent() instanceof JFToolBar)
			{
				arrowPop.getArrowPop().show(arrow, e.getXOnScreen(),
						e.getYOnScreen());
			}

		}
	}

	private class DefaultArrowPop implements IArrowButtonPop
	{

		private JDialog dialog;

		private JPanel panel;

		private int size = 0;

		public int getSize()
		{
			if (panel == null)
			{
				return 0;
			}
			return panel.getComponentCount();
		}

		public IArrowButtonPop getArrowPop()
		{
			return this;
		}

		public void show(JComponent comp, int x, int y)
		{
			int screenSize = (int) Toolkit.getDefaultToolkit().getScreenSize()
					.getWidth();
			int width = getWinWidth();
			int height = getWinHeight();
			getDialog().setSize(200, height);
			if (x + 200 > screenSize)
			{
				getDialog().setLocation(screenSize - 200 - 10, y);
			} else
			{
				getDialog().setLocation(x, y);
			}
			getDialog().setVisible(true);
		}

		public void add(JFToolButton btn)
		{
			add(btn, panel.getComponentCount());
		}

		public void add(JFToolButton btn, int pos)
		{
			if (panel == null)
			{
				panel = new JPanel()
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = -365550636587674104L;

					protected void paintComponent(Graphics g)
					{
						int w = getWidth();
						int h = getHeight();
						
						Graphics2D g2 = (Graphics2D) g;
						Color old = g2.getColor();
						Composite composite = g2.getComposite();
						Paint painter = g2.getPaint();
						RenderingHints renderHints = g2.getRenderingHints();
						
						RenderingHints rh = new RenderingHints(
								RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
						
						g2.setRenderingHints(rh);
						Color temp = SwingConst.POPUP_MENU_BACKGROUND;
						g2.setComposite(AlphaComposite.getInstance(
								AlphaComposite.SRC_OVER, 1.0f));
						GradientPaint gp = new GradientPaint(w / 2, 2, temp,
								w / 2, h, SwingConst.POPUP_MENU_BACKGROUND);
						g2.setPaint(gp);
						g2.fillRect(0, 0, w, h);
						g2.setRenderingHints(renderHints);
						g2.setColor(old);
						g2.setPaint(painter);
						g2.setComposite(composite);
					};
				};
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			}
			panel.add(new JFArrowItem(btn), pos);
			size++;
		}

		public JDialog getDialog()
		{
			if (dialog == null)
			{
				dialog = new JDialog();
				dialog.setUndecorated(true);
				dialog.addWindowListener(new WindowAdapter()
				{
					public void windowDeactivated(WindowEvent e)
					{
						dialog.hide();
						dialog.dispose();
						dialog = null;
					}
				});
				dialog.add(panel);
			}
			return dialog;
		}

		public int getWinHeight()
		{
			int height = 0;
			Component[] comps = getComponents();
			JFToolButton comp = (JFToolButton) comps[0];
			height = comp.getHeight() * comps.length - 5;
			return height;
		}

		public int getWinWidth()
		{
			int width = 0;
			Component[] comps = getComponents();
			for (int i = 0; i < comps.length; i++)
			{
				JFToolButton comp = (JFToolButton) comps[i];
				int temp = comp.getWidth();
				if (temp > width)
				{
					width = temp;
				}
			}
			return width;
		}

		public Component[] getComponents()
		{
			if (panel == null)
			{
				return new Component[0];
			}
			Component[] temps = panel.getComponents();
			Component[] relusts = new Component[temps.length];
			for (int i = 0; i < temps.length; i++)
			{
				relusts[i] = ((JFArrowItem) temps[i]).getItem();
			}
			return relusts;
		}

		public void remove(Component comp)
		{
			Component[] temps = panel.getComponents();

			for (int i = 0; i < temps.length; i++)
			{
				if (((JFArrowItem) temps[i]).getItem() == comp)
				{
					panel.remove(temps[i]);
					break;
				}
			}

		}

		public void removeAll()
		{
			if (panel != null)
			{
				panel.removeAll();
			}
		}

	}

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}
}
