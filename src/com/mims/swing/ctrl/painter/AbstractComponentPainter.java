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

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public abstract class AbstractComponentPainter implements IComponentPainter
{

	private Color old = null;
	private Composite composite = null;
	private Paint painter = null;
	private RenderingHints renderHints = null;
	
	protected abstract void _paint(Graphics g, JComponent comp, float alpha);

	
	protected void getDefaults(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		old = g2.getColor();
		composite = g2.getComposite();
		painter = g2.getPaint();
		renderHints = g2.getRenderingHints();
	}
	
	protected void setDefaults(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}
	
	protected void paint(Graphics g, JComponent comp, float alpha)
	{
		getDefaults(g);
		_paint(g, comp, alpha);
		setDefaults(g);
		
	}

}
