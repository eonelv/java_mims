package com.mims.swing.ctrl.table;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class JFCellEditor extends DefaultCellEditor implements ICellEditor
{

	public JFCellEditor(final JCheckBox checkBox)
	{
		super(checkBox);
	}

	public JFCellEditor(final JTextField textField)
	{
		super(textField);
	}

	public JFCellEditor(final JComboBox comboBox)
	{
		super(comboBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{
		delegate.setValue(value);
		if (editorComponent instanceof JCheckBox)
		{
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component c = renderer.getTableCellRendererComponent(table, value,
					isSelected, true, row, column);
			if (c != null)
			{
				((JCheckBox) editorComponent)
						.setHorizontalAlignment(SwingConstants.CENTER);
				editorComponent.setOpaque(true);
				editorComponent.setBackground(c.getBackground());
				if (c instanceof JComponent)
				{
					editorComponent.setBorder(((JComponent) c).getBorder());
				}
			} else
			{
				editorComponent.setOpaque(false);
			}
		}
		return editorComponent;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JComponent getEditor()
	{
		return editorComponent;
	}

}
