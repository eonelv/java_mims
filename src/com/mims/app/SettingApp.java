package com.mims.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mims.swing.look.JFLookAndFeel;

public class SettingApp {

	public static void main(String[] args) 
	{
		try
		{
			UIManager.setLookAndFeel(new JFLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					SettingUI frame = new SettingUI();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e)
		{
		} 
		catch (UnsupportedLookAndFeelException e) 
		{
		}
	}

}
