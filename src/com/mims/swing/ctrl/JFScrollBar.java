/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Rectangle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-31
 * 
 */
public class JFScrollBar extends JScrollBar implements IPainterVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1254655149939273312L;

	private final String uiClassID = "JFScrollBarUI";

	private IComponentPainter painter;

	private boolean unitIncrementSet;

	private boolean blockIncrementSet;

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFScrollBar(int orientation, int value, int extent, int min, int max)
	{
		super(orientation, value, extent, min, max);
	}

	/**
	 * Creates a scrollbar with the specified orientation and the following
	 * initial values:
	 * 
	 * <pre>
	 * minimum = 0 
	 * maximum = 100 
	 * value = 0
	 * extent = 10
	 * </pre>
	 */
	public JFScrollBar(int orientation)
	{
		super(orientation, 0, 10, 0, 100);
		putClientProperty("JScrollBar.fastWheelScrolling", Boolean.TRUE);
	}

	/**
	 * Creates a vertical scrollbar with the following initial values:
	 * 
	 * <pre>
	 * minimum = 0 
	 * maximum = 100 
	 * value = 0
	 * extent = 10
	 * </pre>
	 */
	public JFScrollBar()
	{
		super(VERTICAL);
	}

	/**
	 * Messages super to set the value, and resets the
	 * <code>unitIncrementSet</code> instance variable to true.
	 * 
	 * @param unitIncrement
	 *            the new unit increment value, in pixels
	 */
	public void setUnitIncrement(int unitIncrement)
	{
		unitIncrementSet = true;
		this.putClientProperty("JScrollBar.fastWheelScrolling", null);
		super.setUnitIncrement(unitIncrement);
	}

	/**
	 * Computes the unit increment for scrolling if the viewport's view is a
	 * <code>Scrollable</code> object. Otherwise return
	 * <code>super.getUnitIncrement</code>.
	 * 
	 * @param direction
	 *            less than zero to scroll up/left, greater than zero for
	 *            down/right
	 * @return an integer, in pixels, containing the unit increment
	 * @see Scrollable#getScrollableUnitIncrement
	 */
	public int getUnitIncrement(int direction)
	{
		JViewport vp = getViewport();
		if (!unitIncrementSet && (vp != null)
				&& (vp.getView() instanceof Scrollable))
		{
			Scrollable view = (Scrollable) (vp.getView());
			Rectangle vr = vp.getViewRect();
			return view.getScrollableUnitIncrement(vr, getOrientation(),
					direction);
		} else
		{
			return super.getUnitIncrement(direction);
		}
	}

	/**
	 * Messages super to set the value, and resets the
	 * <code>blockIncrementSet</code> instance variable to true.
	 * 
	 * @param blockIncrement
	 *            the new block increment value, in pixels
	 */
	public void setBlockIncrement(int blockIncrement)
	{
		blockIncrementSet = true;
		this.putClientProperty("JScrollBar.fastWheelScrolling", null);
		super.setBlockIncrement(blockIncrement);
	}

	/**
	 * Computes the block increment for scrolling if the viewport's view is a
	 * <code>Scrollable</code> object. Otherwise the <code>blockIncrement</code>
	 * equals the viewport's width or height. If there's no viewport return
	 * <code>super.getBlockIncrement</code>.
	 * 
	 * @param direction
	 *            less than zero to scroll up/left, greater than zero for
	 *            down/right
	 * @return an integer, in pixels, containing the block increment
	 * @see Scrollable#getScrollableBlockIncrement
	 */
	public int getBlockIncrement(int direction)
	{
		JViewport vp = getViewport();
		if (blockIncrementSet || vp == null)
		{
			return super.getBlockIncrement(direction);
		} else if (vp.getView() instanceof Scrollable)
		{
			Scrollable view = (Scrollable) (vp.getView());
			Rectangle vr = vp.getViewRect();
			return view.getScrollableBlockIncrement(vr, getOrientation(),
					direction);
		} else if (getOrientation() == VERTICAL)
		{
			return vp.getExtentSize().height;
		} else
		{
			return vp.getExtentSize().width;
		}
	}

	private JViewport getViewport()
	{
		if (getParent() != null && getParent() instanceof JScrollPane)
		{
			return ((JScrollPane) getParent()).getViewport();
		}
		return null;
	}

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}

}
