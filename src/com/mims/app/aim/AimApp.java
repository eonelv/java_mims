package com.mims.app.aim;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.mims.app.test.Test1;
import com.mims.app.test.TestA;
import com.mims.core.MainFrame;
import com.mims.core.log.LogSystem;
import com.mims.swing.look.JFLookAndFeel;

public class AimApp extends MainFrame
{
	
	private static Logger logger = LogSystem.getLogger(AimApp.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6519940739080499175L;

	public AimApp()
	{
		super();
		setVisible(true);
		
	}
	
	public void init()
	{
		// TODO Auto-generated method stub
		super.init();
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		setExtendedState(JFrame.NORMAL);
		setSize(800, 600);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = tk.getScreenSize().width;
		int height = tk.getScreenSize().height;
		setTitle("AIM");
		setLocation(width / 2 - 400, height / 2 - 300);
	}
	
	protected List getTabs()
	{
		List tabs = new ArrayList();
		tabs.add(new AimUI(new HashMap()));
		tabs.add(new Test1(new HashMap(),"Test---1"));
//		tabs.add(new Test1(new HashMap(),"Test---2"));
//		tabs.add(new Test1(new HashMap(),"Test---3"));
//		tabs.add(new Test1(new HashMap(),"Test---4"));
//		tabs.add(new Test1(new HashMap(),"Test---5"));
//		tabs.add(new Test1(new HashMap(),"Test---6"));
//		
//		tabs.add(new Test1(new HashMap(),"Test---7"));
//		tabs.add(new Test1(new HashMap(),"Test---8"));
//		tabs.add(new Test1(new HashMap(),"Test---9"));
//		
//		tabs.add(new Test1(new HashMap(),"Test---10"));
//		tabs.add(new Test1(new HashMap(),"Test---11"));
//		tabs.add(new Test1(new HashMap(),"Test---12"));
//		
//		tabs.add(new Test1(new HashMap(),"Test---13"));
//		tabs.add(new Test1(new HashMap(),"Test---14"));
//		tabs.add(new Test1(new HashMap(),"Test---15"));
		tabs.add(new TestA(new HashMap(),"¿Ø¼þ²âÊÔ"));
		
		return tabs;
	}
	
	protected boolean isMain()
	{
		return true;
	}
	
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
			UIManager.setLookAndFeel(new JFLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					AimApp app = new AimApp();
					
					app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
		} 
		catch (InterruptedException e)
		{
			logger.error("",e);
		} 
		catch (InvocationTargetException e)
		{
			logger.error("",e);
		} catch (UnsupportedLookAndFeelException e)
		{
			logger.error("",e);
		}
	}
}
