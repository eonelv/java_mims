/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface IComponentPainter
{
	public void paintBackGroud(Graphics g, JComponent comp);
	
	public void paintOverBackGroud(Graphics g, JComponent comp);
	
	public void paintPressBackGroud(Graphics g, JComponent comp);
}
