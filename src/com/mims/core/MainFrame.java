/**
 * 
 */
package com.mims.core;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mims.swing.ctrl.JFMenuBar;
import com.mims.swing.ctrl.JFTabbedPane;
import com.mims.swing.ctrl.JFToolBar;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-7-22
 * 
 */
public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 程序主面板容器
	 */
	private JFTabbedPane mainTablePane = null;
	
	/**
	 * 程序住面板
	 */
	private JComponent contentPanel = null;
	
	private JComponent basePanel = null;
	
	/**
	 * 工具栏
	 */
	private JFToolBar appToolBar = null;
	
	/**
	 * 菜单栏
	 */
	private JFMenuBar appMenuBar = null;
	
	private IUIHelper ui = null;
	
	public MainFrame()
	{
		this(null);
	}
	public MainFrame(IUIHelper ui)
	{
		this.ui = ui;
		init();
		//设置Window无窗口栏
//		setUndecorated(true);
		//设置Window透明
//		AWTUtilities.setWindowOpaque(this, false);
	}
	
	/**
	 * 
	 * @描述：UI初始化入口.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-7-23
	 *
	 */
	public void init()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		//窗口打开时是否最大化
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(900, 600);
		
		initCtrl();
		initAction();
		changeMenuAndTool();
		
	}
	
	private void initCtrl()
	{
		contentPanel = (JComponent)getContentPane();
		basePanel = new JPanel();
		mainTablePane = new JFTabbedPane();
		mainTablePane.setMainFrame(true);
		
		appToolBar = new JFToolBar();
		appToolBar.setFloatable(false);
		
		appMenuBar = new JFMenuBar();
		
		setJMenuBar(appMenuBar);
		if (isMain())
		{
			setBaseTab();
			contentPanel.add(mainTablePane,BorderLayout.CENTER);
		}
		else
		{
			contentPanel.add(ui.getUIObject(),BorderLayout.CENTER);
		}

		contentPanel.add(appToolBar, BorderLayout.NORTH);
	}
	
	private void changeMenuAndTool()
	{
		IUIHelper uiHelper = null;
		if (isMain())
		{
			uiHelper = (IUIHelper)mainTablePane.getSelectedComponent();
		}
		else
		{
			uiHelper = ui;
		}
		List menus = uiHelper.getMenu();
		List tools = uiHelper.getTool();
		
		appToolBar.removeAll();
		appMenuBar.removeAll();
		appToolBar.repaint();
		if (tools != null && !tools.isEmpty())
		{
			for (int i = 0; i< tools.size(); i++)
			{
				appToolBar.add((JComponent)tools.get(i));
			}
		}
		if (menus != null && !menus.isEmpty())
		{
			for (Object menu : menus)
			{
				appMenuBar.add((JMenu)menu);
			}
		}
		
	}
	
	private void initAction()
	{
		mainTablePane.addChangeListener(new ChangeListener()
		{
			
			public void stateChanged(ChangeEvent e)
			{
				changeMenuAndTool();
			}
		});
	}

	public JComponent getBasePanel()
	{
		return basePanel;
	}

	public void setBasePanel(JComponent basePanel)
	{
		this.basePanel = basePanel;
	}
	
	private void setBaseTab()
	{
		List tabs = getTabs();
		if (tabs != null && !tabs.isEmpty())
		{
			for (Object temp : tabs)
			{
				IUIHelper tab = (IUIHelper)temp;
				tab.registMain(this);
				addTab(tab);
//				mainTablePane.add(tab.getTitle(),tab.getUIObject());
			}
			
		}
	}
	
	
	
	public JTabbedPane getMainTablePane()
	{
		return mainTablePane;
	}

	public void setMainTablePane(JFTabbedPane mainTablePane)
	{
		this.mainTablePane = mainTablePane;
	}
	
	public void addTab(IUIHelper tab)
	{
		mainTablePane.add(tab.getTitle(),tab.getUIObject());
		mainTablePane.setSelectedComponent(tab.getUIObject());
	}

	protected List getTabs()
	{
		return null;
	}
	
//	protected CoreUI getUI()
//	{
//		return ui;
//	}
	
	protected boolean isMain()
	{
		return false;
	}
}
