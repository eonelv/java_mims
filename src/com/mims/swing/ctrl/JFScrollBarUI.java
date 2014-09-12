/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.mims.swing.ctrl.painter.IScrollBarPainter;
import com.mims.swing.ctrl.painter.JFScrollBarPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-31
 * 
 */
public class JFScrollBarUI extends BasicScrollBarUI
{
	private IScrollBarPainter painter = null;

	private boolean isMouseOver = false;

	public static ComponentUI createUI(JComponent c)
	{
		return new JFScrollBarUI();
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
	
	protected void setThumbRollover(boolean active) {
//        if (thumbActive != active) {
//            thumbActive = active;
//            scrollbar.repaint(getThumbBounds());
//        }
    }

	protected JButton createIncreaseButton(int orientation)
	{
		return new JScrollBarButton(orientation);
	}

	protected JButton createDecreaseButton(int orientation)
	{
		return new JScrollBarButton(orientation);
	}

	public void paint(Graphics g, JComponent c)
	{
		paintTrack(g, c, getTrackBounds());
		Rectangle thumbBounds = getThumbBounds();
		if (thumbBounds.intersects(g.getClipBounds()))
		{
			paintThumb(g, c, thumbBounds);
		}
		c.repaint();
	}

	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
	{
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled())
		{
			return;
		}
		painter.paintThumb(g, c, thumbBounds, isMouseOver, isDragging);
	}

	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
	{
		painter.paintTrack(g, c, trackBounds, trackHighlight);
		if(trackHighlight == DECREASE_HIGHLIGHT)	{
		    paintDecreaseHighlight(g);
		} 
		else if(trackHighlight == INCREASE_HIGHLIGHT)		{
		    paintIncreaseHighlight(g);
		}
	}

	protected TrackListener createTrackListener()
	{
		return new JFTrackListener();
	}

	protected class JFTrackListener extends TrackListener
	{

		public void mouseReleased(MouseEvent e)
		{
			super.mouseReleased(e);
			isMouseOver = false;
		}

		public void mouseEntered(MouseEvent e)
		{
			super.mouseEntered(e);
			isMouseOver = true;
		}

		public void mousePressed(MouseEvent e)
		{
			super.mousePressed(e);
			isMouseOver = false;
		}

		public void mouseDragged(MouseEvent e)
		{
			super.mouseDragged(e);
			isMouseOver = false;
		}

		public void mouseMoved(MouseEvent e)
		{
			super.mouseMoved(e);
		}

		public void mouseExited(MouseEvent e)
		{
			super.mouseExited(e);
			isMouseOver = false;
		}
	}

}
