/**
 * 
 */
package com.mims.swing.ctrl.win;

import javax.swing.UIDefaults;

import com.mims.swing.look.JFLookAndFeel;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinLookAndFeel extends JFLookAndFeel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8022752952920775646L;

	protected void initClassDefaults(UIDefaults table)
    {
		super.initClassDefaults(table);
		final String jfPackageName = "com.mims.swing.ctrl.win.";
		Object[] uiDefaults = {
                "WinTabbedPaneUI", jfPackageName + "WinTabbedPaneUI",
                "WinToolBarUI",jfPackageName +  "WinToolBarUI"
        };
		 table.putDefaults(uiDefaults);
    }
}
