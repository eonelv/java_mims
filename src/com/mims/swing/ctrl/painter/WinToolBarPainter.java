/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * ����:.<p>
 *
 * @author ���� 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinToolBarPainter extends AbstractComponentPainter
{

	/**
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-9-16
	 *
	 * @param g
	 * @param comp
	 *
	 * @see com.mims.swing.ctrl.painter.IComponentPainter#paintBackGroud(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paintBackGroud(Graphics g, JComponent comp)
	{
		g.fillRoundRect(0, 0, comp.getWidth(), comp.getHeight(), 5, 5);
	}

	/**
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-9-16
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
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-9-16
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

	/**
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-9-16
	 *
	 * @param g
	 * @param comp
	 * @param alpha
	 *
	 * @see com.mims.swing.ctrl.painter.AbstractComponentPainter#_paint(java.awt.Graphics, javax.swing.JComponent, float)
	 */
	@Override
	protected void _paint(Graphics g, JComponent comp, float alpha)
	{
		// TODO Auto-generated method stub

	}

}
