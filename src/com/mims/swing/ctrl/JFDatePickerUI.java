/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.plaf.ComponentUI;

import com.mims.swing.ctrl.util.ResourceUtils;
import com.mims.swing.ctrl.util.SwingUtils;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-12
 * 
 */
public class JFDatePickerUI extends ComponentUI
{
	private JFDatePicker datePicker = null;
	
	private JComponent editor = null;
	
	private JFButton btnPicker = null;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new JFDatePickerUI();
	}

	public void installUI(JComponent c)
	{
		datePicker = (JFDatePicker)c;
		datePicker.setEditor(createEditor());
		datePicker.setLayout(new DatePickerLayout());
		createButton();
		
		datePicker.add(editor);
		datePicker.add(btnPicker);
	}

	public void uninstallUI(JComponent c)
	{
		datePicker = null;
		editor = null;
		btnPicker = null;
	}
	
	private void createButton(){
		btnPicker = new JFButton(ResourceUtils.getIcon("calendar_20_24.png"));
		
		btnPicker.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				JDialog dialog = new JFDatePickerDialog(datePicker);
				
				Point location = editor.getLocationOnScreen();
				dialog.setLocation((int)location.getX(), (int)location.getY() + editor.getHeight() + 1);
				dialog.setVisible(true);
			}
		});
		
	}

	public Dimension getPreferredSize(JComponent c)
	{
		return c.getPreferredSize();
	}
	
	protected IDatePickerEdtor createEditor(){
		IDatePickerEdtor picker = new DatePickerEdtor();
		editor = picker.getEditorComponent();
		return picker;
	}
	
	protected Insets getInsets()
	{
		return datePicker.getInsets();
	}
	
	private class DatePickerLayout implements LayoutManager2{

		public void addLayoutComponent(String name, Component comp)
		{
			
		}

		public void removeLayoutComponent(Component comp)
		{
			
		}

		public Dimension preferredLayoutSize(Container parent)
		{
			return new Dimension(20,20);
		}

		public Dimension minimumLayoutSize(Container parent)
		{
			return preferredLayoutSize(parent);
		}

		public void layoutContainer(Container parent)
		{
			JFDatePicker cb = (JFDatePicker) parent;
			int width = cb.getWidth();
			int height = cb.getHeight();

			Insets insets = getInsets();
			int buttonHeight = height - (insets.top + insets.bottom) + 1;
			int buttonWidth = buttonHeight;
			if (btnPicker != null)
			{
				Insets arrowInsets = btnPicker.getInsets();
				buttonWidth = buttonHeight;
			}
			Rectangle cvb;

			if (btnPicker != null)
			{
				if (SwingUtils.isLeftToRight(cb))
				{
					btnPicker.setBounds(width - (insets.right + buttonWidth) + 2
							, insets.top + 2, buttonWidth - 4,
							buttonWidth - 4);
				} else
				{
					btnPicker.setBounds(insets.left, insets.top, buttonWidth,
							buttonHeight);
				}
			}
			if (editor != null)
			{
				cvb = rectangleForCurrentValue();
				editor.setBounds(cvb);
			}
		}

		public void addLayoutComponent(Component comp, Object constraints)
		{
			
		}

		public Dimension maximumLayoutSize(Container target)
		{
			return null;
		}

		public float getLayoutAlignmentX(Container target)
		{
			return 0;
		}

		public float getLayoutAlignmentY(Container target)
		{
			return 0;
		}

		public void invalidateLayout(Container target)
		{
			
		}
		
		protected Rectangle rectangleForCurrentValue()
		{
			int width = datePicker.getWidth();
			int height = datePicker.getHeight();
			Insets insets = getInsets();
			int buttonSize = height - (insets.top + insets.bottom);
			if (btnPicker != null)
			{
				buttonSize = btnPicker.getWidth();
			}
			if (SwingUtils.isLeftToRight(datePicker))
			{
				return new Rectangle(insets.left, insets.top, width
						- (insets.left + insets.right + buttonSize + 3), height
						- (insets.top + insets.bottom));
			} else
			{
				return new Rectangle(insets.left + buttonSize, insets.top, width
						- (insets.left + insets.right + buttonSize), height
						- (insets.top + insets.bottom));
			}
		}
		
	}
	
	
}
