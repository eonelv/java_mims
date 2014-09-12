package com.mims.swing.ctrl.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class JFTableRowModel implements ITableRowModel
{

	private List<IRow> rows = new ArrayList<IRow>();

	private JFTable table;

	public JFTableRowModel(JFTable t)
	{
		table = t;
	}

	public int getRowCount()
	{
		return rows.size();
	}

	public IRow getRow(int r)
	{
		return rows.get(r);
	}

	public void addRow(IRow row)
	{
		rows.add(row);
	}

	public List<IRow> getRows()
	{
		return rows;
	}

	public IRow addRow()
	{
		IRow row = new JFTableRow(table);
		row.setRowSeq(rows.size());
		ICell cell;
		TableColumnModel model = table.getTableHeader().getColumnModel();
		int size = model.getColumnCount();
		for (int i = 0; i < size; i++)
		{
			cell = new JFTableCell();
			TableColumn column = model.getColumn(i);
			cell.setCellRenderer(column.getCellRenderer());
			cell.setColumn(column);
			cell.setRow(row);
			cell.setCellEditor((ICellEditor) column.getCellEditor());
			

			if (cell.getCellEditor() != null)
			{

				if (cell.getCellEditor().getEditor() instanceof JCheckBox)
				{
					cell.setValue(((JCheckBox) cell.getCellEditor().getEditor())
							.isSelected());
				}

				if (cell.getCellEditor().getEditor() instanceof JComboBox)
				{
					cell.setValue(((JComboBox) cell.getCellEditor().getEditor())
							.getSelectedItem());
				}

			}
			row.addCell(cell);
		}

		addRow(row);
		return row;
	}

}
