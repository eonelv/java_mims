/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SpinnerUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.InternationalFormatter;

import com.mims.swing.look.JFBorders;

import sun.swing.DefaultLookup;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-13
 * 
 */
public class JFSpinnerUI extends BasicSpinnerUI
{
	public JFSpinner spinner;
	private Handler handler;

	private static final ArrowButtonHandler nextButtonHandler = new ArrowButtonHandler(
			"increment", true);
	private static final ArrowButtonHandler previousButtonHandler = new ArrowButtonHandler(
			"decrement", false);
	private PropertyChangeListener propertyChangeListener;

	private static final Dimension zeroSize = new Dimension(0, 0);

	public static ComponentUI createUI(JComponent c)
	{
		return new JFSpinnerUI();
	}

	private void maybeAdd(Component c, String s)
	{
		if (c != null)
		{
			spinner.add(c, s);
		}
	}

	/**
	 * Calls <code>installDefaults</code>, <code>installListeners</code>, and
	 * then adds the components returned by <code>createNextButton</code>,
	 * <code>createPreviousButton</code>, and <code>createEditor</code>.
	 * 
	 * @param c
	 *            the JFSpinner
	 * @see #installDefaults
	 * @see #installListeners
	 * @see #createNextButton
	 * @see #createPreviousButton
	 * @see #createEditor
	 */
	public void installUI(JComponent c)
	{
		this.spinner = (JFSpinner) c;
		
		installDefaults();
		installListeners();
		maybeAdd(createNextButton(), "Next");
		maybeAdd(createPreviousButton(), "Previous");
		JComponent editor = createEditor();
		maybeAdd(editor, "Editor");
		updateEnabledState();
		installKeyboardActions();
//		editor.setBorder(new JFBorders.TextFieldBorder());
		spinner.setBorder(new JFBorders.TextFieldBorder());
	}

	/**
	 * Calls <code>uninstallDefaults</code>, <code>uninstallListeners</code>,
	 * and then removes all of the spinners children.
	 * 
	 * @param c
	 *            the JFSpinner (not used)
	 */
	public void uninstallUI(JComponent c)
	{
		uninstallDefaults();
		uninstallListeners();
		this.spinner = null;
		c.removeAll();
	}

	/**
	 * Initializes <code>PropertyChangeListener</code> with a shared object that
	 * delegates interesting PropertyChangeEvents to protected methods.
	 * <p>
	 * This method is called by <code>installUI</code>.
	 * 
	 * @see #replaceEditor
	 * @see #uninstallListeners
	 */
	protected void installListeners()
	{
		propertyChangeListener = createPropertyChangeListener();
		spinner.addPropertyChangeListener(propertyChangeListener);
		if (DefaultLookup.getBoolean(spinner, this,
				"Spinner.disableOnBoundaryValues", false))
		{
			spinner.addChangeListener(getHandler());
		}
		JComponent editor = spinner.getEditor();
		if (editor != null && editor instanceof JFSpinner.DefaultEditor)
		{
			JTextField tf = ((JFSpinner.DefaultEditor) editor).getTextField();
			if (tf != null)
			{
				tf.addFocusListener(nextButtonHandler);
				tf.addFocusListener(previousButtonHandler);
			}
		}
	}

	/**
	 * Removes the <code>PropertyChangeListener</code> added by
	 * installListeners.
	 * <p>
	 * This method is called by <code>uninstallUI</code>.
	 * 
	 * @see #installListeners
	 */
	protected void uninstallListeners()
	{
		spinner.removePropertyChangeListener(propertyChangeListener);
		spinner.removeChangeListener(handler);
		JComponent editor = spinner.getEditor();
		removeEditorBorderListener(editor);
		if (editor instanceof JFSpinner.DefaultEditor)
		{
			JTextField tf = ((JFSpinner.DefaultEditor) editor).getTextField();
			if (tf != null)
			{
				tf.removeFocusListener(nextButtonHandler);
				tf.removeFocusListener(previousButtonHandler);
			}
		}
		propertyChangeListener = null;
		handler = null;
	}

