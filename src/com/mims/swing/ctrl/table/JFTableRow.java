/**
 * 
 */
package com.mims.swing.ctrl.table;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-6
 * 
 */
public class JFTableRow implements IRow
{
	private Map<TableColumn, ICell> cells = new HashMap<TableColumn, ICell>();

	private JFTable table;

	private TableModel model;

	private int rowSeq;

	private int colSize;

	public JFTableRow(JFTable table)
	{
		this.table = table;
		model = table.getModel();
		colSize = table.getTableHeader().getColumnModel().getColumnCount();
		((DefaultTableModel) model).addRow(new String[colSize]);
	}

	public void addCell(ICell cell)
	{
		cells.put(cell.getColumn(), cell);
	}

	public ICell getCell(String name)
	{
		TableColumnModel model = table.getTableHeader().getColumnModel();
		int size = model.getColumnCount();
		for (int i = 0; i < size; i++)
		{
			TableColumn column = model.getColumn(i);
			Object value = column.getHeaderValue();
			JFTableHeaderInfo info = (JFTableHeaderInfo) value;
			if (name.equalsIgnoreCase(info.getNumber()))
			{
				return (JFTableCell) cells.get(column);
			}
		}
		return null;
	}

	public ICell getCell(int r)
	{
		TableColumnModel model = table.getTableHeader().getColumnModel();
		TableColumn column = model.getColumn(r);
		return (JFTableCell) cells.get(column);

	}

	public JFTable getTable()
	{
		return table;
	}

	public void setTable(JFTable table)
	{
		this.table = table;
	}

	public int getRowSeq()
	{
		return rowSeq;
	}

	public void setRowSeq(int rowSeq)
	{
		this.rowSeq = rowSeq;
	}

}
