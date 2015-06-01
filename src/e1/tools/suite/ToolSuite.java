package e1.tools.suite;

import java.awt.Font;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;

import com.mims.core.CoreUI;
import com.mims.core.MainFrame;
import com.mims.core.log.LogSystem;
import com.mims.swing.look.JFLookAndFeel;

public class ToolSuite extends MainFrame
{

	private static Logger logger = LogSystem.getLogger(ToolSuite.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6519940739080499175L;

	public ToolSuite()
	{
		super();
		setVisible(true);
	}
	
	public void init()
	{
		super.init();
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		setExtendedState(JFrame.NORMAL);
		setSize(900, 688);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = tk.getScreenSize().width;
		int height = tk.getScreenSize().height;
		setTitle("客户端工具集");
		setLocation(width / 2 - 450, height / 2 - 344);
	}
	
	protected List getTabs()
	{
		List<CoreUI> tabs = new ArrayList<CoreUI>();
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
			if (!SystemParams.loadConfig())
			{
				logger.error("SYSTEM::config load error");
				return;
			}
			
			UIManager.setLookAndFeel(new JFLookAndFeel());
			Font font = new Font(SystemParams.FONT_NAME, Font.PLAIN, 12);
			FontUIResource fontRes = new FontUIResource(font);
		    for (Enumeration<Object> keys = UIManager.getDefaults().keys();keys.hasMoreElements();)
		    {
		    	Object key = keys.nextElement();
		    	Object value = UIManager.get(key);
		    	
		    	if (value instanceof FontUIResource) 
		    	{
		    		UIManager.put(key, fontRes);
		    	}
		    }
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					ToolSuite app = new ToolSuite();
					
					app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
		} 
		catch (InterruptedException e)
		{
			logger.error(e.getMessage(), e);
		} 
		catch (InvocationTargetException e)
		{
			logger.error(e.getMessage(), e);
		} catch (UnsupportedLookAndFeelException e)
		{
			logger.error(e.getMessage(), e);
		}
	}

}
