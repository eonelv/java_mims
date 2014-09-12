/**
 * 
 */
package com.mims.swing.ctrl.win;

import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.layout.BarLayout;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinToolBarPanel extends JFPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4013520260856864026L;
	private String title = null;
	
	public WinToolBarPanel(){
		this("WinToolBarPanel");
	}
	
	public WinToolBarPanel(String title){
		setLayout(new BarLayout());
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	
}
