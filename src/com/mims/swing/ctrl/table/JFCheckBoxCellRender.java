package com.mims.swing.ctrl.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.TableCellRenderer;

public class JFCheckBoxCellRender extends JLabel implements TableCellRenderer
{

	private JCheckBox render = new JCheckBox();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JFCheckBoxCellRender()
	{
		setLayout(new BorderLayout());
		setOpaque(true);

		add(render);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (value != null)
		{
			render.setSelected((Boolean) value);
		} else
		{
			render.setSelected(false);
		}
		JFTable table1 = (JFTable) table;
		ICell cell = table1.getRowModel().getRow(row).getCell(column);
		cell.getColumn().getWidth();
//		render.setBounds(cell.getColumn().getWidth() / 2 - 15,
//				table1.getRowHeight() / 4, 15, table1.getRowHeight() / 2);
		render.setHorizontalAlignment(SwingConstants.CENTER);

		if (isSelected || hasFocus)
		{
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
			
			render.setBackground(table.getSelectionBackground());
			render.setForeground(table.getSelectionForeground());
		} else
		{
			Color bgColor = table1.getRowModel().getRow(row).getCell(column)
					.getBackGround();
			if (bgColor == null)
			{
				bgColor = table.getBackground();
			}
			setBackground(bgColor);
			setForeground(table.getForeground());
			render.setBackground(bgColor);
			render.setForeground(table.getForeground());
		}

		Border defaultCellBorder = new BevelBorder(0, table.getBackground(),
				table.getBackground(), table.getBackground(),
				table.getBackground());

		render.setBorder(new CompoundBorder(defaultCellBorder, null));
		return this;
	}

}
