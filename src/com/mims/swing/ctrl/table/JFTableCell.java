package com.mims.swing.ctrl.table;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class JFTableCell implements ICell
{
	protected TableCellRenderer	cellRenderer;

    protected ICellEditor	cellEditor;
    
    protected TableColumn column;
    
    protected IRow row;
    
    private Object value;
    
    private boolean isEditable = true;
    
    private Color backGround ;

	public TableCellRenderer getCellRenderer()
	{
		return cellRenderer;
	}

	public void setCellRenderer(TableCellRenderer cellRenderer)
	{
		this.cellRenderer = cellRenderer;
	}

	public ICellEditor getCellEditor()
	{
		return cellEditor;
	}

	public void setCellEditor(ICellEditor cellEditor)
	{
		this.cellEditor = cellEditor;
		if (getCellEditor() != null)
		{

			if (getCellEditor().getEditor() instanceof JCheckBox)
			{
				setValue(((JCheckBox) getCellEditor().getEditor())
						.isSelected());
			}

			if (getCellEditor().getEditor() instanceof JComboBox)
			{
				setValue(((JComboBox) getCellEditor().getEditor())
						.getSelectedItem());
			}

		}
	}

	public TableColumn getColumn()
	{
		return column;
	}

	public void setColumn(TableColumn column)
	{
		this.column = column;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
		row.getTable().setValueAt(value, row.getRowSeq(), getColumnIndex());
	}

	public IRow getRow()
	{
		return row;
	}

	public void setRow(IRow row)
	{
		this.row = row;
	}

	private int getColumnIndex()
	{
		return column.getModelIndex();
	}

	public boolean isEditable()
	{
		if (column.getModelIndex() == 0)
		{
			return false;
		}
		return isEditable;
	}

	public void setEditable(boolean isEditable)
	{
		this.isEditable = isEditable;
	}

	public Color getBackGround()
	{
		return backGround;
	}

	public void setBackGround(Color backGround)
	{
		this.backGround = backGround;
	}

	
}
