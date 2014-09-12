/**
 * 
 */
package com.mims.ctrltest;

import java.util.HashMap;
import java.util.Map;

import com.mims.core.CoreUI;
import com.mims.core.IUIHelper;
import com.mims.swing.ctrl.win.WinTabbedPane;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class TestWin extends CoreUI
{
	private WinTabbedPane tabbedPane = null;
	
	public TestWin(Map uiContext, String title)
	{
		super(uiContext, title);
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		tabbedPane = new WinTabbedPane();
		
		IUIHelper tab = new TestA(new HashMap(),"AAAAA");
		IUIHelper tab1 = new TestA(new HashMap(),"BBBBB");
		tabbedPane.add(tab.getTitle(), tab.getUIObject());
		tabbedPane.add(tab1.getTitle(), tab1.getUIObject());
		
		tabbedPane.setSelectedComponent(tab1.getUIObject());
		tabbedPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 580, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT |  LayoutProperty.BOTTOM));
		add(tabbedPane);
	}

}
