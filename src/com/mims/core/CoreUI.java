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
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-7-22
 * 
 */
/**
 * 描述:.<p>
 *
 * @author 李威 
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
		btnExit = new JFToolButton("退出");

		tools.add(new JFToolButton("退出---1", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---2", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---3", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---4", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---5", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---6", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---7", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---8", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---9", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---0", ResourceUtils.getIcon("folder_close.png")));
		tools.add(new JFToolButton("退出---A", ResourceUtils.getIcon("folder_close.png")));
		
		tools.add(btnExit);
		
		menuFile = new JFMenu("文件");
		muneItemExit = new JFMenuItem("退出");
		
		menuFile.add(muneItemExit);
		menuFile.add(new JFMenuItem("退出---A"));
		
		menuAbout = new JFMenu("关于", ResourceUtils.getIcon("query_16.png"));
		menuAbout.add(new JFMenuItem("关于作者", ResourceUtils.getIcon("query_16.png")));
		menuAbout.add(new JFMenuItem("关于程序", ResourceUtils.getIcon("folder_close.png")));
		menuAbout.add(new JFCheckBoxMenuItem("选中"));
		
		JFMenu menu = new JFMenu("D");
		menu.add(new JFMenuItem("D-1"));
		menu.add(new JFMenuItem("D--2"));
		
		
		btnExit.add(new JFMenuItem("弹出0", ResourceUtils.getIcon("query_16.png")));
		btnExit.add(new JFMenuItem("弹出1"));
		btnExit.add(menu);
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
