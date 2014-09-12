/**
 * 
 */
package com.mims.swing.ctrl;

import java.util.HashMap;
import java.util.Map;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-18
 * 
 */
public class JFTabbedPaneManager
{
	private static JFTabbedPaneManager manager = null;
	
	private Map<String, Object> openedTab = new HashMap<String, Object>();
	
	private JFTabbedPaneManager(){
		
	}
	
	public static JFTabbedPaneManager getManager(){
		if (manager == null){
			manager = new JFTabbedPaneManager();
		}
		return manager;
	}
	
	public Map getOpenedTabs(){
		return openedTab;
	}
}
