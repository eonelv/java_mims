/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

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
public class JFMenuUI extends BasicMenuUI
{
	private MenuHandler handler;
	
	private IComponentPainter painter = null;

	public static ComponentUI createUI(JComponent x)
	{
		return new JFMenuUI();
	}

	protected void installDefaults()
	{
		super.installDefaults();
		menuItem.setOpaque(false);
	}
	
	@Override
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

	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
	{

	}

	public void update(Graphics g, JComponent c)
	{
		super.update(g, c);
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();

		boolean isArmed = model.isArmed();
		boolean isPress = model.isPressed();
		boolean isRollover = model.isRollover();

		if (isArmed || isRollover)
		{
			if (isPress)
			{
				painter.paintPressBackGroud(g, c);
			} else
			{
				painter.paintOverBackGroud(g, c);
			}
		}
		super.update(g, c);
	}

	protected void paintText(Graphics g, JMenuItem menuItem,
			Rectangle textRect, String text)
	{
		ButtonModel model = menuItem.getModel();
		
		boolean isArmed = model.isArmed();
		boolean isRollover = model.isRollover();
		
		if (isArmed || isRollover)
		{
			g.setColor(Color.BLACK);
		}else{
			g.setColor(Color.WHITE);
		}
		
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
			SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text,
					mnemIndex, textRect.x, textRect.y + fm.getAscent());
		}
	}

	protected MenuListener createMenuListener(JComponent c)
	{
		if (handler == null)
		{
			handler = new MenuHandler((JMenuItem) c);
		}
		return handler;
	}

	protected ChangeListener createChangeListener(JComponent c)
	{
		if (handler == null)
		{
			handler = new MenuHandler((JMenuItem) c);
		}
		return handler;
	}

	private class MenuHandler implements ChangeListener, MenuListener
	{

		private JMenuItem menu;

		public MenuHandler(JMenuItem menu)
		{
			this.menu = menu;

			menu.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					JMenuItem menu = (JMenuItem) e.getSource();
					boolean isSelected = menu.getModel().isSelected();

					if (!isSelected)
					{
						menu.setArmed(true);
					}
				}

				public void mouseExited(MouseEvent e)
				{
					JMenuItem menu = (JMenuItem) e.getSource();
					boolean isSelected = menu.getModel().isSelected();

					if (!isSelected)
					{
						menu.setArmed(false);
					}
				}
			});
		}

		public void menuSelected(MenuEvent e)
		{
			menu.setArmed(true);
			menu.setSelected(true);
		}

		public void menuDeselected(MenuEvent e)
		{
			menu.setArmed(false);
			menu.setSelected(false);
		}

		public void menuCanceled(MenuEvent e)
		{
		}

		public void stateChanged(ChangeEvent e)
		{
		}

	}
}
