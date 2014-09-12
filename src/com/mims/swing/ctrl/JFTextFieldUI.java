/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.Highlighter;

import com.mims.swing.look.JFBorders;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-23
 * 
 */
public class JFTextFieldUI extends BasicTextFieldUI
{

	public static ComponentUI createUI(JComponent c)
	{
		return new JFTextFieldUI();
	}

	protected Highlighter createHighlighter()
	{
		return new BasicHighlighter();
	}
	
	@Override
	public void installUI(JComponent c)
	{
		
		super.installUI(c);
		c.setBackground(new Color(249,249,249));
		c.setBorder(new JFBorders.TextFieldBorder());
	}
	
	@Override
	protected void paintBackground(Graphics g)
	{
		if (!getComponent().isEnabled()){
			getComponent().setOpaque(false);
		}else{
			getComponent().setOpaque(true);
		}
		super.paintBackground(g);
	}
}
