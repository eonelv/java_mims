/**
 * 
 */
package com.mims.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;

import javax.swing.JDialog;

import com.mims.core.IUIHelper;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-10
 * 
 */
public class JFDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -269944260005452938L;

	public JFDialog(Window frame ,IUIHelper ui){
		super(frame, Dialog.ModalityType.APPLICATION_MODAL);
		add(ui.getUIObject(), BorderLayout.CENTER);
	}
}
