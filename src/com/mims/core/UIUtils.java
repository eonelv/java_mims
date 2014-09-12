/**
 * 
 */
package com.mims.core;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;

import javax.swing.JFileChooser;

import com.mims.swing.ctrl.FileFilter;
import com.mims.swing.ctrl.JFFileChooser;
import com.mims.swing.ctrl.JFOptionPane;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-7-24
 * 
 */
public class UIUtils
{
	public static JFileChooser getFileChooser(int mode, String[] exts,
			String description)
	{

		JFFileChooser fileChooser = null;
		fileChooser = new JFFileChooser();
		fileChooser.setFileSelectionMode(mode);

		FileFilter filter = new FileFilter(exts, description);
		fileChooser.addChoosableFileFilter(filter);

		return fileChooser;
	}

	public static JFileChooser getFileChooser(int mode, String ext,
			String description)
	{
		return getFileChooser(mode, new String[]
		{ ext }, description);
	}

	public static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException
	{
		if (parentComponent == null)
			return null;
		if (parentComponent instanceof Frame
				|| parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return JFOptionPane.getWindowForComponent(parentComponent.getParent());
	}

}
