/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

import sun.swing.SwingUtilities2;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFMenuPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-9
 * 
 */
public class JFMenuItemUI extends BasicMenuItemUI
{
	
	private IComponentPainter painter;
	
	public static ComponentUI createUI(JComponent x)
	{
		return new JFMenuItemUI();
	}

	protected void installDefaults()
	{
		super.installDefaults();
		menuItem.setOpaque(false);
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		IPainterVisitor b = (IPainterVisitor)c;
		if (b.getPainter() == null){
			painter = new JFMenuPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
		
	}

	public void paint(Graphics g, JComponent c)
	{
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();

		boolean isArmed = model.isArmed();
		boolean isRollover = model.isRollover();

		if (isArmed || isRollover)
		{
			if (model.isSelected())
			{
				painter.paintPressBackGroud(g, c);
			} else
			{
				painter.paintOverBackGroud(g, c);
			}
		}

		super.paint(g, c);
	}

	protected void paintText(Graphics g, JMenuItem menuItem,
			Rectangle textRect, String text)
	{
		ButtonModel model = menuItem.getModel();

		FontMetrics fm = SwingUtilities2.getFontMetrics(menuItem, g);
		int mnemIndex = menuItem.getDisplayedMnemonicIndex();

		if (!model.isEnabled())
		{
			// *** paint the text disabled
			if (UIManager.get("MenuItem.disabledForeground") instanceof Color)
			{
				g.setColor(UIManager.getColor("MenuItem.disabledForeground"));
				SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text,
						mnemIndex, textRect.x, textRect.y + fm.getAscent());
			} else
			{
				g.setColor(menuItem.getBackground().brighter());
				SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text,
						mnemIndex, textRect.x, textRect.y + fm.getAscent());
				g.setColor(menuItem.getBackground().darker());
				SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text,
						mnemIndex, textRect.x - 1, textRect.y + fm.getAscent()
								- 1);
			}
		} else
		{
			// *** paint the text normally
			if (model.isArmed()
					|| (menuItem instanceof JMenu && model.isSelected()))
			{
				g.setColor(selectionForeground); // Uses protected field.
			}
//			g.setColor(Color.WHITE);
			SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text,
					mnemIndex, textRect.x, textRect.y + fm.getAscent());
		}
	}
	
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
	{

	}
}
