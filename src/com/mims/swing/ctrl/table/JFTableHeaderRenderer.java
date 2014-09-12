package com.mims.swing.ctrl.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.swing.ctrl.SwingConst;

public class JFTableHeaderRenderer extends JLabel implements TableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel label = new JLabel();

	private boolean foregroundSet = false;

	private boolean backgroundSet = false;

	private boolean fontSet = false;

	private static boolean isHeaderBKSet = false;

	public JFTableHeaderRenderer()
	{
		setLayout(new BorderLayout());

		Font boldFont = this.label.getFont();
		this.label.setFont(boldFont);
		this.label.setOpaque(false);
		add(this.label);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{

		this.label.setText((value == null) ? "" : value.toString());
		JTableHeader header = table.getTableHeader();
		
		label.setHorizontalAlignment(0);
		if (!isHeaderBKSet)
		{
			setTableColumnWidth(header, column);
			header.setBackground(SwingConst.TABLE_HEADER_BG_COLOR);

			/**
			 * 设置header的高度和宽度
			 */
			header.setPreferredSize(new Dimension(getTableWidth(header),
					SwingConst.headerHeight));
			isHeaderBKSet = false;
		}

		if (header != null)
		{
			if (!foregroundSet)
			{
				setForeground(Color.WHITE);
				this.foregroundSet = true;
			}
			if (!backgroundSet)
			{
				setBackground(SwingConst.TABLE_HEADER_BG_COLOR);
				this.backgroundSet = true;
			}
			if (!fontSet)
			{
				setFont(header.getFont());
				this.fontSet = true;
			}
		}
		setBorder(new CompoundBorder(SwingConst.defaultHeaderBorder, null));
		
		if (isSelected && column == 0)
		{
			table.selectAll();
		}
		return this;
	}

	private int getTableWidth(JTableHeader header)
	{
		int tableWidth = 0;
		TableColumnModel columns = header.getColumnModel();
		for (int i = 0; i < columns.getColumnCount(); i++)
		{
			TableColumn column = columns.getColumn(i);
			tableWidth += column.getWidth();
		}
		return tableWidth;
	}

	protected Color getBackGround()
	{
		return SwingConst.TABLE_HEADER_BG_COLOR;
	}

	protected Border getDefaultBorder()
	{
		return SwingConst.defaultHeaderBorder;
	}
	
	private void setTableColumnWidth(JTableHeader header, int col)
	{
		TableColumnModel columns = header.getColumnModel();
		TableColumn column = columns.getColumn(col);
		Object value = column.getHeaderValue();
		JFTableHeaderInfo info = (JFTableHeaderInfo)value;
		
		
		
		if (info != null)
		{
			column.setPreferredWidth(info.getWidth());
		}
		else
		{
			column.setPreferredWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		}
		
	}

}
