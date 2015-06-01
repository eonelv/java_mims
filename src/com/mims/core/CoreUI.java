/**
 * 
 */
package com.mims.core;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import com.mims.swing.ctrl.JFCheckBoxMenuItem;
import com.mims.swing.ctrl.JFMenu;
import com.mims.swing.ctrl.JFMenuItem;
import com.mims.swing.ctrl.JFOptionPane;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.ctrl.JFToolButton;
import com.mims.swing.ctrl.util.ResourceUtils;

/**
 * ����:.<p>
 *
 * @author ���� 
 *
 * @Date: 2011-7-22
 * 
 */
/**
 * ����:.<p>
 *
 * @author ���� 
 *
 * @Date: 2011-7-24
 * 
 */
public class CoreUI extends JFPanel implements IUIHelper
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6974489590109112544L;

	protected MainFrame frame = null;
	
	private CoreUI mainUI = null;
	
	private Map uiContext = new HashMap();
	
	private List tools = new ArrayList();
	
	private List menus = new ArrayList();
	
	private JFToolButton btnExit = null;
	
	private JFMenu menuFile = null;
	
	private JFMenu menuAbout = null;
	
	private JFMenuItem muneItemExit = null;
	
	protected String title = "CoreUI";

	public CoreUI(Map uiContext, String title)
	{
		super();
		this.uiContext = uiContext;
		initToolsAndMenus();
		this.title = title;
	}
	
	private void initToolsAndMenus()
	{
		btnExit = new JFToolButton("文件");

		tools.add(new JFToolButton("按钮", ResourceUtils.getIcon("folder_close.png")));
		
		tools.add(btnExit);
		
		menuFile = new JFMenu("文件");
		muneItemExit = new JFMenuItem("保存");
		
		menuFile.add(muneItemExit);
		menuFile.add(new JFMenuItem("退出"));
		
		menuAbout = new JFMenu("关于", ResourceUtils.getIcon("query_16.png"));
		menuAbout.add(new JFMenuItem("测试", ResourceUtils.getIcon("query_16.png")));		
		
		btnExit.add(new JFMenuItem("退出", ResourceUtils.getIcon("query_16.png")));
		btnExit.add(new JFMenuItem("退出"));
		menus.add(menuFile);
		menus.add(menuAbout);
		
	}
	
	public List getMenu()
	{
		return menus;
	}

	public List getTool()
	{
		return tools;
	}

	public int getUIModel()
	{
		return IUIHelper.UIMODEL_NEWTAB;
	}

	public JComponent getUIObject()
	{
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public void registMain(MainFrame frame)
	{
		this.frame = frame;
	}
	
	public Map getUiContext()
	{
		return uiContext;
	}


	public void setUiContext(Map uiContext)
	{
		this.uiContext = uiContext;
	}

	protected void addDefaultUIContextItem()
	{
	}

	public CoreUI getMainUI()
	{
		return mainUI;
	}


	public void setMainUI(CoreUI mainUI)
	{
		this.mainUI = mainUI;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public Dimension getPreferredSize()
	{
		Dimension size = super.getPreferredSize();
		if (size == null){
			size = new Dimension(10,10);
		}
		return size;
	}

	public void showUI()
	{
		JFOptionPane.getWindowForComponent(this).setVisible(true);
	}
	
	
}
