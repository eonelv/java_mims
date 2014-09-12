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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.mims.swing.ctrl.painter.IComponentPainter;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-14
 * 
 */
public class JFButton extends JButton implements UIResource,IPainterVisitor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3148504014941934121L;
	
	private IComponentPainter painter = null;
	
	private final String uiClassID = "JFButtonUI";

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFButton()
	{
		this(null, null);
	}

	/**
	 * Creates a button with an icon.
	 * 
	 * @param icon
	 *            the Icon image to display on the button
	 */
	public JFButton(Icon icon)
	{
		this(null, icon);
	}

	/**
	 * Creates a button with text.
	 * 
	 * @param text
	 *            the text of the button
	 */
	public JFButton(String text)
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
	public JFButton(Action a)
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
	public JFButton(String text, Icon icon)
	{
		// Create the model
		setModel(new DefaultButtonModel());

		// initialize
		init(text, icon);
		
//		setIcon(SwingConst.default_icon);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI()
	{
		setUI((JFButtonUI) UIManager.getUI(this));
	}
	
	protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border != null) {
//            border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
		updateUI();
	}
	
	
}
