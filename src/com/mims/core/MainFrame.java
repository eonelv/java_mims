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
 * ����:.<p>
 *
 * @author ���� 
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
	 * �������������
	 */
	private JFTabbedPane mainTablePane = null;
	
	/**
	 * ����ס���
	 */
	private JComponent contentPanel = null;
	
	private JComponent basePanel = null;
	
	/**
	 * ������
	 */
	private JFToolBar appToolBar = null;
	
	/**
	 * �˵���
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
		//����Window�޴�����
//		setUndecorated(true);
		//����Window͸��
//		AWTUtilities.setWindowOpaque(this, false);
	}
	
	/**
	 * 
	 * @������UI��ʼ�����.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-7-23
	 *
	 */
	public void init()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		//���ڴ�ʱ�Ƿ����
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
