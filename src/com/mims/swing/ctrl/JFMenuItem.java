/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-9
 * 
 */
public class JFMenuItem extends JMenuItem implements IPainterVisitor
{
	
	private IComponentPainter painter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -176896060528155749L;
	private final String uiClassID = "JFMenuItemUI";

//	static
//	{
//		UIManager.getDefaults().put("JFMenuItemUI",
//				"com.mims.swing.ctrl.JFMenuItemUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFMenuItem()
	{
		this(null, (Icon) null);
	}

	/**
	 * Creates a <code>JMenuItem</code> with the specified icon.
	 * 
	 * @param icon
	 *            the icon of the <code>JMenuItem</code>
	 */
	public JFMenuItem(Icon icon)
	{
		this(null, icon);
	}

	/**
	 * Creates a <code>JMenuItem</code> with the specified text.
	 * 
	 * @param text
	 *            the text of the <code>JMenuItem</code>
	 */
	public JFMenuItem(String text)
	{
		this(text, (Icon) null);
	}

	/**
	 * Creates a menu item whose properties are taken from the specified
	 * <code>Action</code>.
	 * 
	 * @param a
	 *            the action of the <code>JMenuItem</code>
	 * @since 1.3
	 */
	public JFMenuItem(Action a)
	{
		this();
		setAction(a);
	}

	/**
	 * Creates a <code>JMenuItem</code> with the specified text and icon.
	 * 
	 * @param text
	 *            the text of the <code>JMenuItem</code>
	 * @param icon
	 *            the icon of the <code>JMenuItem</code>
	 */
	public JFMenuItem(String text, Icon icon)
	{
		super(text, icon);
	}

	/**
	 * Creates a <code>JMenuItem</code> with the specified text and keyboard
	 * mnemonic.
	 * 
	 * @param text
	 *            the text of the <code>JMenuItem</code>
	 * @param mnemonic
	 *            the keyboard mnemonic for the <code>JMenuItem</code>
	 */
	public JFMenuItem(String text, int mnemonic)
	{
		super(text, mnemonic);
	}
	
	@Override
	public Border getBorder()
	{
		return null;
	}

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}
}
