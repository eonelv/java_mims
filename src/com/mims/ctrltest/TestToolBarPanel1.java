/**
 * 
 */
package com.mims.ctrltest;

import java.awt.Dimension;

import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFComboBox;
import com.mims.swing.ctrl.painter.JFButtonPainter4Win7;
import com.mims.swing.ctrl.win.WinToolBarPanel;
import com.mims.swing.layout.LayoutProperty;

/**
 * 
 * 描述:.<p>
 *
 * @author 李威 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class TestToolBarPanel1 extends WinToolBarPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3802653528632730381L;
	
	
	public TestToolBarPanel1(String title){
		super(title);
		
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		JFButton btnSelect = new JFButton("选择文件");
		btnSelect.setPainter(new JFButtonPainter4Win7());
		btnSelect.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(600, 10, 100, 25, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnSelect);
		
		JFButton btnProcess = new JFButton("处理");
		btnProcess.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(710, 10, 70, 22, LayoutProperty.TOP  
						| LayoutProperty.RIGHT));
		add(btnProcess);
		
		JFButton btnView = new JFButton("查看文件");
		btnView.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(790, 10, 100, 22, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnView);
		JFComboBox box = new JFComboBox();
		box.setPreferredSize(new Dimension(250, 20));
		add(box);
		
	}
}
