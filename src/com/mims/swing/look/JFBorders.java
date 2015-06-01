/**
 * 
 */
package com.mims.swing.look;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;
import javax.swing.text.JTextComponent;

import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.ctrl.util.UIDrawer;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-23
 * 
 */
public class JFBorders
{
	public static class TextFieldBorder extends Flush3DBorder
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4295547864648558562L;

		public void paintBorder(Component c, Graphics g, int x, int y, int w,
				int h)
		{
			//会导致CPU上升非常多
			if (c.hasFocus())
			{
				c.repaint();

				UIDrawer.draw3DBorderFocus(g, x, y, w, h);
			} 
			else
			{
				if (!(c instanceof JTextComponent))
				{
					if (c.isEnabled())
					{
						UIDrawer.draw3DBorderNormal(g, x, y, w, h);
					} else
					{
						UIDrawer.draw3DBorderDisable(g, x, y, w, h);
					}
					return;
				}

				if (c.isEnabled() && ((JTextComponent) c).isEditable())
				{
					UIDrawer.draw3DBorderNormal(g, x, y, w, h);
				} 
				else
				{
					UIDrawer.draw3DBorderDisable(g, x, y, w, h);
				}
			}

		}
	}
	
	public static class ListSelectedBorder extends Flush3DBorder
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4549224809290708233L;

		public void paintBorder(Component c, Graphics g, int x, int y, int w,
				int h)
		{
			if (c.hasFocus())
			{
				c.repaint();
				UIDrawer.draw3DBorderFocus(g, x, y, w, h);
			} else
			{
				if (!(c instanceof JTextComponent))
				{
					if (c.isEnabled())
					{
						g.setColor(SwingConst.MENUBAR_BACKGROUND_4);
						
						g.drawLine(x, y, w + x , y);
						g.drawLine(x, y + h - 1 , w + x , y + h - 1);
//						g.drawLine(w + x , y , w + x , y + h - 1);
					} else
					{
						g.setColor(Color.BLUE);
						g.drawLine(x, y, w + x , y);
						g.drawLine(x, y + h, w + x , y + h);
					}
					return;
				}

				if (c.isEnabled() && ((JTextComponent) c).isEditable())
				{
					UIDrawer.draw3DBorderNormal(g, x, y, w, h);
				} else
				{
					UIDrawer.draw3DBorderDisable(g, x, y, w, h);
				}
			}

		}
	}
	
	public static class JFComboBoxListBorder extends Flush3DBorder
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4549224809290708233L;

		public void paintBorder(Component c, Graphics g, int x, int y, int w,
				int h)
		{
			Graphics2D g2 = (Graphics2D)g;
			Color old = g2.getColor();

			g2.translate(x, y);
			g2.setColor(SwingConst.COMBOBOX_LIST_BORDER_COLOR);
			g2.drawRoundRect(x, y, w - 1, h - 1,3,3);
			g.translate(-x, -y);
			g.setColor(old);

		}
	}
	
	public static class JFParamBorder extends Flush3DBorder
	{

		private int x1;
		private int x2;
		private int y1;
		private int y2;
		private Color color;
		public JFParamBorder(Color c, int x1, int x2, int y1, int y2){
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			color = c;
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 4549224809290708233L;

		public void paintBorder(Component c, Graphics g, int x, int y, int w,
				int h)
		{
			Graphics2D g2 = (Graphics2D)g;
			Color old = g2.getColor();

			g2.translate(x, y);
			g2.setColor(SwingConst.COMBOBOX_LIST_BORDER_COLOR);
			g2.drawRoundRect(x + 1, y + 1, w - 1, h - 1,3,3);
			g2.setColor(color);
			g2.drawLine(x1, y1, x2, y2);
			g.setColor(old);

		}
	}
	
}
