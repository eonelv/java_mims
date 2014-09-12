/**
 * 
 */
package com.mims.swing.ctrl.win;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.mims.swing.ctrl.IPainterVisitor;
import com.mims.swing.ctrl.JFToolBar;
import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-16
 * 
 */
public class WinToolBar extends JComponent implements IPainterVisitor
{
	private static final long serialVersionUID = 8720606699220945528L;

	private static String uiClassID = "WinToolBarUI";
	
	private IComponentPainter painter = null;
	
	private WinTabbedPane tabbedPane = null;
	
	private int defaultHeight = 119;

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	public WinToolBar(){
		updateUI();
		setPreferredSize(new Dimension(200, defaultHeight));
		add(tabbedPane,BorderLayout.CENTER);
	}
	
//	public void setBounds(int x, int y, int width, int height)
//	{
//		super.setBounds(x, y, width, defaultHeight);
//	}
//	
//	public void setBounds(Rectangle r)
//	{
//		
//		setBounds(r.x, r.y, r.width,defaultHeight);
//	}
	public void add(WinToolBarPanel panel){
		tabbedPane.add(panel.getTitle(), panel);
	}
	
	public void add(String title, JFToolBar bar){
		tabbedPane.add(title, bar);
	}
	
	public void add(WinToolBarResource resource){
		tabbedPane.add(resource.getSource());
	}
	
	public int getDefaultHeight()
	{
		return defaultHeight;
	}

	public void setDefaultHeight(int defaultHeight)
	{
		setPreferredSize(new Dimension(200, defaultHeight));
		this.defaultHeight = defaultHeight;
	}

	public void updateUI() {
        setUI((WinToolBarUI)UIManager.getUI(this));
        invalidate();
    }

    public WinToolBarUI getUI() {
        return (WinToolBarUI)ui;
    }

    public void setUI(WinToolBarUI ui) {
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

	public WinTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

	public void setTabbedPane(WinTabbedPane tabbedPane)
	{
		this.tabbedPane = tabbedPane;
	}
	
	
}
