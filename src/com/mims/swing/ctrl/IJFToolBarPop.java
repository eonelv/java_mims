package com.mims.swing.ctrl;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public interface IJFToolBarPop
{
	public JPopupMenu getAssButton();
	
	public void add(JMenuItem comp);
	
	public int getPopCount();
}
