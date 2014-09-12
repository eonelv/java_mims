/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-2
 * 
 */
public class JFPanel extends JPanel implements IPainterVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5910456658242224997L;

	private IComponentPainter painter = null;

	private final String uiClassID = "JFPanelUI";

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
	}
	
	public JFPanel(boolean isDoubleBuffered) {
        this(new FlowLayout(), isDoubleBuffered);
    }

    public JFPanel() {
        this(true);
    }

    

	public JFPanel(LayoutManager layout)
	{
		this(layout, true);
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
