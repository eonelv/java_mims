/**
 * 
 */
package com.mims.swing.ctrl.win;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.mims.swing.ctrl.IPainterVisitor;
import com.mims.swing.ctrl.JFTabbedPane;
import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.WinToolBarPainter;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinToolBarUI extends ComponentUI
{
	private IComponentPainter painter = null;
	
	private WinTabbedPane tabbedPane = null;
	
	public static ComponentUI createUI(JComponent c)
	{
		return new WinToolBarUI();
	}
	
	public void installUI(JComponent c)
	{
		IPainterVisitor b = (IPainterVisitor) c;
		if (b.getPainter() == null)
		{
			painter = new WinToolBarPainter();
			b.setPainter(painter);
		} else
		{
			painter = (WinToolBarPainter) b.getPainter();
		}
		WinToolBar toolBar = (WinToolBar)c;
		if (toolBar.getTabbedPane() == null){
			tabbedPane = new WinTabbedPane();
			toolBar.setTabbedPane(tabbedPane);
		}else{
			tabbedPane = toolBar.getTabbedPane();
		}
		toolBar.setLayout(new FlexLayout());
		toolBar.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		tabbedPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(0, 0, 900, 600, LayoutProperty.TOP 
						| LayoutProperty.RIGHT|LayoutProperty.LEFT | LayoutProperty.BOTTOM));
	}

	public void paint(Graphics g, JComponent c)
	{
		painter.paintBackGroud(g, c);
		super.paint(g, c);
	}
}
