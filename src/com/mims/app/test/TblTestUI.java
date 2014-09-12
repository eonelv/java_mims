package com.mims.app.test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.app.aim.AimFile;
import com.mims.core.CoreUI;
import com.mims.swing.ctrl.JFScrollPane;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.ctrl.table.IRow;
import com.mims.swing.ctrl.table.JFCellEditor;
import com.mims.swing.ctrl.table.JFCheckBoxCellRender;
import com.mims.swing.ctrl.table.JFTable;
import com.mims.swing.ctrl.table.JFTableHeaderInfo;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

public class TblTestUI extends CoreUI
{
	private JFTable tblMain = null;

	private JPanel corePanel = null;

	private JFileChooser chooser = null;

	private AimFile aimFile = null;

	private List tools = new ArrayList();

	public TblTestUI(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
	}

	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));

		initTable();

		JFScrollPane scrollPane = new JFScrollPane(tblMain);

		scrollPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 580, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT
						| LayoutProperty.BOTTOM));

		add(scrollPane);
	}
	
	private void initTable()
	{
		TableColumnModel model = new DefaultTableColumnModel();
		TableColumn column = new TableColumn();
		
		JComboBox editor = new JComboBox();
		editor.addItem("电视剧");
		editor.addItem("电影");
		editor.setSelectedItem("电影");

		JFTableHeaderInfo columnInfo = new JFTableHeaderInfo();
		columnInfo.setAlign(SwingConstants.LEFT);
		columnInfo.setName("类型");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		column.setCellEditor(new JFCellEditor(editor));
		model.addColumn(column);
		
		columnInfo = new JFTableHeaderInfo();
		column = new TableColumn();
		columnInfo.setAlign(SwingConstants.LEFT);
		columnInfo.setName("账号");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		model.addColumn(column);
		
		columnInfo = new JFTableHeaderInfo();
		column = new TableColumn();
		columnInfo.setAlign(SwingConstants.LEFT);
		columnInfo.setName("密码");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		model.addColumn(column);
		
		columnInfo = new JFTableHeaderInfo();
		column = new TableColumn();
		columnInfo.setAlign(SwingConstants.CENTER);
		columnInfo.setName("备注");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		
		JCheckBox editorC = new JCheckBox();
		editorC.setSelected(true);
		column.setCellEditor(new JFCellEditor(editorC));
		column.setCellRenderer(new JFCheckBoxCellRender());
		
		model.addColumn(column);
		
		
		tblMain = new JFTable(model);
		
		insertTab();
	}


	private void insertTab()
	{

		for (int i = 0; i < 5; i++)
		{
			IRow row = tblMain.addRow(new Object[]{  });
		}
		IRow row = tblMain.addRow(new Object[]{});
		JCheckBox editor = new JCheckBox();
		editor.setSelected(true);
		
		
		row.getCell(4).setValue(false);
		row.getCell(4).setBackGround(Color.YELLOW);

	
	}

	public List getTool()
	{
		return tools;
	}

	public String getTitle()
	{
		return "Table测试";
	}
}
