/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface IScrollBarPainter extends IComponentPainter
{
//	public void paintBackGroud(Graphics g, JComponent comp, Rectangle rect);
//	
//	public void paintOverBackGroud(Graphics g, JComponent comp, Rectangle rect);
//	
//	public void paintPressBackGroud(Graphics g, JComponent comp, Rectangle rect);
	
	public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds, 
			boolean isMouseOver,  boolean isDraged);
	
	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds, int highLight);
}
