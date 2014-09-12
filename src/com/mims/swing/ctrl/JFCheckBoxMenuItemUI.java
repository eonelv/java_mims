/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-4
 * 
 */
public class JFCheckBoxMenuItemUI extends JFMenuItemUI
{
	protected String getPropertyPrefix()
	{
		return "CheckBoxMenuItem";
	}

	public static ComponentUI createUI(JComponent c)
	{
		return new JFCheckBoxMenuItemUI();
	}

}
