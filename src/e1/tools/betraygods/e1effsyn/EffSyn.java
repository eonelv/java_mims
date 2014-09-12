package e1.tools.betraygods.e1effsyn;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mims.swing.look.JFLookAndFeel;


public class EffSyn extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6519940739080499175L;

	public EffSyn()
	{
		super();
		setVisible(true);
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		setExtendedState(JFrame.NORMAL);
		setSize(600, 450);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = tk.getScreenSize().width;
		int height = tk.getScreenSize().height;
		setTitle("EONE 3D Eff Syn");
		setLocation(width / 2 - 400, height / 2 - 300);
		
		this.add(new EffSynUI());
	}
	
	public static void main(String[] args)
	{
		
		try
		{
			UIManager.setLookAndFeel(new JFLookAndFeel());
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					EffSyn app = new EffSyn();
					
					app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		} 
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}

}
