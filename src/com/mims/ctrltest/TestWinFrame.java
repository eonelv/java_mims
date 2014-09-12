/**
 * 
 */
package com.mims.ctrltest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.win.WinLookAndFeel;
import com.mims.swing.ctrl.win.WinToolBar;
import com.mims.swing.layout.LayoutProperty;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class TestWinFrame extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3581343243743695607L;
	
	public TestWinFrame(){
		setBounds(100, 100, 866, 600);
		JFrame.setDefaultLookAndFeelDecorated(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WinToolBar toolBar = new WinToolBar();
		TestToolBarPanel1 toolPanel1 = new TestToolBarPanel1("¿ªÊ¼");
		toolPanel1.setPreferredSize(new Dimension(200, 50));
		toolBar.add(toolPanel1);
		
		TestToolBarPanel2 toolPanel2 = new TestToolBarPanel2("±à¼­");
		toolPanel2.setPreferredSize(new Dimension(200, 50));
		toolBar.add(toolPanel2);

		getContentPane().add(toolBar,BorderLayout.NORTH);
//		add(new JFButton("AAA"),BorderLayout.NORTH);
		
		TestA leftPanel = new TestA(new HashMap(),"a");
		leftPanel.setPreferredSize(new Dimension(800, 500));
//		
//		leftPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
//				new LayoutProperty(0, 0, 200, 600, LayoutProperty.TOP 
//						| LayoutProperty.LEFT | LayoutProperty.BOTTOM));
		
		getContentPane().add(leftPanel,BorderLayout.CENTER);
		setVisible(true);
	}
	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 *
	 * @Date£º2011-9-16
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		try
		{
			UIManager.setLookAndFeel(new WinLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					TestWinFrame app = new TestWinFrame();
					
//					app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
		} 
		catch (InterruptedException e)
		{
//			logger.error("",e);
		} 
		catch (InvocationTargetException e)
		{
//			logger.error("",e);
		} catch (UnsupportedLookAndFeelException e)
		{
//			logger.error("",e);
		}
	}

}
