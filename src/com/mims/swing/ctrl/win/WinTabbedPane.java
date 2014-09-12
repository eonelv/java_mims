/**
 * 
 */
package com.mims.swing.ctrl.win;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinTabbedPane extends JTabbedPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8433545487749990310L;

	private final String uiClassID = "WinTabbedPaneUI";

	public String getUIClassID()
	{
		return uiClassID;
	}

	private int preferredUnselectedTabWidth = 80;
	
	public WinTabbedPane()
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
				WinTabbedPane.this.updateTabComponents();
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
		}
	}

	public int getPreferredUnselectedTabWidth()
	{
		return this.preferredUnselectedTabWidth;
	}
	
}