/**
 * 
 */
package com.mims.app.test;

import java.util.HashMap;
import java.util.Map;

import com.mims.app.aim.AimProcessUI;
import com.mims.app.aim.AimUI;
import com.mims.swing.ctrl.JFTabbedPane;
import com.mims.swing.layout.LayoutProperty;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-20
 * 
 */
public class Test1 extends AimProcessUI
{
	
	public Test1(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
	}

	private void initCtrl()
	{
		JFTabbedPane pane = new JFTabbedPane();
		
		AimProcessUI ui = new AimProcessUI(new HashMap(),"AAA");
		pane.add(ui.getTitle(), ui);
		
		AimUI ui1 = new AimUI(new HashMap());
		pane.add("1111", new Test2(new HashMap(),"AAA-1"));
		
		
		
		pane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 880, 560, LayoutProperty.TOP 
						| LayoutProperty.RIGHT | LayoutProperty.BOTTOM | LayoutProperty.LEFT));
		add(pane);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -2402630463130890689L;

}