	/**
	 * Initialize the <code>JFSpinner</code> <code>border</code>,
	 * <code>foreground</code>, and <code>background</code>, properties based on
	 * the corresponding "Spinner.*" properties from defaults table. The
	 * <code>JSpinners</code> layout is set to the value returned by
	 * <code>createLayout</code>. This method is called by
	 * <code>installUI</code>.
	 * 
	 * @see #uninstallDefaults
	 * @see #installUI
	 * @see #createLayout
	 * @see LookAndFeel#installBorder
	 * @see LookAndFeel#installColors
	 */
	protected void installDefaults()
	{
		spinner.setLayout(createLayout());
		LookAndFeel.installBorder(spinner, "Spinner.border");
		LookAndFeel.installColorsAndFont(spinner, "Spinner.background",
				"Spinner.foreground", "Spinner.font");
		LookAndFeel.installProperty(spinner, "opaque", Boolean.TRUE);
	}

	/**
	 * Sets the <code>JFSpinner's</code> layout manager to null. This method is
	 * called by <code>uninstallUI</code>.
	 * 
	 * @see #installDefaults
	 * @see #uninstallUI
	 */
	protected void uninstallDefaults()
	{
		spinner.setLayout(null);
	}

	private Handler getHandler()
	{
		if (handler == null)
		{
			handler = new Handler();
		}
		return handler;
	}

	/**
	 * Installs the necessary listeners on the next button, <code>c</code>, to
	 * update the <code>JFSpinner</code> in response to a user gesture.
	 * 
	 * @param c
	 *            Component to install the listeners on
	 * @throws NullPointerException
	 *             if <code>c</code> is null.
	 * @see #createNextButton
	 * @since 1.5
	 */
	protected void installNextButtonListeners(Component c)
	{
		installButtonListeners(c, nextButtonHandler);
	}

	/**
	 * Installs the necessary listeners on the previous button, <code>c</code>,
	 * to update the <code>JFSpinner</code> in response to a user gesture.
	 * 
	 * @param c
	 *            Component to install the listeners on.
	 * @throws NullPointerException
	 *             if <code>c</code> is null.
	 * @see #createPreviousButton
	 * @since 1.5
	 */
	protected void installPreviousButtonListeners(Component c)
	{
		installButtonListeners(c, previousButtonHandler);
	}

	private void installButtonListeners(Component c, ArrowButtonHandler handler)
	{
		if (c instanceof JButton)
		{
			((JButton) c).addActionListener(handler);
		}
		c.addMouseListener(handler);
	}

	/**
	 * Create a <code>LayoutManager</code> that manages the <code>editor</code>,
	 * <code>nextButton</code>, and <code>previousButton</code> children of the
	 * JFSpinner. These three children must be added with a constraint that
	 * identifies their role: "Editor", "Next", and "Previous". The default
	 * layout manager can handle the absence of any of these children.
	 * 
	 * @return a LayoutManager for the editor, next button, and previous button.
	 * @see #createNextButton
	 * @see #createPreviousButton
	 * @see #createEditor
	 */
	protected LayoutManager createLayout()
	{
		return getHandler();
	}

	/**
	 * Create a <code>PropertyChangeListener</code> that can be added to the
	 * JFSpinner itself. Typically, this listener will call replaceEditor when
	 * the "editor" property changes, since it's the <code>SpinnerUI's</code>
	 * responsibility to add the editor to the JFSpinner (and remove the old
	 * one). This method is called by <code>installListeners</code>.
	 * 
	 * @return A PropertyChangeListener for the JFSpinner itself
	 * @see #installListeners
	 */
	protected PropertyChangeListener createPropertyChangeListener()
	{
		return getHandler();
	}

	/**
	 * Create a component that will replace the spinner models value with the
	 * object returned by <code>spinner.getPreviousValue</code>. By default the
	 * <code>previousButton</code> is a JButton. This method invokes
	 * <code>installPreviousButtonListeners</code> to install the necessary
	 * listeners to update the <code>JFSpinner</code>'s model in response to a
	 * user gesture. If a previousButton isn't needed (in a subclass) then
	 * override this method to return null.
	 * 
	 * @return a component that will replace the spinners model with the next
	 *         value in the sequence, or null
	 * @see #installUI
	 * @see #createNextButton
	 * @see #installPreviousButtonListeners
	 */
	protected Component createPreviousButton()
	{
		Component c = createArrowButton(SwingConstants.SOUTH);
		c.setName("Spinner.previousButton");
		installPreviousButtonListeners(c);
		return c;
	}

