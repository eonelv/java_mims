/**
 * 
 */
package com.mims.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.mims.core.log.LogSystem;
import com.mims.swing.dialog.JFDialog;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-7-23
 * 
 */
public class UIFactory
{
	private static Logger logger = LogSystem.getLogger(UIFactory.class);
	
	public static IUIHelper getUI(String uiName, int model, Map uiContext, CoreUI frame)
	{
		return getUI(uiName, model, uiContext, null,frame);
	}
	public static IUIHelper getUI(String uiName, int model, Map uiContext, String title, IUIHelper parent)
	{
		IUIHelper ui = null;
		try
		{
			Class clientClass = Class.forName(uiName);
			try
			{
				Constructor constructor = clientClass.getConstructor(Map.class, String.class);
				ui = (IUIHelper)constructor.newInstance(uiContext, title);
			} catch (SecurityException e)
			{
				logger.error(e.getMessage(), e);
			} catch (NoSuchMethodException e)
			{
				logger.error(e.getMessage(), e);
			} catch (IllegalArgumentException e)
			{
				logger.error(e.getMessage(), e);
			} catch (InvocationTargetException e)
			{
				logger.error(e.getMessage(), e);
			}
			
			if (IUIHelper.UIMODEL_NEWTAB == model)
			{
				return ui;
			}
			else if (IUIHelper.UIMODEL_NEWWIN == model)
			{
				JFrame frame = new JFFrame((CoreUI)ui);
				frame.setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);
			}else if(IUIHelper.UIMODEL_NEWDIA == model){
				JFDialog dialog = new WindowUI((CoreUI)ui);
			}
			return ui;
			
		} catch (ClassNotFoundException e)
		{
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e)
		{
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
		}
		return ui;
	}
	
}
