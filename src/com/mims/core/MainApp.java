/**
 * 
 */
package com.mims.core;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mims.app.aim.AimApp;
import com.mims.swing.look.JFLookAndFeel;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-7-23
 * 
 */
public class MainApp
{

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 * 
	 * @Date£º2011-7-23
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			// UIManager.setLookAndFeel(new
			// SubstanceOfficeBlue2007LookAndFeel());
			UIManager.setLookAndFeel(new JFLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					AimApp application = new AimApp();
				}
			});
		} 
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

}
