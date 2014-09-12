/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.mims.swing.look.JFBorders;

import sun.swing.DefaultLookup;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-24
 * 
 */
public class JFListCellRenderer extends DefaultListCellRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6369486047515551908L;
	private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1,
			1);
	private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1,
			1, 1);

	private Border getNoFocusBorder()
	{
		Border border = DefaultLookup.getBorder(this, ui,
				"List.cellNoFocusBorder");
		if (System.getSecurityManager() != null)
		{
			if (border != null)
				return border;
			return SAFE_NO_FOCUS_BORDER;
		} else
		{
			if (border != null
					&& (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER))
			{
				return border;
			}
			return noFocusBorder;
		}
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		setComponentOrientation(list.getComponentOrientation());

		Color bg = null;
		Color fg = null;

		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert()
				&& dropLocation.getIndex() == index)
		{

			bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
			fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

			isSelected = true;
		}

		if (isSelected)
		{
			setBackground(bg == null ? list.getSelectionBackground() : bg);
			setForeground(fg == null ? list.getSelectionForeground() : fg);
		} else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if (value instanceof Icon)
		{
			setIcon((Icon) value);
			setText("");
		} else
		{
			setIcon(null);
			setText((value == null) ? "" : value.toString());
		}

		setEnabled(list.isEnabled());
		setFont(list.getFont());

		Border border = null;
		if (isSelected)
		{
			if (isSelected)
			{
//				border = DefaultLookup.getBorder(this, ui,
//						"List.focusSelectedCellHighlightBorder");
//				border = new JFBorders.TextFieldBorder();
				border = new JFBorders.ListSelectedBorder();
			}
			if (border == null)
			{
				border = DefaultLookup.getBorder(this, ui,
						"List.focusCellHighlightBorder");
			}
		} else
		{
			border = getNoFocusBorder();
		}
		setBorder(border);
		setOpaque(false);
		return this;
	}
}
