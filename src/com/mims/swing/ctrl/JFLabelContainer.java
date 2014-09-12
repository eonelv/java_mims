/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-22
 * 
 */
public class JFLabelContainer extends JPanel
{

	private static final String uiClassID = "JFLabelContainerUI";

	public String getUIClassID()
	{
		return uiClassID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6489814391311627930L;

	private JComponent editor = null;

	private String text;

	private int textLength = 80;

	private boolean lineVisiable = true;

	public JFLabelContainer()
	{
		this(null, null);
	}

	public JFLabelContainer(JComponent editor, String text)
	{
//		setLayout(null);
		if (editor != null)
		{
			setSize(270, editor.getHeight());
			add(editor);
		} else
		{
			setSize(270, 19);
		}
		this.editor = editor;
		this.text = text;
	}

	public JFLabelContainer(String text)
	{
		this(null, text);
	}

	public JFLabelContainer(JComponent editor)
	{
		this(editor, null);
	}
	
	public JComponent getEditor()
	{
		return editor;
	}

	public void setEditor(JComponent editor)
	{
		this.editor = editor;
		add(editor);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getTextLength()
	{
		return textLength;
	}

	public void setTextLength(int textLength)
	{
		this.textLength = textLength;
	}

	public boolean isLineVisiable()
	{
		return lineVisiable;
	}

	public void setLineVisiable(boolean lineVisiable)
	{
		this.lineVisiable = lineVisiable;
	}

	
}
