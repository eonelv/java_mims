/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.JFPanelPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-2
 * 
 */
public class JFPanelUI extends BasicPanelUI
{
	private static PanelUI panelUI;
	
	private IComponentPainter painter = null;

	public static ComponentUI createUI(JComponent c)
	{
		if (panelUI == null)
		{
			panelUI = new JFPanelUI();
		}
		return panelUI;
	}
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		IPainterVisitor b = (IPainterVisitor)c;
		if (b.getPainter() == null){
			painter = new JFPanelPainter();
			b.setPainter(painter);
		}else{
			painter = b.getPainter();
		}
	}
	
	public void paint(Graphics g, JComponent c) {
		
		painter.paintBackGroud(g, c);
    }
}
