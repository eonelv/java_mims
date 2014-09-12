/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.painter.IScrollBarPainter;
import com.mims.swing.ctrl.painter.JFScrollBarPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-3
 * 
 */
public class JFLabelUI extends BasicLabelUI
{
	private IComponentPainter painter = null;
	
	public static ComponentUI createUI(JComponent c) {
        return new JFLabelUI();
    }
	
	public void installUI(JComponent c)
	{
		super.installUI(c);
		IPainterVisitor b = (IPainterVisitor) c;
		if (b.getPainter() == null)
		{
			painter = new JFScrollBarPainter();
			b.setPainter(painter);
		} else
		{
			painter = (IScrollBarPainter) b.getPainter();
		}
	}
}
