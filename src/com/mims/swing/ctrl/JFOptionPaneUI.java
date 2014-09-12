/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;

import sun.swing.DefaultLookup;

import com.mims.swing.look.JFLookAndFeel;

/**
 * √Ë ˆ:.
 * <p>
 * 
 * @author ¿ÓÕ˛
 * 
 * @Date: 2011-9-10
 * 
 */
public class JFOptionPaneUI extends BasicOptionPaneUI
{
	private Handler handler;

	public static ComponentUI createUI(JComponent x)
	{
		return new JFOptionPaneUI();
	}

	public void installUI(JComponent c)
	{
		super.installUI(c);
		optionPane.setBackground(SwingConst.PANEL_BACKGROUND);
	}

	protected void installDefaults()
	{
		super.installDefaults();
		optionPane.setBackground(Color.RED);
	}

	protected void addButtonComponents(Container container, Object[] buttons,
			int initialIndex)
	{
		if (buttons != null && buttons.length > 0)
		{
			boolean sizeButtonsToSame = getSizeButtonsToSameWidth();
			boolean createdAll = true;
			int numButtons = buttons.length;
			JButton[] createdButtons = null;
			int maxWidth = 0;

			if (sizeButtonsToSame)
			{
				createdButtons = new JButton[numButtons];
			}

			for (int counter = 0; counter < numButtons; counter++)
			{
				Object button = buttons[counter];
				Component newComponent;

				if (button instanceof Component)
				{
					createdAll = false;
					newComponent = (Component) button;
					container.add(newComponent);
					hasCustomComponents = true;

				} else
				{
					JButton aButton;

					if (button instanceof ButtonFactory)
					{
						aButton = ((ButtonFactory) button).createButton();
					} else if (button instanceof Icon)
						aButton = new JButton((Icon) button);
					else
						aButton = new JButton(button.toString());

					aButton.setName("OptionPane.button");
					aButton.setMultiClickThreshhold(DefaultLookup.getInt(
							optionPane, this,
							"OptionPane.buttonClickThreshhold", 0));
					configureButton(aButton);

					container.add(aButton);

					ActionListener buttonListener = createButtonActionListener(counter);
					if (buttonListener != null)
					{
						aButton.addActionListener(buttonListener);
					}
					newComponent = aButton;
				}
				if (sizeButtonsToSame && createdAll
						&& (newComponent instanceof JButton))
				{
					createdButtons[counter] = (JButton) newComponent;
					maxWidth = Math.max(maxWidth,
							newComponent.getMinimumSize().width);
				}
				if (counter == initialIndex)
				{
					initialFocusComponent = newComponent;
					if (initialFocusComponent instanceof JButton)
					{
						JButton defaultB = (JButton) initialFocusComponent;
						defaultB.addHierarchyListener(new HierarchyListener()
						{
							public void hierarchyChanged(HierarchyEvent e)
							{
								if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0)
								{
									JButton defaultButton = (JButton) e
											.getComponent();
									JRootPane root = SwingUtilities
											.getRootPane(defaultButton);
									if (root != null)
									{
										root.setDefaultButton(defaultButton);
									}
								}
							}
						});
					}
				}
			}
			((ButtonAreaLayout) container.getLayout())
					.setSyncAllWidths((sizeButtonsToSame && createdAll));
			/*
			 * Set the padding, windows seems to use 8 if <= 2 components,
			 * otherwise 4 is used. It may actually just be the size of the
			 * buttons is always the same, not sure.
			 */
			if (DefaultLookup.getBoolean(optionPane, this,
					"OptionPane.setButtonMargin", true)
					&& sizeButtonsToSame
					&& createdAll)
			{
				JButton aButton;
				int padSize;

				padSize = (numButtons <= 2 ? 8 : 4);

				for (int counter = 0; counter < numButtons; counter++)
				{
					aButton = createdButtons[counter];
					aButton.setMargin(new Insets(2, padSize, 2, padSize));
				}
			}
		}
	}

	protected Container createButtonArea()
	{
		JPanel bottom = new JFPanel();
		Border border = (Border) DefaultLookup.get(optionPane, this,
				"OptionPane.buttonAreaBorder");
		bottom.setName("OptionPane.buttonArea");
		if (border != null)
		{
			bottom.setBorder(border);
		}
		bottom.setLayout(new ButtonAreaLayout(DefaultLookup.getBoolean(
				optionPane, this, "OptionPane.sameSizeButtons", true),
				DefaultLookup.getInt(optionPane, this,
						"OptionPane.buttonPadding", 6), DefaultLookup.getInt(
						optionPane, this, "OptionPane.buttonOrientation",
						SwingConstants.CENTER), DefaultLookup.getBoolean(
						optionPane, this, "OptionPane.isYesLast", false)));
		addButtonComponents(bottom, getButtons(), getInitialValueIndex());
		return bottom;
	}

	protected Container createMessageArea()
	{
		JPanel top = new JFPanel();
		Border topBorder = (Border) DefaultLookup.get(optionPane, this,
				"OptionPane.messageAreaBorder");
		if (topBorder != null)
		{
			top.setBorder(topBorder);
		}
		top.setLayout(new BorderLayout());

		/* Fill the body. */
		Container body = new JFPanel(new GridBagLayout());
		Container realBody = new JFPanel(new BorderLayout());

		body.setName("OptionPane.body");
		realBody.setName("OptionPane.realBody");

		if (getIcon() != null)
		{
			JPanel sep = new JFPanel();
			sep.setName("OptionPane.separator");
			sep.setPreferredSize(new Dimension(15, 1));
			realBody.add(sep, BorderLayout.BEFORE_LINE_BEGINS);
		}
		realBody.add(body, BorderLayout.CENTER);

		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = cons.gridy = 0;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.gridheight = 1;
		cons.anchor = DefaultLookup.getInt(optionPane, this,
				"OptionPane.messageAnchor", GridBagConstraints.CENTER);
		cons.insets = new Insets(0, 0, 3, 0);

		addMessageComponents(body, cons, getMessage(),
				getMaxCharactersPerLineCount(), false);
		top.add(realBody, BorderLayout.CENTER);

		addIcon(top);
		return top;
	}

	protected Object[] getButtons()
	{
		if (optionPane != null)
		{
			Object[] suppliedOptions = optionPane.getOptions();

			if (suppliedOptions == null)
			{
				Object[] defaultOptions;
				int type = optionPane.getOptionType();
				Locale l = optionPane.getLocale();
				int minimumWidth = DefaultLookup.getInt(optionPane, this,
						"OptionPane.buttonMinimumWidth", -1);
				minimumWidth = 0;
				if (type == JOptionPane.YES_NO_OPTION)
				{
					defaultOptions = new ButtonFactory[2];
					defaultOptions[0] = new ButtonFactory(UIManager.getString(
							"OptionPane.yesButtonText", l), getMnemonic(
							"OptionPane.yesButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.yesIcon"), minimumWidth);
					defaultOptions[1] = new ButtonFactory(UIManager.getString(
							"OptionPane.noButtonText", l), getMnemonic(
							"OptionPane.noButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.noIcon"), minimumWidth);
				} else if (type == JOptionPane.YES_NO_CANCEL_OPTION)
				{
					defaultOptions = new ButtonFactory[3];
					defaultOptions[0] = new ButtonFactory(UIManager.getString(
							"OptionPane.yesButtonText", l), getMnemonic(
							"OptionPane.yesButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.yesIcon"), minimumWidth);
					defaultOptions[1] = new ButtonFactory(UIManager.getString(
							"OptionPane.noButtonText", l), getMnemonic(
							"OptionPane.noButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.noIcon"), minimumWidth);
					defaultOptions[2] = new ButtonFactory(UIManager.getString(
							"OptionPane.cancelButtonText", l), getMnemonic(
							"OptionPane.cancelButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.cancelIcon"), minimumWidth);
				} else if (type == JOptionPane.OK_CANCEL_OPTION)
				{
					defaultOptions = new ButtonFactory[2];
					defaultOptions[0] = new ButtonFactory(UIManager.getString(
							"OptionPane.okButtonText", l), getMnemonic(
							"OptionPane.okButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.okIcon"), minimumWidth);
					defaultOptions[1] = new ButtonFactory(UIManager.getString(
							"OptionPane.cancelButtonText", l), getMnemonic(
							"OptionPane.cancelButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.cancelIcon"), minimumWidth);
				} else
				{
					defaultOptions = new ButtonFactory[1];
					defaultOptions[0] = new ButtonFactory(UIManager.getString(
							"OptionPane.okButtonText", l), getMnemonic(
							"OptionPane.okButtonMnemonic", l),
							(Icon) DefaultLookup.get(optionPane, this,
									"OptionPane.okIcon"), minimumWidth);
				}
				return defaultOptions;

			}
			return suppliedOptions;
		}
		return null;
	}

	private static class ButtonFactory
	{
		private String text;
		private int mnemonic;
		private Icon icon;
		private int minimumWidth = -1;

		ButtonFactory(String text, int mnemonic, Icon icon, int minimumWidth)
		{
			this.text = text;
			this.mnemonic = mnemonic;
			this.icon = icon;
			this.minimumWidth = minimumWidth;
		}

		JButton createButton()
		{
			JButton button = null;

			if (minimumWidth > 0)
			{
				button = new ConstrainedButton(text, minimumWidth);
			} else
			{
				button = new JFButton(text);
			}
			if (icon != null)
			{
				button.setIcon(icon);
			}
			if (mnemonic != 0)
			{
				button.setMnemonic(mnemonic);
			}
			return button;
		}

		private static class ConstrainedButton extends JButton
		{
			int minimumWidth;

			ConstrainedButton(String text, int minimumWidth)
			{
				super(text);
				this.minimumWidth = minimumWidth;
			}

			public Dimension getMinimumSize()
			{
				Dimension min = super.getMinimumSize();
				min.width = Math.max(min.width, minimumWidth);
				return min;
			}

			public Dimension getPreferredSize()
			{
				Dimension pref = super.getPreferredSize();
				pref.width = Math.max(pref.width, minimumWidth);
				return pref;
			}
		}
	}

	private int getMnemonic(String key, Locale l)
	{
		String value = (String) UIManager.get(key, l);

		if (value == null)
		{
			return 0;
		}
		try
		{
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe)
		{
		}
		return 0;
	}

	protected Object getMessage()
	{
		inputComponent = null;
		if (optionPane != null)
		{
			if (optionPane.getWantsInput())
			{
				/*
				 * Create a user component to capture the input. If the
				 * selectionValues are non null the component and there are < 20
				 * values it'll be a combobox, if non null and >= 20, it'll be a
				 * list, otherwise it'll be a textfield.
				 */
				Object message = optionPane.getMessage();
				Object[] sValues = optionPane.getSelectionValues();
				Object inputValue = optionPane.getInitialSelectionValue();
				JComponent toAdd;

				if (sValues != null)
				{
					if (sValues.length < 20)
					{
						JComboBox cBox = new JComboBox();

						cBox.setName("OptionPane.comboBox");
						for (int counter = 0, maxCounter = sValues.length; counter < maxCounter; counter++)
						{
							cBox.addItem(sValues[counter]);
						}
						if (inputValue != null)
						{
							cBox.setSelectedItem(inputValue);
						}
						inputComponent = cBox;
						toAdd = cBox;

					} else
					{
						JList list = new JList(sValues);
						JScrollPane sp = new JScrollPane(list);

						sp.setName("OptionPane.scrollPane");
						list.setName("OptionPane.list");
						list.setVisibleRowCount(10);
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						if (inputValue != null)
							list.setSelectedValue(inputValue, true);
						list.addMouseListener(getHandler());
						toAdd = sp;
						inputComponent = list;
					}

				} else
				{
					JFTextField tf = new JFTextField(20);

					tf.setName("OptionPane.textField");
					// tf.setKeyStrokes(new KeyStroke[] {
					// KeyStroke.getKeyStroke("ENTER") } );
					if (inputValue != null)
					{
						String inputString = inputValue.toString();
						tf.setText(inputString);
						tf.setSelectionStart(0);
						tf.setSelectionEnd(inputString.length());
					}
					tf.addActionListener(getHandler());
					toAdd = inputComponent = tf;
				}

				Object[] newMessage;

				if (message == null)
				{
					newMessage = new Object[1];
					newMessage[0] = toAdd;

				} else
				{
					newMessage = new Object[2];
					newMessage[0] = message;
					newMessage[1] = toAdd;
				}
				return newMessage;
			}
			return optionPane.getMessage();
		}
		return null;
	}

	private class Handler implements ActionListener, MouseListener,
			PropertyChangeListener
	{
		//
		// ActionListener
		//
		public void actionPerformed(ActionEvent e)
		{
			optionPane.setInputValue(((JTextField) e.getSource()).getText());
		}

		//
		// MouseListener
		//
		public void mouseClicked(MouseEvent e)
		{
		}

		public void mouseReleased(MouseEvent e)
		{
		}

		public void mouseEntered(MouseEvent e)
		{
		}

		public void mouseExited(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
			if (e.getClickCount() == 2)
			{
				JList list = (JList) e.getSource();
				int index = list.locationToIndex(e.getPoint());

				optionPane.setInputValue(list.getModel().getElementAt(index));
			}
		}

		//
		// PropertyChangeListener
		//
		public void propertyChange(PropertyChangeEvent e)
		{
			if (e.getSource() == optionPane)
			{
				// Option Pane Auditory Cue Activation
				// only respond to "ancestor" changes
				// the idea being that a JOptionPane gets a JDialog when it is
				// set to appear and loses it's JDialog when it is dismissed.
				if ("ancestor" == e.getPropertyName())
				{
					JOptionPane op = (JOptionPane) e.getSource();
					boolean isComingUp;

					// if the old value is null, then the JOptionPane is being
					// created since it didn't previously have an ancestor.
					if (e.getOldValue() == null)
					{
						isComingUp = true;
					} else
					{
						isComingUp = false;
					}

					// figure out what to do based on the message type
					switch (op.getMessageType())
					{
					case JOptionPane.PLAIN_MESSAGE:
						if (isComingUp)
						{
							JFLookAndFeel.playSound(optionPane,
									"OptionPane.informationSound");
						}
						break;
					case JOptionPane.QUESTION_MESSAGE:
						if (isComingUp)
						{
							JFLookAndFeel.playSound(optionPane,
									"OptionPane.questionSound");
						}
						break;
					case JOptionPane.INFORMATION_MESSAGE:
						if (isComingUp)
						{
							JFLookAndFeel.playSound(optionPane,
									"OptionPane.informationSound");
						}
						break;
					case JOptionPane.WARNING_MESSAGE:
						if (isComingUp)
						{
							JFLookAndFeel.playSound(optionPane,
									"OptionPane.warningSound");
						}
						break;
					case JOptionPane.ERROR_MESSAGE:
						if (isComingUp)
						{
							JFLookAndFeel.playSound(optionPane,
									"OptionPane.errorSound");
						}
						break;
					default:
						System.err.println("Undefined JOptionPane type: "
								+ op.getMessageType());
						break;
					}
				}
				// Visual activity
				String changeName = e.getPropertyName();

				if (changeName == JOptionPane.OPTIONS_PROPERTY
						|| changeName == JOptionPane.INITIAL_VALUE_PROPERTY
						|| changeName == JOptionPane.ICON_PROPERTY
						|| changeName == JOptionPane.MESSAGE_TYPE_PROPERTY
						|| changeName == JOptionPane.OPTION_TYPE_PROPERTY
						|| changeName == JOptionPane.MESSAGE_PROPERTY
						|| changeName == JOptionPane.SELECTION_VALUES_PROPERTY
						|| changeName == JOptionPane.INITIAL_SELECTION_VALUE_PROPERTY
						|| changeName == JOptionPane.WANTS_INPUT_PROPERTY)
				{
					uninstallComponents();
					installComponents();
					optionPane.validate();
				} else if (changeName == "componentOrientation")
				{
					ComponentOrientation o = (ComponentOrientation) e
							.getNewValue();
					JOptionPane op = (JOptionPane) e.getSource();
					if (o != (ComponentOrientation) e.getOldValue())
					{
						op.applyComponentOrientation(o);
					}
				}
			}
		}
	}

	private Handler getHandler()
	{
		if (handler == null)
		{
			handler = new Handler();
		}
		return handler;
	}

	private void configureButton(JButton button)
	{
		Font buttonFont = (Font) DefaultLookup.get(optionPane, this,
				"OptionPane.buttonFont");
		if (buttonFont != null)
		{
			button.setFont(buttonFont);
		}
	}
	
	 public static class ButtonAreaLayout implements LayoutManager {
			protected boolean           syncAllWidths;
			protected int               padding;
		        /** If true, children are lumped together in parent. */
			protected boolean           centersChildren;
		        private int orientation;
		        private boolean reverseButtons;
		        /**
		         * Indicates whether or not centersChildren should be used vs
		         * the orientation. This is done for backward compatability
		         * for subclassers.
		         */
		        private boolean useOrientation;

			public ButtonAreaLayout(boolean syncAllWidths, int padding) {
			    this.syncAllWidths = syncAllWidths;
			    this.padding = padding;
			    centersChildren = true;
		            useOrientation = false;
			}

		        ButtonAreaLayout(boolean syncAllSizes, int padding, int orientation,
		                         boolean reverseButtons) {
		            this(syncAllSizes, padding);
		            useOrientation = true;
		            this.orientation = orientation;
		            this.reverseButtons = reverseButtons;
		        }

			public void setSyncAllWidths(boolean newValue) {
			    syncAllWidths = newValue;
			}

			public boolean getSyncAllWidths() {
			    return syncAllWidths;
			}

			public void setPadding(int newPadding) {
			    this.padding = newPadding;
			}

			public int getPadding() {
			    return padding;
			}

		        public void setCentersChildren(boolean newValue) {
			    centersChildren = newValue;
		            useOrientation = false;
			}

		        public boolean getCentersChildren() {
			    return centersChildren;
			}

		        private int getOrientation(Container container) {
		            if (!useOrientation) {
		                return SwingConstants.CENTER;
		            }
		            if (container.getComponentOrientation().isLeftToRight()) {
		                return orientation;
		            }
		            switch (orientation) {
		            case SwingConstants.LEFT:
		                return SwingConstants.RIGHT;
		            case SwingConstants.RIGHT:
		                return SwingConstants.LEFT;
		            case SwingConstants.CENTER:
		                return SwingConstants.CENTER;
		            }
		            return SwingConstants.LEFT;
		        }

			public void addLayoutComponent(String string, Component comp) {
			}

			public void layoutContainer(Container container) {
			    Component[]      children = container.getComponents();

			    if(children != null && children.length > 0) {
				int               numChildren = children.length;
				Insets            insets = container.getInsets();
		                int maxWidth = 0;
		                int maxHeight = 0;
		                int totalButtonWidth = 0;
		                int x = 0;
		                int xOffset = 0;
		                boolean ltr = container.getComponentOrientation().
		                                        isLeftToRight();
		                boolean reverse = (ltr) ? reverseButtons : !reverseButtons;

		                for(int counter = 0; counter < numChildren; counter++) {
		                    Dimension pref = children[counter].getPreferredSize();
		                    maxWidth = Math.max(maxWidth, pref.width);
		                    maxHeight = Math.max(maxHeight, pref.height);
		                    totalButtonWidth += pref.width;
		                }
		                if (getSyncAllWidths()) {
		                    totalButtonWidth = maxWidth * numChildren;
		                }
		                totalButtonWidth += (numChildren - 1) * padding;

		                switch (getOrientation(container)) {
		                case SwingConstants.LEFT:
		                    x = insets.left;
		                    break;
		                case SwingConstants.RIGHT:
		                    x = container.getWidth() - insets.right - totalButtonWidth;
		                    break;
		                case SwingConstants.CENTER:
		                    if (getCentersChildren() || numChildren < 2) {
		                        x = (container.getWidth() - totalButtonWidth) / 2;
		                    }
		                    else {
		                        x = insets.left;
		                        if (getSyncAllWidths()) {
		                            xOffset = (container.getWidth() - insets.left -
		                                       insets.right - totalButtonWidth) /
		                                (numChildren - 1) + maxWidth;
		                        }
		                        else {
		                            xOffset = (container.getWidth() - insets.left -
		                                       insets.right - totalButtonWidth) /
		                                      (numChildren - 1);
		                        }
		                    }
		                    break;
		                }

		                for (int counter = 0; counter < numChildren; counter++) {
		                    int index = (reverse) ? numChildren - counter - 1 :
		                                counter;
		                    Dimension pref = children[index].getPreferredSize();

		                    if (getSyncAllWidths()) {
		                        children[index].setBounds(x, insets.top,
		                                                  maxWidth, maxHeight);
		                    }
		                    else {
		                        children[index].setBounds(x, insets.top, pref.width,
		                                                  pref.height);
		                    }
		                    if (xOffset != 0) {
		                        x += xOffset;
		                    }
		                    else {
		                        x += children[index].getWidth() + padding;
		                    }
		                }
			    }
			}

			public Dimension minimumLayoutSize(Container c) {
			    if(c != null) {
				Component[]       children = c.getComponents();

				if(children != null && children.length > 0) {
				    Dimension     aSize;
				    int           numChildren = children.length;
				    int           height = 0;
				    Insets        cInsets = c.getInsets();
				    int           extraHeight = cInsets.top + cInsets.bottom;
				    int           extraWidth = cInsets.left + cInsets.right;

				    if (syncAllWidths) {
					int              maxWidth = 0;

					for(int counter = 0; counter < numChildren; counter++){
					    aSize = children[counter].getPreferredSize();
					    height = Math.max(height, aSize.height);
					    maxWidth = Math.max(maxWidth, aSize.width);
					}
					return new Dimension(extraWidth + (maxWidth * numChildren) + 
							     (numChildren - 1) * padding,
							     extraHeight + height);
				    }
				    else {
					int        totalWidth = 0;

					for(int counter = 0; counter < numChildren; counter++){
					    aSize = children[counter].getPreferredSize();
					    height = Math.max(height, aSize.height);
					    totalWidth += aSize.width;
					}
					totalWidth += ((numChildren - 1) * padding);
					return new Dimension(extraWidth + totalWidth, extraHeight + height);
				    }
				}
			    }
			    return new Dimension(0, 0);
			}

			public Dimension preferredLayoutSize(Container c) {
			    return minimumLayoutSize(c);
			}

			public void removeLayoutComponent(Component c) { }
		    }

}
