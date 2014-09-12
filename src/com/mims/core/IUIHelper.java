package com.mims.core;

import java.util.List;

import javax.swing.JComponent;

public interface IUIHelper {

	public List getMenu();
	
	public List getTool();
	
	public int getUIModel();
	
	public JComponent getUIObject();
	
	public String getTitle();
	
	public void registMain(MainFrame frame);
	
	public void setTitle(String title);
	
	public void showUI();
	
	public static int UIMODEL_NEWTAB = 0;
	
	public static int UIMODEL_NEWWIN = 1;
	
	public static int UIMODEL_NEWDIA = 2;

}
