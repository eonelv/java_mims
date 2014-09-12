/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-8
 * 
 */
public class JFToolButton extends JButton implements IJFToolBarPop, IPainterVisitor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1601154964152102607L;
	private final String uiClassID = "JFToolButtonUI";
	
	private JFPopupMenu popupMenu = new JFPopupMenu();
	
	private IComponentPainter painter = null;
	
	private int popSize = 0;

//	static
//	{
//		UIManager.getDefaults().put("JFToolButtonUI",
//				"com.mims.swing.ctrl.JFToolButtonUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFToolButton()
	{
		this(null, null);
	}

	/**
	 * Creates a button with an icon.
	 * 
	 * @param icon
	 *            the Icon image to display on the button
	 */
	public JFToolButton(Icon icon)
	{
		this(null, icon);
	}

	/**
	 * Creates a button with text.
	 * 
	 * @param text
	 *            the text of the button
	 */
	public JFToolButton(String text)
	{
		this(text, null);
	}

	/**
	 * Creates a button where properties are taken from the <code>Action</code>
	 * supplied.
	 * 
	 * @param a
	 *            the <code>Action</code> used to specify the new button
	 * 
	 * @since 1.3
	 */
	public JFToolButton(Action a)
	{
		this();
		setAction(a);
	}

	/**
	 * Creates a button with initial text and an icon.
	 * 
	 * @param text
	 *            the text of the button
	 * @param icon
	 *            the Icon image to display on the button
	 */
	public JFToolButton(String text, Icon icon)
	{
		// Create the model
		setModel(new DefaultButtonModel());

		// initialize
		init(text, icon);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI()
	{
		setUI((ButtonUI) UIManager.getUI(this));
	}
	
	protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border != null) {
//            border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }

	public JPopupMenu getAssButton()
	{
		return popupMenu;
	}
	
	public void add(JMenuItem comp)
	{
		popSize++;
		popupMenu.add(comp);
	}
	
	public int getPopCount()
	{
		return popSize;
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
