package com.mims.app.aim;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.core.CoreUI;
import com.mims.core.UIUtils;
import com.mims.swing.ctrl.JFComboBox;
import com.mims.swing.ctrl.JFScrollBar;
import com.mims.swing.ctrl.JFScrollPane;
import com.mims.swing.ctrl.JFScrollPaneLayout;
import com.mims.swing.ctrl.JFToolButton;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.ctrl.table.IRow;
import com.mims.swing.ctrl.table.JFCellEditor;
import com.mims.swing.ctrl.table.JFCheckBoxCellRender;
import com.mims.swing.ctrl.table.JFTable;
import com.mims.swing.ctrl.table.JFTableHeaderInfo;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

public class AimListUI extends CoreUI
{
	private JFToolButton btnFilter = null;

	private JFTable tblMain = null;

	private JPanel corePanel = null;

	private JFileChooser chooser = null;

	private AimFile aimFile = null;

	private List tools = new ArrayList();

	public AimListUI(Map uiContext)
	{
		this(uiContext, null);
	}
	public AimListUI(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
		initAction();
		initTableData(this);
	}

	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));

		btnFilter = new JFToolButton("打开文件");
		initTable();
		JFScrollPane scrollPane = new JFScrollPane(tblMain);
		scrollPane.setLayout(new JFScrollPaneLayout());
		
		scrollPane.setVerticalScrollBar(new JFScrollBar());
		scrollPane.setHorizontalScrollBar(new JFScrollBar(Adjustable.HORIZONTAL));

		scrollPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 580, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT
						| LayoutProperty.BOTTOM));

		add(scrollPane);
		chooser = UIUtils.getFileChooser(JFileChooser.FILES_ONLY, "aim",
				"Aim文件");
		tools.add(btnFilter);
	}
	
	private void initTable()
	{
		TableColumnModel model = new DefaultTableColumnModel();
		TableColumn column = new TableColumn();

		JFTableHeaderInfo columnInfo = new JFTableHeaderInfo();
		columnInfo.setAlign(SwingConstants.LEFT);
		columnInfo.setName("类型");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		model.addColumn(column);
		
		columnInfo = new JFTableHeaderInfo();
		column = new TableColumn();
		columnInfo.setAlign(SwingConstants.CENTER);
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
		columnInfo.setName("保存");
		columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
		column.setHeaderValue(columnInfo);
		
		JCheckBox editorC = new JCheckBox();
		editorC.setSelected(true);
		column.setCellEditor(new JFCellEditor(editorC));
		column.setCellRenderer(new JFCheckBoxCellRender());
		
		model.addColumn(column);
		
		
		tblMain = new JFTable(model);
		
		
	}

	private void initAction()
	{
		btnFilter.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				initTableData(AimListUI.this);

			}
		});
	}

	private void initTableData(Component parent)
	{
		File file = null;
		if (getUiContext().get("UIParam") != null)
		{
			file = new File((String) getUiContext().get("UIParam"));
			insertTab(file);
			return;
		}
		int state = chooser.showOpenDialog(parent);
		if (state == 1)
		{
			return;// 撤销则返回
		} else
		{
			file = chooser.getSelectedFile();// f为选择到的目录
			insertTab(file);
		}
	}

	private void insertTab(File file)
	{
		try
		{
			aimFile = AimFileOperator.readAimFile(file);
			List accounts = aimFile.getBody().getAccountInfos();
			AccountInfo info = null;
			for (int i = 0; i < accounts.size(); i++)
			{
				info = (AccountInfo) accounts.get(i);
				IRow row = tblMain.addRow(new Object[]{ info.getType(), info.getName(), info.getPassword() });
//				IRow row = tblMain.addRow(new Object[]{ "A","B","C" });
				if (i == accounts.size() - 5)
				{
					JFComboBox editor = new JFComboBox();
					editor.addItem("1");
					editor.addItem("2");
					row.getCell(4).setCellEditor(new JFCellEditor(editor));
					row.getCell(4).setCellRenderer(null);
				}
			}
			IRow row = tblMain.addRow(new Object[]{});
			JCheckBox editor = new JCheckBox();
			editor.setSelected(true);
			row.getCell(4).setCellEditor(new JFCellEditor(editor));
			row.getCell(4).setCellRenderer(new JFCheckBoxCellRender());
			
			row.getCell(4).setValue(false);
			row.getCell(4).setBackGround(Color.YELLOW);

		} catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		} catch (IOException e2)
		{
			e2.printStackTrace();
		}
	}

	public List getTool()
	{
		tools.addAll(super.getTool());
		return tools;
	}

	public String getTitle()
	{
		return "账号查询";
	}
}
