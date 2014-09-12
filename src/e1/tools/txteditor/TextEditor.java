package e1.tools.txteditor;

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

import com.mims.core.MainFrame;
import com.mims.core.log.LogSystem;
import com.mims.swing.look.JFLookAndFeel;

public class TextEditor extends MainFrame
{

	private static Logger logger = LogSystem.getLogger(TextEditor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6519940739080499175L;

	public TextEditor()
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
		setTitle("EONE Text Editor");
		setLocation(width / 2 - 400, height / 2 - 300);

	}
	
	protected List getTabs()
	{
		List tabs = new ArrayList();
		tabs.add(new MenuUI(new HashMap(), "Text Editor"));
		
		return tabs;
	}
	
	protected boolean isMain()
	{
		return true;
	}
	
	/**
	 * @������.<p>
	 * 
	 * @author ����
	 * 
	 * @Date��2011-7-23
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
					TextEditor app = new TextEditor();
					
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
