/**
 * 
 */
package com.mims.core;

import javax.swing.JDialog;
import javax.swing.JFrame;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-10
 * 
 */
public class JFFrame extends MainFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3911527050797639479L;
	
	
	
	public JFFrame()
	{
		this(null);
	}
	public JFFrame(IUIHelper ui)
	{
		super(ui);
		
		setSize(500, 400);
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		setLocationRelativeTo(ui.getUIObject());
	}
	
	protected boolean isMain()
	{
		return false;
	}
	
}
