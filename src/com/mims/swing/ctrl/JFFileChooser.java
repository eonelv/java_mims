/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;

import javax.accessibility.AccessibleContext;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-11
 * 
 */
public class JFFileChooser extends JFileChooser
{
	protected JDialog createDialog(Component parent) throws HeadlessException {
		String title = getUI().getDialogTitle(this);
	        putClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY, 
	                          title);

	        JDialog dialog;
	        Window window = JFOptionPane.getWindowForComponent(parent);
	        if (window instanceof Frame) {
	            dialog = new JDialog((Frame)window, title, true);	
	        } else {
	            dialog = new JDialog((Dialog)window, title, true);
	        }
	        dialog.setComponentOrientation(this.getComponentOrientation());

	        Container contentPane = dialog.getContentPane();
	        contentPane.setLayout(new BorderLayout());
	        contentPane.add(this, BorderLayout.CENTER);
	 
	        if (JDialog.isDefaultLookAndFeelDecorated()) {
	            boolean supportsWindowDecorations = 
	            UIManager.getLookAndFeel().getSupportsWindowDecorations();
	            if (supportsWindowDecorations) {
	                dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
	            }
	        }
	        dialog.pack();
	        dialog.setLocationRelativeTo(parent);

		return dialog;
	    }
}
