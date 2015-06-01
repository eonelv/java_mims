package com.mims.app.buildpath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFList;
import com.mims.swing.ctrl.JFMenu;
import com.mims.swing.ctrl.JFMenuBar;
import com.mims.swing.ctrl.JFMenuItem;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFLookAndFeel;

public class BuildClassPath extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField field = null;

	private JFButton selectBtn = null;
	
	private JButton addBtn = null;

	private JFButton submitBtn = null;
	
	private JList listDirs = null;

	private JFileChooser fileChooser = null;

	private String[] args = null;
	
	private Set<String> dirSet = new HashSet<String>();
	
	private DefaultListModel model = null;
	
	private JFMenuBar bar = null;

	public BuildClassPath(String[] args) {
		this.args = args;
		bar = new JFMenuBar();
		
		setJMenuBar(bar);
		JFMenu menu = new JFMenu("閫夋嫨");
		bar.add(menu);
		menu.add(new JFMenuItem("閫夋嫨"));
		
		field = new JFTextField();
		field.setEditable(false);
//		selectBtn = new JButton("选锟斤拷");
		selectBtn = new JFButton("閫夋嫨");
		
		addBtn = new JFButton("娣诲姞");
		submitBtn = new JFButton("鐢熸垚");
		listDirs = new JFList();
		model = new DefaultListModel();
		listDirs.setModel(model);
		fileChooser = new JFileChooser();
		setSize(500, 400);

		JPanel contentPanel = (JPanel) getContentPane();
		contentPanel.setLayout(new FlexLayout());
		contentPanel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 400, 400));

		field.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 280, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		selectBtn.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(310, 10, 80, 30, LayoutProperty.TOP
						| LayoutProperty.RIGHT));
		addBtn.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(310, 52, 80, 30, LayoutProperty.TOP
						| LayoutProperty.RIGHT));
		submitBtn.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(310, 340, 80, 30, LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		
		listDirs.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 52, 280, 340, LayoutProperty.TOP | LayoutProperty.BOTTOM
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		contentPanel.add(field);
		contentPanel.add(selectBtn);
		contentPanel.add(addBtn);
		contentPanel.add(submitBtn);
		contentPanel.add(listDirs);
		initActions();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public BuildClassPath() {
		this(null);
	}

	private void initActions() {
		submitBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				submitActionPerformanced(e);
			}
		});
		selectBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				selectActionPerformanced(e);
			}
		});
		addBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addActionPerformanced(e);
			}
		});
	}

	private void addActionPerformanced(ActionEvent e) {
		String text = field.getText();
		if (text != null && !dirSet.contains(text))
		{
			dirSet.add(text);
			model.addElement(text);
		}
	}
	private void selectActionPerformanced(ActionEvent e) {
		fileChooser.setFileSelectionMode(1);// 锟借定只锟斤拷选锟斤拷锟侥硷拷锟斤拷
		int state = fileChooser.showOpenDialog(null);// 锟剿撅拷锟角达拷锟侥硷拷选锟斤拷锟斤拷锟斤拷锟斤拷拇锟斤拷锟斤拷锟斤拷
		if (state == 1) {
			return;// 锟斤拷锟斤拷锟津返伙拷
		} else {
			File file = fileChooser.getSelectedFile();// f为选锟今到碉拷目录
			field.setText(file.getAbsolutePath());
		}

	}

	private void submitActionPerformanced(ActionEvent e) {
//		String filePath = field.getText();
		if (dirSet == null || dirSet.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"锟斤拷选锟斤拷一锟斤拷目录", "锟斤拷锟斤拷bat", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		StringBuffer classpath = new StringBuffer();
		classpath.append("set classpath=%classpath%;");
		for(String filePath : dirSet)
		{
			File dir = new File(filePath);
			String[] files = null;
			
			if (dir.isDirectory()) {
				files = dir.list(new FilenameFilter() {

					public boolean accept(File dir, String name) {
						if (name.endsWith(".jar")) {
							return true;
						}
						return false;
					}
				});
			}
//			classpath.append("set CLASSPATH_HOME=");
//			classpath.append(filePath);
//			classpath.append("\r\n");
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					classpath.append(filePath);
					classpath.append('\\');
					classpath.append(files[i]);
					classpath.append(';');
				}
			}
		}
		wirteFile(".", classpath.toString());
	}

	private void wirteFile(String path, String content) {
		String filename = null;
		if (args != null && args.length != 0) {
			filename = path + "\\" + args[0] + ".bat";
		} else {
			filename = path + "\\set_class_env.bat";
		}
		File file = new File(filename);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.close();
			JOptionPane.showMessageDialog(null,
					"锟斤拷锟斤拷锟斤拷锟�", "锟斤拷锟斤拷bat", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					e, "锟斤拷锟斤拷bat", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					e, "锟斤拷锟斤拷bat", JOptionPane.ERROR_MESSAGE);
		}

	}


	/**
	 * @锟斤拷锟斤拷锟斤拷.<p>
	 * 
	 * @author 锟斤拷锟斤拷
	 * 
	 * @Date锟斤拷2011-4-3
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
			UIManager.setLookAndFeel(new JFLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					BuildClassPath frame = new BuildClassPath();
					frame.setVisible(true);
				}
			});
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
