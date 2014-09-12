/**
 * 
 */
package com.mims.swing.ctrl.table;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.swing.ctrl.SwingConst;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-5
 * 
 */
public class JFTable extends JTable
{

	private JFTableRowModel rowModel;

	public JFTable(TableColumnModel model)
	{
		super();
		TableColumnModel lmodel = new DefaultTableColumnModel();

		TableColumn column = new TableColumn();

		JFTableHeaderInfo columnInfo = new JFTableHeaderInfo();

		columnInfo.setAlign(SwingConstants.CENTER);
		columnInfo.setWidth(50);
		column.setHeaderValue(columnInfo);
		column.setModelIndex(0);

		lmodel.addColumn(column);

		int count = model.getColumnCount();
		for (int i = 0; i < count; i++)
		{
			TableColumn temp = model.getColumn(i);
			temp.setModelIndex(i + 1);
			lmodel.addColumn(temp);
		}

		rowModel = new JFTableRowModel(this);
		setModel(new DefaultTableModel(0, lmodel.getColumnCount()));
		setColumnModel(lmodel);
		setRowHeight(SwingConst.DEFAULT_ROW_HEIGHT);
		getTableHeader().setDefaultRenderer(new JFTableHeaderRenderer());
		setDefaultRenderer(Object.class, new JFTableCellRender());
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public IRow addRow(){
		return addRow(new Object[]{});
	}

	public IRow addRow(Object[] obj)
	{
		Object[] temp = new Object[obj.length + 1];
		temp[0] = ((DefaultTableModel) getModel()).getRowCount() + 1;
		System.arraycopy(obj, 0, temp, 1, obj.length);

		IRow row = rowModel.addRow();
		row.getCell(0).setValue(temp[0]);
		
		for (int i = 0; i <= obj.length; i++)
		{
			row.getCell(i).setValue(temp[i]);
		}
		return row;
	}

	public TableCellEditor getCellEditor(int row, int column)
	{
		TableColumn tableColumn = getColumnModel().getColumn(column);
		TableCellEditor editor = rowModel.getRow(row).getCell(column)
				.getCellEditor();
		if (editor == null)
		{
			editor = tableColumn.getCellEditor();
		}

		if (editor == null)
		{
			editor = getDefaultEditor(getColumnClass(column));
		}
		return editor;
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		TableColumn tableColumn = getColumnModel().getColumn(column);
		TableCellRenderer renderer  = rowModel.getRow(row).getCell(column).getCellRenderer();
//		if (renderer == null)
//		{
//			renderer = tableColumn.getCellRenderer();
//		}
		
		if (renderer == null)
		{
			renderer = getDefaultRenderer(getColumnClass(column));
		}
		return renderer;
	}
	
	public boolean isCellEditable(int row, int column) {
        return rowModel.getRow(row).getCell(column).isEditable();
    }

	public JFTableRowModel getRowModel()
	{
		return rowModel;
	}

	public void setRowModel(JFTableRowModel rowModel)
	{
		this.rowModel = rowModel;
	}
	
	
}
