/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-14
 * 
 */
public class JFTabbedPaneButton extends JButton implements UIResource
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3148504014941934121L;
	
	private final String uiClassID = "JFTabbedPaneButtonUI";
	
//	static
//	{
//		UIManager.getDefaults().put("JFTabbedPaneButtonUI",
//				"com.mims.swing.ctrl.JFTabbedPaneButtonUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFTabbedPaneButton()
	{
		this(null, null);
	}

	public JFTabbedPaneButton(Icon icon)
	{
		this(null, icon);
	}

	public JFTabbedPaneButton(String text)
	{
		this(text, null);
	}

	public JFTabbedPaneButton(Action a)
	{
		this();
		setAction(a);
	}

	public JFTabbedPaneButton(String text, Icon icon)
	{
		setModel(new DefaultButtonModel());

		init(text, icon);
		
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI()
	{
		setUI((JFTabbedPaneButtonUI) UIManager.getUI(this));
	}
	
	protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border != null) {
//            border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(30,30);
	}
}
