package com.mims.swing.ctrl.table;

import java.util.List;

public interface ITableRowModel
{
	public int getRowCount();
	
	public IRow getRow(int r);
	
	public void addRow(IRow row);
	
	public IRow addRow();
	
	public List<IRow> getRows();
}
