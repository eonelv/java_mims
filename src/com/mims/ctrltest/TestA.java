/**
 * 
 */
package com.mims.ctrltest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.core.CoreUI;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFComboBox;
import com.mims.swing.ctrl.JFDatePicker;
import com.mims.swing.ctrl.JFLabelContainer;
import com.mims.swing.ctrl.JFList;
import com.mims.swing.ctrl.JFOptionPane;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.ctrl.JFScrollPane;
import com.mims.swing.ctrl.JFScrollPaneLayout;
import com.mims.swing.ctrl.JFSeparator;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.ctrl.painter.JFButtonPainter4Win7;
import com.mims.swing.ctrl.table.JFCellEditor;
import com.mims.swing.ctrl.table.JFCheckBoxCellRender;
import com.mims.swing.ctrl.table.JFTable;
import com.mims.swing.ctrl.table.JFTableHeaderInfo;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;
import com.mims.swing.look.JFLookAndFeelColor;

/**
 * 
 * 描述:.<p>
 *
 * @author 李威 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class TestA extends CoreUI
{
	private JFButton btnProcess = null;
	
	private JFButton btnSelect = null;
	
	private JFButton btnView = null;
	
	private JFLabelContainer labelName = null;
	
	private JFTextField txtName = null;
	
	private JFLabelContainer labelNumber = null;
	
	private JFTextField txtNumber = null;
	
	private JFLabelContainer labelSex = null;
	
	private JComboBox comboSex = null;
	
	//
	private JFLabelContainer labelName1 = null;
	
	private JFTextField txtName1 = null;
	
	private JFLabelContainer labelNumber1 = null;
	
	private JFTextField txtNumber1 = null;
	
	private JFLabelContainer labelSysColor = null;
	
	private JFComboBox comboSysColor = null;
	
	private JFLabelContainer labelDatePicker = null;
	
	private JFDatePicker datePicker = null;
	
	private JFList listTest = null;
	
	private JFPanel panelTest = null;
	
	private JFTable table = null;
	
	private JFSeparator separator = null;
	
	public TestA(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
		initActions();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6224588817912273848L;

	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		separator = new JFSeparator();
		separator.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 5, 880, 5, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT ));
		add(separator);
		btnSelect = new JFButton("选择文件");
		btnSelect.setPainter(new JFButtonPainter4Win7());
		btnSelect.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(600, 10, 100, 25, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnSelect);
		
		btnProcess = new JFButton("处理");
		btnProcess.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(710, 10, 70, 22, LayoutProperty.TOP  
						| LayoutProperty.RIGHT));
		add(btnProcess);
		
		btnView = new JFButton("查看文件");
		btnView.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(790, 10, 100, 22, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnView);
		
		labelNumber = new JFLabelContainer("学号");
		labelNumber.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		txtNumber = new JFTextField();
		labelNumber.setEditor(txtNumber);
		add(labelNumber);
		
		labelName = new JFLabelContainer("姓名");
		labelName.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 40, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT_RESIZE));
		
		txtName = new JFTextField();
		labelName.setEditor(txtName);
		add(labelName);
		
		labelSex = new JFLabelContainer("性别");
		labelSex.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(621, 40, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT));
		
		comboSex = new JFComboBox(new String[]{"男","女"});
		labelSex.setEditor(comboSex);
		add(labelSex);
		
		//
		labelNumber1 = new JFLabelContainer("学号");
		labelNumber1.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		txtNumber1 = new JFTextField();
		labelNumber1.setEditor(txtNumber1);
		add(labelNumber1);
		
		labelName1 = new JFLabelContainer("姓名");
		labelName1.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 70, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT_RESIZE));
		
		txtName1 = new JFTextField();
		labelName1.setEditor(txtName1);
		txtName1.setEnabled(false);
		add(labelName1);
		
		labelSysColor = new JFLabelContainer("皮肤");
		labelSysColor.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(621, 70, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT));
		
		comboSysColor = new JFComboBox(new String[]{
				JFLookAndFeelColor.JF_BLUE,
				JFLookAndFeelColor.JF_RED,
				JFLookAndFeelColor.WIN7,
				JFLookAndFeelColor.BLUE,JFLookAndFeelColor.YELLOW,JFLookAndFeelColor.GREEN,
				JFLookAndFeelColor.RED});
		labelSysColor.setEditor(comboSysColor);
		add(labelSysColor);
		
		
		labelDatePicker = new JFLabelContainer("日期");
		labelDatePicker.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 100, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		datePicker = new JFDatePicker();
		labelDatePicker.setEditor(datePicker);
		add(labelDatePicker);
		
		listTest = new JFList(); 
		listTest.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 130, 270, 170, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		listTest.setListData(new Object[]{"A", "B"});
		add(listTest);
		
		panelTest = new JFPanel(); 
		panelTest.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(280, 100, 270, 200, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelTest.setBackground(Color.LIGHT_GRAY);
		panelTest.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelTest);
		
		initTable();
		for (int i=0;i<20;i++){
			table.addRow();
		}
		JFScrollPane scrollPane = new JFScrollPane(table);
		scrollPane.setLayout(new JFScrollPaneLayout());
		
//		scrollPane.setVerticalScrollBar(new JFScrollBar());
//		scrollPane.setHorizontalScrollBar(new JFScrollBar(Adjustable.HORIZONTAL));
		
		scrollPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 303, 880, 290, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT 
						| LayoutProperty.BOTTOM));
		add(scrollPane);
		
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
		
		
		table = new JFTable(model);
		
		
	}
	
	private void initActions(){
		comboSysColor.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				String key = (String)comboSysColor.getSelectedItem();
				Object[] objs = JFLookAndFeelColor.getInstance().getSysColors(key);
				if (objs != null){
					UIManager.getDefaults().putDefaults(objs);
					SwingConst.reset();
					SwingUtilities.getWindowAncestor(comboSysColor).repaint();
				}
				
			}
		});
		btnSelect.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
//				JFOptionPane.showMessageDialog(TestA.this, "李威......................................D........" +
//						"...........................................D.............................D");
//				JFOptionPane.showConfirmDialog(TestA.this,"message","title",JFOptionPane.YES_NO_OPTION);
				
				JFOptionPane.showInputDialog(null,"message","title",JFOptionPane.YES_NO_OPTION);
			}
		});
	}
}
