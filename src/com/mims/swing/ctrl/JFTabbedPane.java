/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-16
 * 
 */
public class JFTabbedPane extends JTabbedPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2094533927809368351L;

	private final String uiClassID = "JFTabbedPaneUI";

	private boolean isMainFrame = false;

//	static
//	{
//		UIManager.getDefaults().put("JFTabbedPaneUI",
//				"com.mims.swing.ctrl.JFTabbedPaneUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	private int preferredUnselectedTabWidth = 80;
	
	public JFTabbedPane()
	{
		init();
	}

	private void init()
	{
		setBorder(null);
		setFocusable(false);
		setTabLayoutPolicy(1);
		setOpaque(false);

		addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				JFTabbedPane.this.updateTabComponents();
			}
		});
	}

	public void addTab(String title, Component component)
	{
		super.addTab(title, component);
		int index = getTabCount() - 1;
		JComponent tabComponent = (JComponent) component;
		setSelectedComponent(tabComponent);
	}

	public int getPreferredTabHeight()
	{
		return 80;
	}

	private void updateTabComponents()
	{
		int selectedIndex = getSelectedIndex();
		for (int i = 0; i < getTabCount(); i++)
		{
			Component c = getTabComponentAt(i);
//			if ((c instanceof FreeTabComponent))
//			{
//				FreeTabComponent component = (FreeTabComponent) c;
//				boolean selected = selectedIndex == i;
//				component.updateSelection(selected);
//			}
		}
	}

	public int getPreferredUnselectedTabWidth()
	{
		return this.preferredUnselectedTabWidth;
	}

	public boolean isMainFrame()
	{
		return isMainFrame;
	}

	public void setMainFrame(boolean isMainFrame)
	{
		this.isMainFrame = isMainFrame;
	}
	
}