	/**
	 * Create a component that will replace the spinner models value with the
	 * object returned by <code>spinner.getNextValue</code>. By default the
	 * <code>nextButton</code> is a JButton who's <code>ActionListener</code>
	 * updates it's <code>JFSpinner</code> ancestors model. If a nextButton
	 * isn't needed (in a subclass) then override this method to return null.
	 * 
	 * @return a component that will replace the spinners model with the next
	 *         value in the sequence, or null
	 * @see #installUI
	 * @see #createPreviousButton
	 * @see #installNextButtonListeners
	 */
	protected Component createNextButton()
	{
		Component c = createArrowButton(SwingConstants.NORTH);
		c.setName("Spinner.nextButton");
		installNextButtonListeners(c);
		return c;
	}

	private Component createArrowButton(int direction)
	{
		JButton b = new JFArrowButton(direction);
//		Border buttonBorder = UIManager.getBorder("Spinner.arrowButtonBorder");
//		if (buttonBorder instanceof UIResource)
//		{
//			// Wrap the border to avoid having the UIResource be replaced by
//			// the ButtonUI. This is the opposite of using BorderUIResource.
//			b.setBorder(new CompoundBorder(buttonBorder, null));
//		} else
//		{
//			b.setBorder(buttonBorder);
//		}
		b.setInheritsPopupMenu(true);
		b.setOpaque(true);
		return b;
	}

	/**
	 * This method is called by installUI to get the editor component of the
	 * <code>JFSpinner</code>. By default it just returns
	 * <code>JFSpinner.getEditor()</code>. Subclasses can override
	 * <code>createEditor</code> to return a component that contains the
	 * spinner's editor or null, if they're going to handle adding the editor to
	 * the <code>JFSpinner</code> in an <code>installUI</code> override.
	 * <p>
	 * Typically this method would be overridden to wrap the editor with a
	 * container with a custom border, since one can't assume that the editors
	 * border can be set directly.
	 * <p>
	 * The <code>replaceEditor</code> method is called when the spinners editor
	 * is changed with <code>JFSpinner.setEditor</code>. If you've overriden
	 * this method, then you'll probably want to override
	 * <code>replaceEditor</code> as well.
	 * 
	 * @return the JSpinners editor JComponent, spinner.getEditor() by default
	 * @see #installUI
	 * @see #replaceEditor
	 * @see JFSpinner#getEditor
	 */
	protected JComponent createEditor()
	{
		JComponent editor = spinner.getEditor();
		maybeRemoveEditorBorder(editor);
		installEditorBorderListener(editor);
		editor.setInheritsPopupMenu(true);
		updateEditorAlignment(editor);
		return editor;
	}

	/**
	 * Called by the <code>PropertyChangeListener</code> when the
	 * <code>JFSpinner</code> editor property changes. It's the responsibility
	 * of this method to remove the old editor and add the new one. By default
	 * this operation is just:
	 * 
	 * <pre>
	 * spinner.remove(oldEditor);
	 * spinner.add(newEditor, &quot;Editor&quot;);
	 * </pre>
	 * 
	 * The implementation of <code>replaceEditor</code> should be coordinated
	 * with the <code>createEditor</code> method.
	 * 
	 * @see #createEditor
	 * @see #createPropertyChangeListener
	 */
	protected void replaceEditor(JComponent oldEditor, JComponent newEditor)
	{
		spinner.remove(oldEditor);
		maybeRemoveEditorBorder(newEditor);
		installEditorBorderListener(newEditor);
		newEditor.setInheritsPopupMenu(true);
		updateEditorAlignment(newEditor);
		spinner.add(newEditor, "Editor");
	}

	private void updateEditorAlignment(JComponent editor)
	{
		if (editor instanceof JFSpinner.DefaultEditor)
		{
			// if editor alignment isn't set in LAF, we get 0 (CENTER) here
			int alignment = UIManager.getInt("Spinner.editorAlignment");
			JTextField text = ((JFSpinner.DefaultEditor) editor).getTextField();
			text.setHorizontalAlignment(alignment);
		}
	}

