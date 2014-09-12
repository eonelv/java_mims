/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.mims.swing.ctrl.SwingConst;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-9-2
 * 
 */
public class JFPanelPainter extends AbstractComponentPainter
{

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-2
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintBackGroud(Graphics g, JComponent comp)
	{
		paint(g, comp, 1.0f);
	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-2
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintOverBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintOverBackGroud(Graphics g, JComponent comp)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍş
	 *
	 * @Date£º2011-9-2
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintPressBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintPressBackGroud(Graphics g, JComponent comp)
	{
		// TODO Auto-generated method stub

	}
	
	protected void _paint(Graphics g, JComponent comp,float alpha){
		int w = comp.getWidth();
		int h = comp.getHeight();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;
		
		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		RenderingHints renderHints = g2.getRenderingHints();
		
		g2.setRenderingHints(rh);
		g2.setColor(SwingConst.MAIN_PANEL_BACKGROUND);
		g2.fillRect(0, 0, w, h);
		
		
		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}

}
