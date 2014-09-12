package com.mims.swing.ctrl.table;

import java.awt.Color;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public interface ICell
{
	public TableCellRenderer getCellRenderer();
	
	public void setCellRenderer(TableCellRenderer cellRenderer);
	
	public ICellEditor getCellEditor();
	
	public void setCellEditor(ICellEditor cellEditor);
	
	public TableColumn getColumn();
	
	public void setColumn(TableColumn column);
	
	public Object getValue();
	
	public void setValue(Object value);
	
	public IRow getRow();
	
	public void setRow(IRow row);
	
	public boolean isEditable();
	
	public void setEditable(boolean isEditable);
	
	public void setBackGround(Color color);
	
	public Color getBackGround();
}
