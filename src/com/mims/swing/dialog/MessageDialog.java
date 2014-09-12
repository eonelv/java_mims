/**
 * 
 */
package com.mims.swing.dialog;

import java.awt.Window;

import com.mims.core.CoreUI;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-10
 * 
 */
public class MessageDialog extends JFDialog
{

	private String msg = null;
	
	public MessageDialog(Window frame, CoreUI ui, String msg)
	{
		this(frame, ui);
		setSize(400,300);
		this.msg = msg;
	}
	
	public MessageDialog(Window frame, CoreUI ui)
	{
		super(frame, ui);
	}

	private static final long serialVersionUID = 5422764881007003602L;
	
	public void show(){
		
		setVisible(true);
	}

}
