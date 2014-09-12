package com.mims.swing.ctrl.table;

import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;

public interface ICellEditor extends TableCellEditor
{
	public JComponent getEditor();
}
