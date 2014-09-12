/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Graphics;

import javax.swing.JComponent;

import com.mims.swing.ctrl.util.UIDrawer;

/**
 * ����:.<p>
 *
 * @author ���� 
 *
 * @Date: 2011-8-27
 * 
 */
public class JFToolBarPainter extends AbstractComponentPainter
{

	/**
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-8-27
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
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2011-8-27
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
	 * @Date��2011-8-27
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
	
	protected void _paint(Graphics g, JComponent comp, float alpha){
		int w = comp.getWidth();
		int h = comp.getHeight();
		UIDrawer.drawToolBarBackGround(g, w , h, 1.0f);
	}

}
