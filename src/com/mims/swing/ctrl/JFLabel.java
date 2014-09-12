/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-3
 * 
 */
public class JFLabel extends JLabel implements IPainterVisitor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9115078046936675160L;
	
	private final String uiClassID = "JFLabelUI";
	
	private IComponentPainter painter = null;

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFLabel(String text, Icon icon, int horizontalAlignment) {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }
            
 
    public JFLabel(String text, int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }


    public JFLabel(String text) {
        this(text, null, LEADING);
    }


    public JFLabel(Icon image, int horizontalAlignment) {
        this(null, image, horizontalAlignment);
    }

  
    public JFLabel(Icon image) {
        this(null, image, CENTER);
    }

 
    public JFLabel() {
        this("", null, LEADING);
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
