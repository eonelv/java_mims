/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-16
 * 
 */
public class JFSeparator extends JComponent implements IPainterVisitor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4968877535668613297L;
	
	private static String uiClassID = "JFSeparatorUI";
	
	private IComponentPainter painter = null;
	
	private int direction = HORIZTONTAL;
	
	public static final int HORIZTONTAL = 0;
	
	public static final int VERTICAL = 1;

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	public JFSeparator(){
		updateUI();
	}
	
	public void updateUI() {
        setUI((JFSeparatorUI)UIManager.getUI(this));
        invalidate();
    }

    public JFSeparatorUI getUI() {
        return (JFSeparatorUI)ui;
    }

    public void setUI(JFSeparatorUI ui) {
        super.setUI(ui);
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
