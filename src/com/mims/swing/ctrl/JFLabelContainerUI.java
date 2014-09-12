/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager2;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-22
 * 
 */
public class JFLabelContainerUI extends BasicPanelUI
{
	private static PanelUI panelUI;

	public static ComponentUI createUI(JComponent c)
	{
		if (panelUI == null)
		{
			panelUI = new JFLabelContainerUI();
		}
		return panelUI;
	}

	public void installUI(JComponent c)
	{
		JFLabelContainer p = (JFLabelContainer) c;
		super.installUI(p);
		installDefaults(p);
		c.setLayout(new DefaultLabelContainerLayout());
	}

	public void uninstallUI(JComponent c)
	{
		JPanel p = (JPanel) c;
		uninstallDefaults(p);
		super.uninstallUI(c);
	}

	protected void installDefaults(JPanel p)
	{
		LookAndFeel.installColorsAndFont(p, "Panel.background",
				"Panel.foreground", "Panel.font");
		LookAndFeel.installBorder(p, "Panel.border");
		LookAndFeel.installProperty(p, "opaque", Boolean.FALSE);
	}

	protected void uninstallDefaults(JPanel p)
	{
		LookAndFeel.uninstallBorder(p);
	}
	
	@Override
	public void paint(Graphics g, JComponent c)
	{
		Color oldColor = g.getColor();
		JFLabelContainer lable = (JFLabelContainer)c;
		Graphics2D g2 = (Graphics2D)g;		
	
		g2.setColor(oldColor);
		g2.drawString(lable.getText(), 2, lable.getHeight() - 4);
		
		
		if(lable.isLineVisiable()){
			g2.drawLine(0,lable.getHeight() - 1 , lable.getTextLength(), lable.getHeight() - 1);
		}
		g2.setColor(oldColor);
	}
	
	protected FontMetrics getFontMetrics(JComponent comp) {
        Font font = comp.getFont();
        return comp.getFontMetrics(font);
    }
	
	private class DefaultLabelContainerLayout implements LayoutManager2{

		public void addLayoutComponent(String name, Component comp)
		{
			
		}

		public void removeLayoutComponent(Component comp)
		{
			
		}

		public Dimension preferredLayoutSize(Container parent)
		{
			return new Dimension(190, 19);
		}

		public Dimension minimumLayoutSize(Container parent)
		{
			return new Dimension(50, 19);
		}

		public void layoutContainer(Container parent)
		{
			JFLabelContainer container = (JFLabelContainer)parent;
			JComponent child = container.getEditor();
			if (child == null){
				return;
			}
			int y = 0;
			if (child instanceof JTextField){
				y = 1;
			}
			child.setBounds(container.getTextLength(), y, 
					container.getWidth() - container.getTextLength(),
					container.getHeight());
		}

		public void addLayoutComponent(Component comp, Object constraints)
		{
			
		}

		public Dimension maximumLayoutSize(Container target)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public float getLayoutAlignmentX(Container target)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		public float getLayoutAlignmentY(Container target)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		public void invalidateLayout(Container target)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
}
