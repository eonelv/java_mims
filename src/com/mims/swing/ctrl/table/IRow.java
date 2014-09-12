/**
 * 
 */
package com.mims.swing.ctrl.table;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-6
 * 
 */
public interface IRow
{
	public void addCell(ICell cell);
	
	public ICell getCell(String name);
	
	public ICell getCell(int r);
	
	public JFTable getTable();
	
	public void setTable(JFTable table);
	
	public int getRowSeq();
	
	public void setRowSeq(int rowSeq);
}