	/**
	 * Remove the border around the inner editor component for LaFs that install
	 * an outside border around the spinner,
	 */
	private void maybeRemoveEditorBorder(JComponent editor)
	{
		if (!UIManager.getBoolean("Spinner.editorBorderPainted"))
		{
			if (editor instanceof JPanel && editor.getBorder() == null
					&& editor.getComponentCount() > 0)
			{

				editor = (JComponent) editor.getComponent(0);
			}

			if (editor != null && editor.getBorder() instanceof UIResource)
			{
				editor.setBorder(null);
			}
		}
	}

	/**
	 * Remove the border around the inner editor component for LaFs that install
	 * an outside border around the spinner,
	 */
	private void installEditorBorderListener(JComponent editor)
	{
		if (!UIManager.getBoolean("Spinner.editorBorderPainted"))
		{
			if (editor instanceof JPanel && editor.getBorder() == null
					&& editor.getComponentCount() > 0)
			{

				editor = (JComponent) editor.getComponent(0);
			}
			if (editor != null
					&& (editor.getBorder() == null || editor.getBorder() instanceof UIResource))
			{
				editor.addPropertyChangeListener(getHandler());
			}
		}
	}

	private void removeEditorBorderListener(JComponent editor)
	{
		if (!UIManager.getBoolean("Spinner.editorBorderPainted"))
		{
			if (editor instanceof JPanel && editor.getComponentCount() > 0)
			{

				editor = (JComponent) editor.getComponent(0);
			}
			if (editor != null)
			{
				editor.removePropertyChangeListener(getHandler());
			}
		}
	}

	/**
	 * Updates the enabled state of the children Components based on the enabled
	 * state of the <code>JFSpinner</code>.
	 */
	private void updateEnabledState()
	{
		updateEnabledState(spinner, spinner.isEnabled());
	}

	/**
	 * Recursively updates the enabled state of the child <code>Component</code>
	 * s of <code>c</code>.
	 */
	private void updateEnabledState(Container c, boolean enabled)
	{
		for (int counter = c.getComponentCount() - 1; counter >= 0; counter--)
		{
			Component child = c.getComponent(counter);

			if (DefaultLookup.getBoolean(spinner, this,
					"Spinner.disableOnBoundaryValues", false))
			{
				SpinnerModel model = spinner.getModel();
				if (child.getName() == "Spinner.nextButton"
						&& model.getNextValue() == null)
				{
					child.setEnabled(false);
				} else if (child.getName() == "Spinner.previousButton"
						&& model.getPreviousValue() == null)
				{
					child.setEnabled(false);
				} else
				{
					child.setEnabled(enabled);
				}
			} else
			{
				child.setEnabled(enabled);
			}
			if (child instanceof Container)
			{
				updateEnabledState((Container) child, enabled);
			}
		}
	}

	/**
	 * Installs the keyboard Actions onto the JFSpinner.
	 * 
	 * @since 1.5
	 */
	protected void installKeyboardActions()
	{
		InputMap iMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		SwingUtilities.replaceUIInputMap(spinner,
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, iMap);

		LazyActionMap.installLazyActionMap(spinner, BasicSpinnerUI.class,
				"Spinner.actionMap");
	}

	/**
	 * Returns the InputMap to install for <code>condition</code>.
	 */
	private InputMap getInputMap(int condition)
	{
		if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
		{
			return (InputMap) DefaultLookup.get(spinner, this,
					"Spinner.ancestorInputMap");
		}
		return null;
	}

	static void loadActionMap(LazyActionMap map)
	{
		map.put("increment", nextButtonHandler);
		map.put("decrement", previousButtonHandler);
	}

	/**
	 * Returns the baseline.
	 * 
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 * @throws IllegalArgumentException
	 *             {@inheritDoc}
	 * @see javax.swing.JComponent#getBaseline(int, int)
	 * @since 1.6
	 */
	public int getBaseline(JComponent c, int width, int height)
	{
		super.getBaseline(c, width, height);
		JComponent editor = spinner.getEditor();
		Insets insets = spinner.getInsets();
		width = width - insets.left - insets.right;
		height = height - insets.top - insets.bottom;
		if (width >= 0 && height >= 0)
		{
			int baseline = editor.getBaseline(width, height);
			if (baseline >= 0)
			{
				return insets.top + baseline;
			}
		}
		return -1;
	}

