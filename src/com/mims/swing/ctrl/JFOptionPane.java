/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-10
 * 
 */
public class JFOptionPane extends JOptionPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6441155304643441151L;

	private final String uiClassID = "JFOptionPaneUI";

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFOptionPane(Object message, int messageType, int optionType,
			Icon icon, Object[] options, Object initialValue)
	{
		super(message, messageType, optionType, icon, options, initialValue);
	}

	public static void showMessageDialog(Component parentComponent,
			Object message) throws HeadlessException
	{
		showMessageDialog(parentComponent, message, "mandy",
				INFORMATION_MESSAGE);
	}

	public static void showMessageDialog(Component parentComponent,
			Object message, String title, int messageType)
			throws HeadlessException
	{
		showMessageDialog(parentComponent, message, title, messageType, null);
	}

	public static void showMessageDialog(Component parentComponent,
			Object message, String title, int messageType, Icon icon)
			throws HeadlessException
	{
		showOptionDialog(parentComponent, message, title, DEFAULT_OPTION,
				messageType, icon, null, null);
	}

	public static int showOptionDialog(Component parentComponent,
			Object message, String title, int optionType, int messageType,
			Icon icon, Object[] options, Object initialValue)
			throws HeadlessException
	{
		JFOptionPane pane = new JFOptionPane(message, messageType, optionType,
				icon, options, initialValue);

		pane.setInitialValue(initialValue);
		pane.setComponentOrientation(((parentComponent == null) ? getRootFrame()
				: parentComponent).getComponentOrientation());

		int style = styleFromMessageType(messageType);
		JDialog dialog = pane.createDialog(parentComponent, title, style);

		pane.selectInitialValue();
		dialog.show();
		dialog.dispose();

		Object selectedValue = pane.getValue();

		if (selectedValue == null)
			return CLOSED_OPTION;
		if (options == null)
		{
			if (selectedValue instanceof Integer)
				return ((Integer) selectedValue).intValue();
			return CLOSED_OPTION;
		}
		for (int counter = 0, maxCounter = options.length; counter < maxCounter; counter++)
		{
			if (options[counter].equals(selectedValue))
				return counter;
		}
		return CLOSED_OPTION;
	}

	public static int showConfirmDialog(Component parentComponent,
			Object message) throws HeadlessException
	{
		return showConfirmDialog(parentComponent, message,
				UIManager.getString("OptionPane.titleText"),
				YES_NO_CANCEL_OPTION);
	}

	public static int showConfirmDialog(Component parentComponent,
			Object message, String title, int optionType)
			throws HeadlessException
	{
		return showConfirmDialog(parentComponent, message, title, optionType,
				QUESTION_MESSAGE);
	}

	public static int showConfirmDialog(Component parentComponent,
			Object message, String title, int optionType, int messageType)
			throws HeadlessException
	{
		return showConfirmDialog(parentComponent, message, title, optionType,
				messageType, null);
	}

	public static int showConfirmDialog(Component parentComponent,
			Object message, String title, int optionType, int messageType,
			Icon icon) throws HeadlessException
	{
		return showOptionDialog(parentComponent, message, title, optionType,
				messageType, icon, null, null);
	}

	public static String showInputDialog(Object message)
			throws HeadlessException
	{
		return showInputDialog(null, message);
	}
	
	public static String showInputDialog(Component parentComponent,
	        Object message) throws HeadlessException { 
	        return showInputDialog(parentComponent, message, "", QUESTION_MESSAGE);
	    }
	
	public static String showInputDialog(Component parentComponent,
	        Object message, String title, int messageType) 
	        throws HeadlessException {
	        return (String)showInputDialog(parentComponent, message, title,
	                                       messageType, null, null, null);
	    }
	
	 public static Object showInputDialog(Component parentComponent,
		        Object message, String title, int messageType, Icon icon,
		        Object[] selectionValues, Object initialSelectionValue) 
		        throws HeadlessException {
		        JFOptionPane    pane = new JFOptionPane(message, messageType,
		                                              OK_CANCEL_OPTION, icon,
		                                              null, null);

		        pane.setWantsInput(true);
		        pane.setSelectionValues(selectionValues);
		        pane.setInitialSelectionValue(initialSelectionValue);
		        pane.setComponentOrientation(((parentComponent == null) ?
			    getRootFrame() : parentComponent).getComponentOrientation());

		        int style = styleFromMessageType(messageType);
		        JDialog dialog = pane.createDialog(parentComponent, title, style);

		        pane.selectInitialValue();
		        dialog.show();
		        dialog.dispose();

		        Object value = pane.getInputValue();

		        if (value == UNINITIALIZED_VALUE) {
		            return null;
		        }
		        return value;
		    }

	public static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException
	{
		if (parentComponent == null)
			return getRootFrame();
		if (parentComponent instanceof Frame
				|| parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return JFOptionPane.getWindowForComponent(parentComponent.getParent());
	}

	private JDialog createDialog(Component parentComponent, String title,
			int style) throws HeadlessException
	{

		final JDialog dialog;

		Window window = JFOptionPane.getWindowForComponent(parentComponent);
		if (window instanceof Frame)
		{
			dialog = new JDialog((Frame) window, title, true);
		} else
		{
			dialog = new JDialog((Dialog) window, title, true);
		}
		// if (window instanceof SwingUtilities.SharedOwnerFrame)
		// {
		// WindowListener ownerShutdownListener = (WindowListener)
		// SwingUtilities
		// .getSharedOwnerFrameShutdownListener();
		// dialog.addWindowListener(ownerShutdownListener);
		// }
		initDialog(dialog, style, parentComponent);
		return dialog;
	}

	private void initDialog(final JDialog dialog, int style,
			Component parentComponent)
	{
		dialog.setComponentOrientation(this.getComponentOrientation());
		Container contentPane = dialog.getContentPane();

		contentPane.setLayout(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);
		dialog.setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated())
		{
			boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations)
			{
				dialog.setUndecorated(true);
				getRootPane().setWindowDecorationStyle(style);
			}
		}
		dialog.pack();
		dialog.setLocationRelativeTo(parentComponent);
		WindowAdapter adapter = new WindowAdapter()
		{
			private boolean gotFocus = false;

			public void windowClosing(WindowEvent we)
			{
				setValue(null);
			}

			public void windowGainedFocus(WindowEvent we)
			{
				// Once window gets focus, set initial focus
				if (!gotFocus)
				{
					selectInitialValue();
					gotFocus = true;
				}
			}
		};
		dialog.addWindowListener(adapter);
		dialog.addWindowFocusListener(adapter);
		dialog.addComponentListener(new ComponentAdapter()
		{
			public void componentShown(ComponentEvent ce)
			{
				// reset value to ensure closing works properly
				setValue(JOptionPane.UNINITIALIZED_VALUE);
			}
		});
		addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent event)
			{
				// Let the defaultCloseOperation handle the closing
				// if the user closed the window without selecting a button
				// (newValue = null in that case). Otherwise, close the dialog.
				if (dialog.isVisible()
						&& event.getSource() == JFOptionPane.this
						&& (event.getPropertyName().equals(VALUE_PROPERTY))
						&& event.getNewValue() != null
						&& event.getNewValue() != JFOptionPane.UNINITIALIZED_VALUE)
				{
					dialog.setVisible(false);
				}
			}
		});
	}

	private static int styleFromMessageType(int messageType)
	{
		switch (messageType)
		{
		case ERROR_MESSAGE:
			return JRootPane.ERROR_DIALOG;
		case QUESTION_MESSAGE:
			return JRootPane.QUESTION_DIALOG;
		case WARNING_MESSAGE:
			return JRootPane.WARNING_DIALOG;
		case INFORMATION_MESSAGE:
			return JRootPane.INFORMATION_DIALOG;
		case PLAIN_MESSAGE:
		default:
			return JRootPane.PLAIN_DIALOG;
		}
	}
}
