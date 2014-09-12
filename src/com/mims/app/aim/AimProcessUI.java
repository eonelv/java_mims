/**
 * 
 */
package com.mims.app.aim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.mims.core.CoreUI;
import com.mims.core.IUIHelper;
import com.mims.core.UIFactory;
import com.mims.core.UIUtils;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFLabelContainer;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-7-24
 * 
 */
public class AimProcessUI extends CoreUI
{	
	private JTextField txtFilePath = null;
	
	private JFButton btnProcess = null;
	
	private JFButton btnSelect = null;
	
	private JFButton btnView = null;
	
	private AimFile aimFile = null;
	
	private String filePath = null;
	
	private JFLabelContainer labelName = null;
	
	private JTextField txtName = null;
	
	private JFLabelContainer labelSex = null;
	
	private JComboBox comboSex = null;
	
	public AimProcessUI(Map uiContext)
	{
		this(uiContext, null);
	}
	public AimProcessUI(Map uiContext, String title)
	{
		super(uiContext,title);
		initCtrl();
		initAction();
	}
	
	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		txtFilePath = new JTextField();
//		txtFilePath.setEditable(false);
		txtFilePath.setEnabled(false);
		txtFilePath.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 540, 30, LayoutProperty.TOP | LayoutProperty.LEFT 
						| LayoutProperty.RIGHT));
		add(txtFilePath);
		
		btnSelect = new JFButton("选择文件");
		btnSelect.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(600, 10, 100, 27, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnSelect);
		
		btnProcess = new JFButton("处理");
		btnProcess.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(710, 10, 70, 27, LayoutProperty.TOP  
						| LayoutProperty.RIGHT));
		add(btnProcess);
		
		btnView = new JFButton("查看文件");
		btnView.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(790, 10, 100, 27, LayoutProperty.TOP 
						| LayoutProperty.RIGHT));
		add(btnView);
	}
	
	private void initAction()
	{
		btnSelect.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = UIUtils.getFileChooser(JFileChooser.FILES_ONLY, "txt", "*.txt");
				int state = chooser.showOpenDialog(AimProcessUI.this);
				if (state == 1) {
					return;// 撤销则返回
				} else {
					File file = chooser.getSelectedFile();// f为选择到的目录
					try
					{
						aimFile = AimAccountImportor.imports(file);
						String temp  = file.getAbsolutePath();
						txtFilePath.setText(temp);
						int index = temp.lastIndexOf(".");
						filePath = temp.substring(0, index) + ".aim";
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		btnProcess.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					AimFileOperator.writeFile(filePath, aimFile);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		btnView.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				AimProcessUI.this.getUiContext().put("UIParam", filePath);
				AimListUI ui = (AimListUI)UIFactory.getUI(AimListUI.class.getName(), IUIHelper.UIMODEL_NEWTAB,
						AimProcessUI.this.getUiContext(),AimProcessUI.this);
				ui.setMainUI(AimProcessUI.this.getMainUI());
				frame.addTab(ui);
			}
		});
	}
	
	public JComponent getUIObject()
	{
		return this;
	}
}
