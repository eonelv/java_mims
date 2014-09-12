/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-13
 * 
 */
public class JFSpinner extends JSpinner
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4083989618009846942L;

	private static final String uiClassID = "JFSpinnerUI";

	private static final Action DISABLED_ACTION = new DisabledAction();

	public JFSpinner(SpinnerModel model)
	{
		super(model);
		setEditor(createEditor(model));
		updateUI();
	}

	public JFSpinner()
	{
		this(new SpinnerNumberModel());
	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	protected JComponent createEditor(SpinnerModel model)
	{
		if (model instanceof SpinnerDateModel)
		{
			return new DateEditor(this);
		} else if (model instanceof SpinnerListModel)
		{
			return new ListEditor(this);
		} else if (model instanceof SpinnerNumberModel)
		{
			return new NumberEditor(this);
		} else
		{
			return new DefaultEditor(this);
		}
	}

	public boolean hasFocus() {
		
		Object temp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		
		boolean hasFocus = false;
		hasFocus = temp == this;

		JTextField editor = null;
		if (getEditor() instanceof JFSpinner.NumberEditor ){
			editor = ((JFSpinner.DefaultEditor) getEditor()).getTextField();
		}

		if (editor != null && ! hasFocus){
			if (editor.hasFocus())
			{
				return editor.hasFocus();
			}
		}
        return hasFocus;
    }
	
	
	public static class DefaultEditor extends JPanel implements ChangeListener,
			PropertyChangeListener, LayoutManager
	{
		/**
		 * Constructs an editor component for the specified
		 * <code>JSpinner</code>. This <code>DefaultEditor</code> is it's own
		 * layout manager and it is added to the spinner's
		 * <code>ChangeListener</code> list. The constructor creates a single
		 * <code>JFormattedTextField</code> child, initializes it's value to be
		 * the spinner model's current value and adds it to <code>this</code>
		 * <code>DefaultEditor</code>.
		 * 
		 * @param spinner
		 *            the spinner whose model <code>this</code> editor will
		 *            monitor
		 * @see #getTextField
		 * @see JSpinner#addChangeListener
		 */
		public DefaultEditor(JSpinner spinner)
		{
			super(null);

			JFormattedTextField ftf = new JFormattedTextField();
			ftf.setName("Spinner.formattedTextField");
			ftf.setValue(spinner.getValue());
			ftf.addPropertyChangeListener(this);
			ftf.setEditable(false);
			ftf.setInheritsPopupMenu(true);

			String toolTipText = spinner.getToolTipText();
			if (toolTipText != null)
			{
				ftf.setToolTipText(toolTipText);
			}

			add(ftf);

			setLayout(this);
			spinner.addChangeListener(this);

			// We want the spinner's increment/decrement actions to be
			// active vs those of the JFormattedTextField. As such we
			// put disabled actions in the JFormattedTextField's actionmap.
			// A binding to a disabled action is treated as a nonexistant
			// binding.
			ActionMap ftfMap = ftf.getActionMap();

			if (ftfMap != null)
			{
				ftfMap.put("increment", DISABLED_ACTION);
				ftfMap.put("decrement", DISABLED_ACTION);
			}
		}

		/**
		 * Disconnect <code>this</code> editor from the specified
		 * <code>JSpinner</code>. By default, this method removes itself from
		 * the spinners <code>ChangeListener</code> list.
		 * 
		 * @param spinner
		 *            the <code>JSpinner</code> to disconnect this editor from;
		 *            the same spinner as was passed to the constructor.
		 */
		public void dismiss(JSpinner spinner)
		{
			spinner.removeChangeListener(this);
		}

		/**
		 * Returns the <code>JSpinner</code> ancestor of this editor or
		 * <code>null</code> if none of the ancestors are a
		 * <code>JSpinner</code>. Typically the editor's parent is a
		 * <code>JSpinner</code> however subclasses of <code>JSpinner</code> may
		 * override the the <code>createEditor</code> method and insert one or
		 * more containers between the <code>JSpinner</code> and it's editor.
		 * 
		 * @return <code>JSpinner</code> ancestor; <code>null</code> if none of
		 *         the ancestors are a <code>JSpinner</code>
		 * 
		 * @see JSpinner#createEditor
		 */
		public JSpinner getSpinner()
		{
			for (Component c = this; c != null; c = c.getParent())
			{
				if (c instanceof JSpinner)
				{
					return (JSpinner) c;
				}
			}
			return null;
		}

		/**
		 * Returns the <code>JFormattedTextField</code> child of this editor. By
		 * default the text field is the first and only child of editor.
		 * 
		 * @return the <code>JFormattedTextField</code> that gives the user
		 *         access to the <code>SpinnerDateModel's</code> value.
		 * @see #getSpinner
		 * @see #getModel
		 */
		public JFormattedTextField getTextField()
		{
			return (JFormattedTextField) getComponent(0);
		}

		/**
		 * This method is called when the spinner's model's state changes. It
		 * sets the <code>value</code> of the text field to the current value of
		 * the spinners model.
		 * 
		 * @param e
		 *            the <code>ChangeEvent</code> whose source is the
		 *            <code>JSpinner</code> whose model has changed.
		 * @see #getTextField
		 * @see JSpinner#getValue
		 */
		public void stateChanged(ChangeEvent e)
		{
			JSpinner spinner = (JSpinner) (e.getSource());
			getTextField().setValue(spinner.getValue());
		}

		/**
		 * Called by the <code>JFormattedTextField</code>
		 * <code>PropertyChangeListener</code>. When the <code>"value"</code>
		 * property changes, which implies that the user has typed a new number,
		 * we set the value of the spinners model.
		 * <p>
		 * This class ignores <code>PropertyChangeEvents</code> whose source is
		 * not the <code>JFormattedTextField</code>, so subclasses may safely
		 * make <code>this</code> <code>DefaultEditor</code> a
		 * <code>PropertyChangeListener</code> on other objects.
		 * 
		 * @param e
		 *            the <code>PropertyChangeEvent</code> whose source is the
		 *            <code>JFormattedTextField</code> created by this class.
		 * @see #getTextField
		 */
		public void propertyChange(PropertyChangeEvent e)
		{
			JSpinner spinner = getSpinner();

			if (spinner == null)
			{
				// Indicates we aren't installed anywhere.
				return;
			}

			Object source = e.getSource();
			String name = e.getPropertyName();
			if ((source instanceof JFormattedTextField) && "value".equals(name))
			{
				Object lastValue = spinner.getValue();

				// Try to set the new value
				try
				{
					spinner.setValue(getTextField().getValue());
				} catch (IllegalArgumentException iae)
				{
					// SpinnerModel didn't like new value, reset
					try
					{
						((JFormattedTextField) source).setValue(lastValue);
					} catch (IllegalArgumentException iae2)
					{
						// Still bogus, nothing else we can do, the
						// SpinnerModel and JFormattedTextField are now out
						// of sync.
					}
				}
			}
		}

		/**
		 * This <code>LayoutManager</code> method does nothing. We're only
		 * managing a single child and there's no support for layout
		 * constraints.
		 * 
		 * @param name
		 *            ignored
		 * @param child
		 *            ignored
		 */
		public void addLayoutComponent(String name, Component child)
		{
		}

		/**
		 * This <code>LayoutManager</code> method does nothing. There isn't any
		 * per-child state.
		 * 
		 * @param child
		 *            ignored
		 */
		public void removeLayoutComponent(Component child)
		{
		}

		/**
		 * Returns the size of the parents insets.
		 */
		private Dimension insetSize(Container parent)
		{
			Insets insets = parent.getInsets();
			int w = insets.left + insets.right;
			int h = insets.top + insets.bottom;
			return new Dimension(w, h);
		}

		/**
		 * Returns the preferred size of first (and only) child plus the size of
		 * the parents insets.
		 * 
		 * @param parent
		 *            the Container that's managing the layout
		 * @return the preferred dimensions to lay out the subcomponents of the
		 *         specified container.
		 */
		public Dimension preferredLayoutSize(Container parent)
		{
			Dimension preferredSize = insetSize(parent);
			if (parent.getComponentCount() > 0)
			{
				Dimension childSize = getComponent(0).getPreferredSize();
				preferredSize.width += childSize.width;
				preferredSize.height += childSize.height;
			}
			return preferredSize;
		}

		/**
		 * Returns the minimum size of first (and only) child plus the size of
		 * the parents insets.
		 * 
		 * @param parent
		 *            the Container that's managing the layout
		 * @return the minimum dimensions needed to lay out the subcomponents of
		 *         the specified container.
		 */
		public Dimension minimumLayoutSize(Container parent)
		{
			Dimension minimumSize = insetSize(parent);
			if (parent.getComponentCount() > 0)
			{
				Dimension childSize = getComponent(0).getMinimumSize();
				minimumSize.width += childSize.width;
				minimumSize.height += childSize.height;
			}
			return minimumSize;
		}

		/**
		 * Resize the one (and only) child to completely fill the area within
		 * the parents insets.
		 */
		public void layoutContainer(Container parent)
		{
			if (parent.getComponentCount() > 0)
			{
				Insets insets = parent.getInsets();
				int w = parent.getWidth() - (insets.left + insets.right);
				int h = parent.getHeight() - (insets.top + insets.bottom);
				getComponent(0).setBounds(insets.left, insets.top, w, h);
			}
		}

		/**
		 * Pushes the currently edited value to the <code>SpinnerModel</code>.
		 * <p>
		 * The default implementation invokes <code>commitEdit</code> on the
		 * <code>JFormattedTextField</code>.
		 * 
		 * @throws ParseException
		 *             if the edited value is not legal
		 */
		public void commitEdit() throws ParseException
		{
			// If the value in the JFormattedTextField is legal, this will have
			// the result of pushing the value to the SpinnerModel
			// by way of the <code>propertyChange</code> method.
			JFormattedTextField ftf = getTextField();

			ftf.commitEdit();
		}

		/**
		 * Returns the baseline.
		 * 
		 * @throws IllegalArgumentException
		 *             {@inheritDoc}
		 * @see javax.swing.JComponent#getBaseline(int,int)
		 * @see javax.swing.JComponent#getBaselineResizeBehavior()
		 * @since 1.6
		 */
		public int getBaseline(int width, int height)
		{
			// check size.
			super.getBaseline(width, height);
			Insets insets = getInsets();
			width = width - insets.left - insets.right;
			height = height - insets.top - insets.bottom;
			int baseline = getComponent(0).getBaseline(width, height);
			if (baseline >= 0)
			{
				return baseline + insets.top;
			}
			return -1;
		}

		/**
		 * Returns an enum indicating how the baseline of the component changes
		 * as the size changes.
		 * 
		 * @throws NullPointerException
		 *             {@inheritDoc}
		 * @see javax.swing.JComponent#getBaseline(int, int)
		 * @since 1.6
		 */
		public BaselineResizeBehavior getBaselineResizeBehavior()
		{
			return getComponent(0).getBaselineResizeBehavior();
		}
	}

	private static class DisabledAction implements Action
	{
		public Object getValue(String key)
		{
			return null;
		}

		public void putValue(String key, Object value)
		{
		}

		public void setEnabled(boolean b)
		{
		}

		public boolean isEnabled()
		{
			return false;
		}

		public void addPropertyChangeListener(PropertyChangeListener l)
		{
		}

		public void removePropertyChangeListener(PropertyChangeListener l)
		{
		}

		public void actionPerformed(ActionEvent ae)
		{
		}
	}

	public static class NumberEditor extends DefaultEditor
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4513355067782285997L;

		// This is here until DecimalFormat gets a constructor that
		// takes a Locale: 4923525
		private static String getDefaultPattern(Locale locale)
		{
//			ResourceBundle rb = LocaleData.getNumberFormatData(locale);
//			String[] all = rb.getStringArray("NumberPatterns");
//			return all[0];
			return "#00.0#";
		}

		public NumberEditor(JFSpinner spinner)
		{
			this(spinner, getDefaultPattern(spinner.getLocale()));
		}

		public NumberEditor(JFSpinner spinner, String decimalFormatPattern)
		{
			this(spinner, new DecimalFormat(decimalFormatPattern));
		}

		private NumberEditor(JFSpinner spinner, DecimalFormat format)
		{
			super(spinner);
			if (!(spinner.getModel() instanceof SpinnerNumberModel))
			{
				throw new IllegalArgumentException(
						"model not a SpinnerNumberModel");
			}

			SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
			NumberFormatter formatter = new NumberEditorFormatter(model, format);
			DefaultFormatterFactory factory = new DefaultFormatterFactory(
					formatter);
			JFormattedTextField ftf = getTextField();
			ftf.setEditable(true);
			ftf.setFormatterFactory(factory);
			ftf.setHorizontalAlignment(JTextField.RIGHT);

			/*
			 * TBD - initializing the column width of the text field is
			 * imprecise and doing it here is tricky because the developer may
			 * configure the formatter later.
			 */
			try
			{
				String maxString = formatter.valueToString(model.getMinimum());
				String minString = formatter.valueToString(model.getMaximum());
				ftf.setColumns(Math.max(maxString.length(), minString.length()));
			} catch (ParseException e)
			{
				// TBD should throw a chained error here
			}

		}

		public DecimalFormat getFormat()
		{
			return (DecimalFormat) ((NumberFormatter) (getTextField()
					.getFormatter())).getFormat();
		}

		public SpinnerNumberModel getModel()
		{
			return (SpinnerNumberModel) (getSpinner().getModel());
		}
	}

	private static class NumberEditorFormatter extends NumberFormatter
	{
		private final SpinnerNumberModel model;

		NumberEditorFormatter(SpinnerNumberModel model, NumberFormat format)
		{
			super(format);
			this.model = model;
			setValueClass(model.getValue().getClass());
		}

		public void setMinimum(Comparable min)
		{
			model.setMinimum(min);
		}

		public Comparable getMinimum()
		{
			return model.getMinimum();
		}

		public void setMaximum(Comparable max)
		{
			model.setMaximum(max);
		}

		public Comparable getMaximum()
		{
			return model.getMaximum();
		}
	}

}
