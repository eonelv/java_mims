/**
 * 
 */
package com.mims.swing.ctrl.painter;

import java.awt.Graphics;
/**
 * 
 * ����:.<p>
 *
 * @author ���� 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface IScrollBarButtonPainter extends IComponentPainter
{

	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled);

}
