/**
 * 
 */
package com.mims.swing.ctrl;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-26
 * 
 */
public interface IPainterVisitor
{
	public IComponentPainter getPainter();
	
	public void setPainter(IComponentPainter painter);
}
