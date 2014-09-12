/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreeCellRenderer;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-3
 * 
 */
public class JFTreeUI extends BasicTreeUI
{
	public static ComponentUI createUI(JComponent x)
	{
		return new JFTreeUI();
	}
	
	@Override
	protected TreeCellRenderer createDefaultCellRenderer()
	{
		return new JFTreeCellRenderer();
	}
	
	protected CellRendererPane createCellRendererPane() {
        return new JFCellRendererPane();
    }
	
	@Override
	public void installUI(JComponent c)
	{
		super.installUI(c);
		c.setBackground(SwingConst.MAIN_PANEL_BACKGROUND);
	}
}