	/**
	 * Returns an enum indicating how the baseline of the component changes as
	 * the size changes.
	 * 
	 * @throws NullPointerException
	 *             {@inheritDoc}
	 * @see javax.swing.JComponent#getBaseline(int, int)
	 * @since 1.6
	 */
	public Component.BaselineResizeBehavior getBaselineResizeBehavior(
			JComponent c)
	{
		super.getBaselineResizeBehavior(c);
		return spinner.getEditor().getBaselineResizeBehavior();
	}

	/**
	 * A handler for spinner arrow button mouse and action events. When a left
	 * mouse pressed event occurs we look up the (enabled) spinner that's the
	 * source of the event and start the autorepeat timer. The timer fires
	 * action events until any button is released at which point the timer is
	 * stopped and the reference to the spinner cleared. The timer doesn't start
	 * until after a 300ms delay, so often the source of the initial (and final)
	 * action event is just the button logic for mouse released - which means
	 * that we're relying on the fact that our mouse listener runs after the
	 * buttons mouse listener.
	 * <p>
	 * Note that one instance of this handler is shared by all slider previous
	 * arrow buttons and likewise for all of the next buttons, so it doesn't
	 * have any state that persists beyond the limits of a single button
	 * pressed/released gesture.
	 */
	private static class ArrowButtonHandler extends AbstractAction implements
			FocusListener, MouseListener, UIResource
	{
		final javax.swing.Timer autoRepeatTimer;
		final boolean isNext;
		JFSpinner spinner1 = null;
		JButton arrowButton = null;

		ArrowButtonHandler(String name, boolean isNext)
		{
			super(name);
			this.isNext = isNext;
			autoRepeatTimer = new javax.swing.Timer(60, this);
			autoRepeatTimer.setInitialDelay(300);
		}

		private JFSpinner eventToSpinner(AWTEvent e)
		{
			Object src = e.getSource();
			while ((src instanceof Component) && !(src instanceof JFSpinner))
			{
				src = ((Component) src).getParent();
			}
			return (src instanceof JFSpinner) ? (JFSpinner) src : null;
		}

		public void actionPerformed(ActionEvent e)
		{
			JFSpinner spinner = this.spinner1;

			if (!(e.getSource() instanceof javax.swing.Timer))
			{
				// Most likely resulting from being in ActionMap.
				spinner = eventToSpinner(e);
				if (e.getSource() instanceof JButton)
				{
					arrowButton = (JButton) e.getSource();
				}
			} else
			{
				if (arrowButton != null && !arrowButton.getModel().isPressed()
						&& autoRepeatTimer.isRunning())
				{
					autoRepeatTimer.stop();
					spinner = null;
					arrowButton = null;
				}
			}
			if (spinner != null)
			{
				try
				{
					int calendarField = getCalendarField(spinner);
					spinner.commitEdit();
					if (calendarField != -1)
					{
						((SpinnerDateModel) spinner.getModel())
								.setCalendarField(calendarField);
					}
					Object value = (isNext) ? spinner.getNextValue() : spinner
							.getPreviousValue();
					if (value != null)
					{
						spinner.setValue(value);
						select(spinner);
					}
				} catch (IllegalArgumentException iae)
				{
					UIManager.getLookAndFeel().provideErrorFeedback(spinner);
				} catch (ParseException pe)
				{
					UIManager.getLookAndFeel().provideErrorFeedback(spinner);
				}
			}
		}

		/**
		 * If the spinner's editor is a DateEditor, this selects the field
		 * associated with the value that is being incremented.
		 */
		private void select(JFSpinner spinner)
		{
			JComponent editor = spinner.getEditor();

			if (editor instanceof JFSpinner.DateEditor)
			{
				JFSpinner.DateEditor dateEditor = (JFSpinner.DateEditor) editor;
				JFormattedTextField ftf = dateEditor.getTextField();
				Format format = dateEditor.getFormat();
				Object value;

				if (format != null && (value = spinner.getValue()) != null)
				{
					SpinnerDateModel model = dateEditor.getModel();
					DateFormat.Field field = DateFormat.Field
							.ofCalendarField(model.getCalendarField());

					if (field != null)
					{
						try
						{
							AttributedCharacterIterator iterator = format
									.formatToCharacterIterator(value);
							if (!select(ftf, iterator, field)
									&& field == DateFormat.Field.HOUR0)
							{
								select(ftf, iterator, DateFormat.Field.HOUR1);
							}
						} catch (IllegalArgumentException iae)
						{
						}
					}
				}
			}
		}

		/**
		 * Selects the passed in field, returning true if it is found, false
		 * otherwise.
		 */
		private boolean select(JFormattedTextField ftf,
				AttributedCharacterIterator iterator, DateFormat.Field field)
		{
			int max = ftf.getDocument().getLength();

			iterator.first();
			do
			{
				Map attrs = iterator.getAttributes();

				if (attrs != null && attrs.containsKey(field))
				{
					int start = iterator.getRunStart(field);
					int end = iterator.getRunLimit(field);

					if (start != -1 && end != -1 && start <= max && end <= max)
					{
						ftf.select(start, end);
					}
					return true;
				}
			} while (iterator.next() != CharacterIterator.DONE);
			return false;
		}

		/**
		 * Returns the calendarField under the start of the selection, or -1 if
		 * there is no valid calendar field under the selection (or the spinner
		 * isn't editing dates.
		 */
		private int getCalendarField(JFSpinner spinner)
		{
			JComponent editor = spinner.getEditor();

			if (editor instanceof JFSpinner.DateEditor)
			{
				JFSpinner.DateEditor dateEditor = (JFSpinner.DateEditor) editor;
				JFormattedTextField ftf = dateEditor.getTextField();
				int start = ftf.getSelectionStart();
				JFormattedTextField.AbstractFormatter formatter = ftf
						.getFormatter();

				if (formatter instanceof InternationalFormatter)
				{
					Format.Field[] fields = ((InternationalFormatter) formatter)
							.getFields(start);

					for (int counter = 0; counter < fields.length; counter++)
					{
						if (fields[counter] instanceof DateFormat.Field)
						{
							int calendarField;

							if (fields[counter] == DateFormat.Field.HOUR1)
							{
								calendarField = Calendar.HOUR;
							} else
							{
								calendarField = ((DateFormat.Field) fields[counter])
										.getCalendarField();
							}
							if (calendarField != -1)
							{
								return calendarField;
							}
						}
					}
				}
			}
			return -1;
		}

		public void mousePressed(MouseEvent e)
		{
			if (SwingUtilities.isLeftMouseButton(e)
					&& e.getComponent().isEnabled())
			{
				spinner1 = eventToSpinner(e);
				autoRepeatTimer.start();

				focusSpinnerIfNecessary();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			autoRepeatTimer.stop();
			arrowButton = null;
			spinner1 = null;
		}

		public void mouseClicked(MouseEvent e)
		{
		}

		public void mouseEntered(MouseEvent e)
		{
			if (spinner1 != null && !autoRepeatTimer.isRunning()
					&& spinner1 == eventToSpinner(e))
			{
				autoRepeatTimer.start();
			}
		}

		public void mouseExited(MouseEvent e)
		{
			if (autoRepeatTimer.isRunning())
			{
				autoRepeatTimer.stop();
			}
		}

		/**
		 * Requests focus on a child of the spinner if the spinner doesn't have
		 * focus.
		 */
		private void focusSpinnerIfNecessary()
		{
			Component fo = KeyboardFocusManager
					.getCurrentKeyboardFocusManager().getFocusOwner();
			if (spinner1.isRequestFocusEnabled()
					&& (fo == null || !SwingUtilities.isDescendingFrom(fo,
							spinner1)))
			{
				Container root = spinner1;

				if (!root.isFocusCycleRoot())
				{
					root = root.getFocusCycleRootAncestor();
				}
				if (root != null)
				{
					FocusTraversalPolicy ftp = root.getFocusTraversalPolicy();
					Component child = ftp.getComponentAfter(root, spinner1);

					if (child != null
							&& SwingUtilities.isDescendingFrom(child, spinner1))
					{
						child.requestFocus();
					}
				}
			}
		}

		public void focusGained(FocusEvent e)
		{
			if (e.getComponent().getParent() != null && e.getComponent().getParent().getParent() != null){
				e.getComponent().getParent().getParent().repaint();
			}
		}

		public void focusLost(FocusEvent e)
		{
			if (spinner1 == eventToSpinner(e))
			{
				if (autoRepeatTimer.isRunning())
				{
					autoRepeatTimer.stop();
				}
				spinner1 = null;
				if (arrowButton != null)
				{
					ButtonModel model = arrowButton.getModel();
					model.setPressed(false);
					model.setArmed(false);
					arrowButton = null;
				}
			}
		}
	}

	private static class Handler implements LayoutManager,
			PropertyChangeListener, ChangeListener
	{
		//
		// LayoutManager
		//
		private Component nextButton = null;
		private Component previousButton = null;
		private Component editor = null;

		public void addLayoutComponent(String name, Component c)
		{
			if ("Next".equals(name))
			{
				nextButton = c;
			} else if ("Previous".equals(name))
			{
				previousButton = c;
			} else if ("Editor".equals(name))
			{
				editor = c;
			}
		}

		public void removeLayoutComponent(Component c)
		{
			if (c == nextButton)
			{
				c = null;
			} else if (c == previousButton)
			{
				previousButton = null;
			} else if (c == editor)
			{
				editor = null;
			}
		}

		private Dimension preferredSize(Component c)
		{
			return (c == null) ? zeroSize : c.getPreferredSize();
		}

		public Dimension preferredLayoutSize(Container parent)
		{
			Dimension nextD = preferredSize(nextButton);
			Dimension previousD = preferredSize(previousButton);
			Dimension editorD = preferredSize(editor);

			/*
			 * Force the editors height to be a multiple of 2
			 */
			editorD.height = ((editorD.height + 1) / 2) * 2;

			Dimension size = new Dimension(editorD.width, editorD.height);
			size.width += Math.max(nextD.width, previousD.width);
			Insets insets = parent.getInsets();
			size.width += insets.left + insets.right;
			size.height += insets.top + insets.bottom;
			return size;
		}

		public Dimension minimumLayoutSize(Container parent)
		{
			return preferredLayoutSize(parent);
		}

		private void setBounds(Component c, int x, int y, int width, int height)
		{
			if (c != null)
			{
				c.setBounds(x, y, width, height);
			}
		}

		public void layoutContainer(Container parent)
		{
			int width = parent.getWidth();
			int height = parent.getHeight();

			Insets insets = parent.getInsets();
			Dimension nextD = preferredSize(nextButton);
			Dimension previousD = preferredSize(previousButton);
			int buttonsWidth = Math.max(nextD.width, previousD.width);
			int editorHeight = height - (insets.top + insets.bottom);

			// The arrowButtonInsets value is used instead of the JFSpinner's
			// insets if not null. Defining this to be (0, 0, 0, 0) causes the
			// buttons to be aligned with the outer edge of the spinner's
			// border, and leaving it as "null" places the buttons completely
			// inside the spinner's border.
			Insets buttonInsets = UIManager
					.getInsets("Spinner.arrowButtonInsets");
			if (buttonInsets == null)
			{
				buttonInsets = insets;
			}

			/*
			 * Deal with the spinner's componentOrientation property.
			 */
			int editorX, editorWidth, buttonsX;
			if (parent.getComponentOrientation().isLeftToRight())
			{
				editorX = insets.left;
				editorWidth = width - insets.left - buttonsWidth
						- buttonInsets.right;
				buttonsX = width - buttonsWidth - buttonInsets.right;
			} else
			{
				buttonsX = buttonInsets.left;
				editorX = buttonsX + buttonsWidth;
				editorWidth = width - buttonInsets.left - buttonsWidth
						- insets.right;
			}

			int nextY = buttonInsets.top;
			int nextHeight = (height / 2) + (height % 2) - nextY;
			int previousY = buttonInsets.top + nextHeight;
			int previousHeight = height - previousY - buttonInsets.bottom;

			setBounds(editor, editorX, insets.top, editorWidth, editorHeight - 1);
			setBounds(nextButton, buttonsX - 1, nextY + 1, buttonsWidth - 1, nextHeight - 1);
			setBounds(previousButton, buttonsX - 1, previousY - 1, buttonsWidth - 1,
					previousHeight - 1);
		}

		//
		// PropertyChangeListener
		//
		public void propertyChange(PropertyChangeEvent e)
		{
			String propertyName = e.getPropertyName();
			if (e.getSource() instanceof JFSpinner)
			{
				JFSpinner spinner = (JFSpinner) (e.getSource());
				SpinnerUI spinnerUI = spinner.getUI();

				if (spinnerUI instanceof JFSpinnerUI)
				{
					JFSpinnerUI ui = (JFSpinnerUI) spinnerUI;

					if ("editor".equals(propertyName))
					{
						JComponent oldEditor = (JComponent) e.getOldValue();
						JComponent newEditor = (JComponent) e.getNewValue();
						ui.replaceEditor(oldEditor, newEditor);
						ui.updateEnabledState();
						if (oldEditor instanceof JFSpinner.DefaultEditor)
						{
							JTextField tf = ((JFSpinner.DefaultEditor) oldEditor)
									.getTextField();
							if (tf != null)
							{
								tf.removeFocusListener(nextButtonHandler);
								tf.removeFocusListener(previousButtonHandler);
							}
						}
						if (newEditor instanceof JFSpinner.DefaultEditor)
						{
							JTextField tf = ((JFSpinner.DefaultEditor) newEditor)
									.getTextField();
							if (tf != null)
							{
								if (tf.getFont() instanceof UIResource)
								{
									tf.setFont(spinner.getFont());
								}
								tf.addFocusListener(nextButtonHandler);
								tf.addFocusListener(previousButtonHandler);
							}
						}
						if (newEditor instanceof JFSpinner.DefaultEditor)
						{
							JTextField tf = ((JFSpinner.DefaultEditor) newEditor)
									.getTextField();
							if (tf != null)
							{
								if (tf.getFont() instanceof UIResource)
								{
									tf.setFont(spinner.getFont());
								}
							}
						}
					} else if ("enabled".equals(propertyName)
							|| "model".equals(propertyName))
					{
						ui.updateEnabledState();
					} else if ("font".equals(propertyName))
					{
						JComponent editor = spinner.getEditor();
						if (editor != null
								&& editor instanceof JFSpinner.DefaultEditor)
						{
							JTextField tf = ((JFSpinner.DefaultEditor) editor)
									.getTextField();
							if (tf != null)
							{
								if (tf.getFont() instanceof UIResource)
								{
									tf.setFont(spinner.getFont());
								}
							}
						}
					} else if (JComponent.TOOL_TIP_TEXT_KEY
							.equals(propertyName))
					{
						updateToolTipTextForChildren(spinner);
					}
				}
			} else if (e.getSource() instanceof JComponent)
			{
				JComponent c = (JComponent) e.getSource();
				if ((c.getParent() instanceof JPanel)
						&& (c.getParent().getParent() instanceof JFSpinner)
						&& "border".equals(propertyName))
				{

					JFSpinner spinner = (JFSpinner) c.getParent().getParent();
					SpinnerUI spinnerUI = spinner.getUI();
					if (spinnerUI instanceof JFSpinnerUI)
					{
						JFSpinnerUI ui = (JFSpinnerUI) spinnerUI;
						ui.maybeRemoveEditorBorder(c);
					}
				}
			}
		}

		// Syncronizes the ToolTip text for the components within the spinner
		// to be the same value as the spinner ToolTip text.
		private void updateToolTipTextForChildren(JComponent spinner)
		{
			String toolTipText = spinner.getToolTipText();
			Component[] children = spinner.getComponents();
			for (int i = 0; i < children.length; i++)
			{
				if (children[i] instanceof JFSpinner.DefaultEditor)
				{
					JTextField tf = ((JFSpinner.DefaultEditor) children[i])
							.getTextField();
					if (tf != null)
					{
						tf.setToolTipText(toolTipText);
					}
				} else if (children[i] instanceof JComponent)
				{
					((JComponent) children[i]).setToolTipText(spinner
							.getToolTipText());
				}
			}
		}

		public void stateChanged(ChangeEvent e)
		{
			if (e.getSource() instanceof JFSpinner)
			{
				JFSpinner spinner = (JFSpinner) e.getSource();
				SpinnerUI spinnerUI = spinner.getUI();
				if (DefaultLookup.getBoolean(spinner, spinnerUI,
						"Spinner.disableOnBoundaryValues", false)
						&& spinnerUI instanceof JFSpinnerUI)
				{
					JFSpinnerUI ui = (JFSpinnerUI) spinnerUI;
					ui.updateEnabledState();
				}
			}
		}
	}
}
