package com.mims.swing.layout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class FlexLayoutTest extends JFrame {

	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 * 
	 * @Date£º2011-3-12
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FlexLayoutTest frame = new FlexLayoutTest();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setPreferredSize(new Dimension(1000, 600));
		// Container contentPane = frame.getContentPane();
		JComponent contentPane = (JComponent) frame.getContentPane();
		 contentPane.setLayout(new FlexLayout());
		contentPane.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 1000, 600));
		JButton btn1 = new JButton("AAA");
		btn1.setBorder(new JButtonBorder());
		JButton btn2 = new JButton("BBB");
		JButton btn3 = new JButton("CCC");
		JButton btn4 = new JButton("DDD");
		JButton btn5 = new JButton("EEE");
		JButton btn6 = new JButton("FFF");
		JTextArea area = new JTextArea();
		btn1.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 300, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		btn2.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(350, 10, 300, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT_RESIZE
						| LayoutProperty.RIGHT_RESIZE));
		btn3.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(690, 10, 300, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT));

		btn4.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 560, 300, 30, LayoutProperty.BOTTOM
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		btn5.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(350, 560, 300, 30, LayoutProperty.BOTTOM
						| LayoutProperty.LEFT_RESIZE
						| LayoutProperty.RIGHT_RESIZE));
		btn6.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(690, 560, 300, 30, LayoutProperty.BOTTOM
						| LayoutProperty.LEFT_RESIZE | LayoutProperty.RIGHT));

		area.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 52, 980, 498, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT
						| LayoutProperty.BOTTOM));
		area.setBackground(Color.DARK_GRAY);
		area.setForeground(Color.RED);
		frame.add(btn1);
		frame.add(btn2);
		frame.add(btn3);
		frame.add(btn4);
		frame.add(btn5);
		frame.add(btn6);
		frame.add(area);

		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("Exit Application");
		menuItem.setBorder(new JMenuItemBorder());
		menu.add(menuItem);
		
		JMenuItem menuItem1 = new JMenuItem("Exit Application");
		menuItem1.setBorder(new JMenuItemBorder());
		menu.add(menuItem1);
		
		JMenuItem menuItem2 = new JMenuItem("Exit Application");
		menuItem2.setBorder(new JMenuItemBorder());
		menu.add(menuItem2);
		
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		frame.setJMenuBar(bar);

		frame.setVisible(true);
	}

	public static void cFrame(JFrame frame)
	{
		try {   
			    UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");   
			    SwingUtilities.updateComponentTreeUI(frame);   
			} catch (UnsupportedLookAndFeelException ex1) {   
			    System.err.println("Unsupported LookAndFeel: ");   
			} catch (ClassNotFoundException ex2) {   
			   System.err.println("LookAndFeel class not found: " );   
			} catch (InstantiationException ex3) {   
			    System.err.println("Could not load LookAndFeel: "  );   
			} catch (IllegalAccessException ex4) {   
			    System.err.println("Cannot use LookAndFeel: ");   
			}   
	}
}
