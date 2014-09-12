/**
 * 
 */
package com.mims.ctrltest;

import java.awt.Dimension;

import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFComboBox;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.ctrl.painter.JFButtonPainter4Win7;
import com.mims.swing.ctrl.win.WinToolBarPanel;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

/**
 * 
 * 描述:.<p>
 *
 * @author 李威 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class TestToolBarPanel2 extends WinToolBarPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3802653528632730381L;
	
	
	public TestToolBarPanel2(String title){
		super(title);
		setBorder(new JFBorders.JFComboBoxListBorder());
		JFTextField field = new JFTextField();
		field.setPreferredSize(new Dimension(120, 22));
		add(field);
		
		JFPanel panel = new JFPanel();
		panel.setPreferredSize(new Dimension(120, 10));
		panel.setLayout(new FlexLayout());
		panel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 150, 100));
		JFButton btnSelect = new JFButton("选择文件");
		btnSelect.setPainter(new JFButtonPainter4Win7());
		btnSelect.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 0, 80, 25, LayoutProperty.TOP 
						| LayoutProperty.LEFT));
		panel.add(btnSelect);
		
		JFButton btnProcess = new JFButton("处理");
		btnProcess.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 27, 80, 25, LayoutProperty.TOP  
						 | LayoutProperty.LEFT));
		panel.add(btnProcess);
		
		JFButton btnView = new JFButton("查看文件");
		btnView.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 53, 80, 25, LayoutProperty.TOP 
						 | LayoutProperty.LEFT));
		panel.add(btnView);
		
		JFTextField field1 = new JFTextField();
		field1.setPreferredSize(new Dimension(120, 22));
		field1.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(150 - 28, 0, 28, 99, LayoutProperty.TOP 
						 | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panel.add(field1);
		
		add(panel);
		JFComboBox box = new JFComboBox();
		box.setPreferredSize(new Dimension(250, 20));
		add(box);
		
	}
}
