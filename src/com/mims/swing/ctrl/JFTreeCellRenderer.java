/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.tree.TreeCellRenderer;

import com.mims.swing.ctrl.util.ResourceUtils;

import sun.swing.DefaultLookup;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-3
 * 
 */
public class JFTreeCellRenderer extends JFLabel implements TreeCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5775543501693753807L;

	private JTree tree;

	protected boolean selected;
	protected boolean hasFocus;
	private boolean drawsFocusBorderAroundIcon;
	private boolean drawDashedFocusIndicator;

	private Color treeBGColor;
	private Color focusBGColor;

	// Icons
	/** Icon used to show non-leaf nodes that aren't expanded. */
	transient protected Icon closedIcon;

	/** Icon used to show leaf nodes. */
	transient protected Icon leafIcon;

	/** Icon used to show non-leaf nodes that are expanded. */
	transient protected Icon openIcon;

	// Colors
	/** Color to use for the foreground for selected nodes. */
	protected Color textSelectionColor;

	/** Color to use for the foreground for non-selected nodes. */
	protected Color textNonSelectionColor;

	/** Color to use for the background when a node is selected. */
	protected Color backgroundSelectionColor;

	/** Color to use for the background when the node isn't selected. */
	protected Color backgroundNonSelectionColor;

	/** Color to use for the focus indicator when the node has focus. */
	protected Color borderSelectionColor;

	private boolean isDropCell;
	private boolean fillBackground = true;

	/**
	 * Returns a new instance of DefaultTreeCellRenderer. Alignment is set to
	 * left aligned. Icons and text color are determined from the UIManager.
	 */
	public JFTreeCellRenderer()
	{
//		setLeafIcon(DefaultLookup.getIcon(this, ui, "Tree.leafIcon"));
//		setClosedIcon(DefaultLookup.getIcon(this, ui, "Tree.closedIcon"));
//		setOpenIcon(DefaultLookup.getIcon(this, ui, "Tree.openIcon"));
		
		setLeafIcon(DefaultLookup.getIcon(this, ui, "Tree.leafIcon"));
		setClosedIcon(ResourceUtils.getIcon("folder_close.png"));
		setOpenIcon(ResourceUtils.getIcon("folder_open.png"));

		setTextSelectionColor(DefaultLookup.getColor(this, ui,
				"Tree.selectionForeground"));
		setTextNonSelectionColor(DefaultLookup.getColor(this, ui,
				"Tree.textForeground"));
		setBackgroundSelectionColor(DefaultLookup.getColor(this, ui,
				"Tree.selectionBackground"));
		setBackgroundNonSelectionColor(DefaultLookup.getColor(this, ui,
				"Tree.textBackground"));
		setBorderSelectionColor(DefaultLookup.getColor(this, ui,
				"Tree.selectionBorderColor"));
		drawsFocusBorderAroundIcon = DefaultLookup.getBoolean(this, ui,
				"Tree.drawsFocusBorderAroundIcon", false);
		drawDashedFocusIndicator = DefaultLookup.getBoolean(this, ui,
				"Tree.drawDashedFocusIndicator", false);

		fillBackground = DefaultLookup.getBoolean(this, ui,
				"Tree.rendererFillBackground", true);
		Insets margins = DefaultLookup.getInsets(this, ui,
				"Tree.rendererMargins");
		if (margins != null)
		{
			setBorder(new EmptyBorder(margins.top, margins.left,
					margins.bottom, margins.right));
		}

		setName("Tree.cellRenderer");
	}

	/**
	 * Returns the default icon, for the current laf, that is used to represent
	 * non-leaf nodes that are expanded.
	 */
	public Icon getDefaultOpenIcon()
	{
		return DefaultLookup.getIcon(this, ui, "Tree.openIcon");
	}

	/**
	 * Returns the default icon, for the current laf, that is used to represent
	 * non-leaf nodes that are not expanded.
	 */
	public Icon getDefaultClosedIcon()
	{
		return DefaultLookup.getIcon(this, ui, "Tree.closedIcon");
	}

	/**
	 * Returns the default icon, for the current laf, that is used to represent
	 * leaf nodes.
	 */
	public Icon getDefaultLeafIcon()
	{
		return DefaultLookup.getIcon(this, ui, "Tree.leafIcon");
	}

	/**
	 * Sets the icon used to represent non-leaf nodes that are expanded.
	 */
	public void setOpenIcon(Icon newIcon)
	{
		openIcon = newIcon;
	}

	/**
	 * Returns the icon used to represent non-leaf nodes that are expanded.
	 */
	public Icon getOpenIcon()
	{
		return openIcon;
	}

	/**
	 * Sets the icon used to represent non-leaf nodes that are not expanded.
	 */
	public void setClosedIcon(Icon newIcon)
	{
		closedIcon = newIcon;
	}

	/**
	 * Returns the icon used to represent non-leaf nodes that are not expanded.
	 */
	public Icon getClosedIcon()
	{
		return closedIcon;
	}

	/**
	 * Sets the icon used to represent leaf nodes.
	 */
	public void setLeafIcon(Icon newIcon)
	{
		leafIcon = newIcon;
	}

	/**
	 * Returns the icon used to represent leaf nodes.
	 */
	public Icon getLeafIcon()
	{
		return leafIcon;
	}

	/**
	 * Sets the color the text is drawn with when the node is selected.
	 */
	public void setTextSelectionColor(Color newColor)
	{
		textSelectionColor = newColor;
	}

	/**
	 * Returns the color the text is drawn with when the node is selected.
	 */
	public Color getTextSelectionColor()
	{
		return textSelectionColor;
	}

	/**
	 * Sets the color the text is drawn with when the node isn't selected.
	 */
	public void setTextNonSelectionColor(Color newColor)
	{
		textNonSelectionColor = newColor;
	}

	/**
	 * Returns the color the text is drawn with when the node isn't selected.
	 */
	public Color getTextNonSelectionColor()
	{
		return textNonSelectionColor;
	}

	/**
	 * Sets the color to use for the background if node is selected.
	 */
	public void setBackgroundSelectionColor(Color newColor)
	{
		backgroundSelectionColor = newColor;
	}

	/**
	 * Returns the color to use for the background if node is selected.
	 */
	public Color getBackgroundSelectionColor()
	{
		return backgroundSelectionColor;
	}

	/**
	 * Sets the background color to be used for non selected nodes.
	 */
	public void setBackgroundNonSelectionColor(Color newColor)
	{
		backgroundNonSelectionColor = newColor;
	}

	/**
	 * Returns the background color to be used for non selected nodes.
	 */
	public Color getBackgroundNonSelectionColor()
	{
		return backgroundNonSelectionColor;
	}

	/**
	 * Sets the color to use for the border.
	 */
	public void setBorderSelectionColor(Color newColor)
	{
		borderSelectionColor = newColor;
	}

	/**
	 * Returns the color the border is drawn.
	 */
	public Color getBorderSelectionColor()
	{
		return borderSelectionColor;
	}

	/**
	 * Subclassed to map <code>FontUIResource</code>s to null. If
	 * <code>font</code> is null, or a <code>FontUIResource</code>, this has the
	 * effect of letting the font of the JTree show through. On the other hand,
	 * if <code>font</code> is non-null, and not a <code>FontUIResource</code>,
	 * the font becomes <code>font</code>.
	 */
	public void setFont(Font font)
	{
		if (font instanceof FontUIResource)
			font = null;
		super.setFont(font);
	}

	/**
	 * Gets the font of this component.
	 * 
	 * @return this component's font; if a font has not been set for this
	 *         component, the font of its parent is returned
	 */
	public Font getFont()
	{
		Font font = super.getFont();

		if (font == null && tree != null)
		{
			// Strive to return a non-null value, otherwise the html support
			// will typically pick up the wrong font in certain situations.
			font = tree.getFont();
		}
		return font;
	}

	/**
	 * Subclassed to map <code>ColorUIResource</code>s to null. If
	 * <code>color</code> is null, or a <code>ColorUIResource</code>, this has
	 * the effect of letting the background color of the JTree show through. On
	 * the other hand, if <code>color</code> is non-null, and not a
	 * <code>ColorUIResource</code>, the background becomes <code>color</code>.
	 */
	public void setBackground(Color color)
	{
		if (color instanceof ColorUIResource)
			color = null;
		super.setBackground(color);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		String stringValue = tree.convertValueToText(value, sel, expanded,
				leaf, row, hasFocus);

		this.tree = tree;
		this.hasFocus = hasFocus;
		setText(stringValue);

		Color fg = null;
		isDropCell = false;

		JTree.DropLocation dropLocation = tree.getDropLocation();
		if (dropLocation != null && dropLocation.getChildIndex() == -1
				&& tree.getRowForPath(dropLocation.getPath()) == row)
		{

			Color col = DefaultLookup.getColor(this, ui,
					"Tree.dropCellForeground");
			if (col != null)
			{
				fg = col;
			} else
			{
				fg = getTextSelectionColor();
			}

			isDropCell = true;
		} else if (sel)
		{
			fg = getTextSelectionColor();
		} else
		{
			fg = getTextNonSelectionColor();
		}

		setForeground(fg);

		Icon icon = null;
		if (leaf)
		{
			icon = getLeafIcon();
		} else if (expanded)
		{
			icon = getOpenIcon();
		} else
		{
			icon = getClosedIcon();
		}

		if (!tree.isEnabled())
		{
			setEnabled(false);
			LookAndFeel laf = UIManager.getLookAndFeel();
			Icon disabledIcon = laf.getDisabledIcon(tree, icon);
			if (disabledIcon != null)
				icon = disabledIcon;
			setDisabledIcon(icon);
		} else
		{
			setEnabled(true);
			setIcon(icon);
		}
		setComponentOrientation(tree.getComponentOrientation());

		selected = sel;

		if (selected){
			setBackground(Color.RED);
		}
		return this;
	}

	/**
	 * Paints the value. The background is filled based on selected.
	 */
	public void paint(Graphics g)
	{
		Color bColor;

		if (isDropCell)
		{
			bColor = DefaultLookup
					.getColor(this, ui, "Tree.dropCellBackground");
			if (bColor == null)
			{
				bColor = getBackgroundSelectionColor();
			}
		} else if (selected)
		{
			bColor = getBackgroundSelectionColor();
		} else
		{
			bColor = getBackgroundNonSelectionColor();
			if (bColor == null)
			{
				bColor = getBackground();
			}
		}

		int imageOffset = -1;
		if (bColor != null && fillBackground && selected)
		{
			Icon currentI = getIcon();

			imageOffset = getLabelStart();
			g.setColor(bColor);
			if (getComponentOrientation().isLeftToRight())
			{
				g.fillRect(imageOffset, 0, getWidth() - imageOffset,
						getHeight());
			} else
			{
				g.fillRect(0, 0, getWidth() - imageOffset, getHeight());
			}
		}

		if (hasFocus)
		{
			if (drawsFocusBorderAroundIcon)
			{
				imageOffset = 0;
			} else if (imageOffset == -1)
			{
				imageOffset = getLabelStart();
			}
			if (getComponentOrientation().isLeftToRight())
			{
				paintFocus(g, imageOffset, 0, getWidth() - imageOffset,
						getHeight(), bColor);
			} else
			{
				paintFocus(g, 0, 0, getWidth() - imageOffset, getHeight(),
						bColor);
			}
		}
		super.paint(g);
	}

	private void paintFocus(Graphics g, int x, int y, int w, int h,
			Color notColor)
	{
		Color bsColor = getBorderSelectionColor();

		if (bsColor != null && (selected || !drawDashedFocusIndicator))
		{
			g.setColor(bsColor);
			g.drawRect(x, y, w - 1, h - 1);
		}
		if (drawDashedFocusIndicator && notColor != null)
		{
			if (treeBGColor != notColor)
			{
				treeBGColor = notColor;
				focusBGColor = new Color(~notColor.getRGB());
			}
			g.setColor(Color.RED);
			BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
		}
	}

	private int getLabelStart()
	{
		Icon currentI = getIcon();
		if (currentI != null && getText() != null)
		{
			return currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
		}
		return 0;
	}

	public Dimension getPreferredSize()
	{
		Dimension retDimension = super.getPreferredSize();

		if (retDimension != null)
			retDimension = new Dimension(retDimension.width + 3,
					retDimension.height);
		return retDimension;
	}

	public void validate()
	{
	}

	public void invalidate()
	{
	}

	public void revalidate()
	{
	}

	public void repaint(long tm, int x, int y, int width, int height)
	{
	}

	public void repaint(Rectangle r)
	{
	}

	public void repaint()
	{
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue)
	{
		// Strings get interned...
		if (propertyName == "text"
				|| ((propertyName == "font" || propertyName == "foreground")
						&& oldValue != newValue && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null))
		{

			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	public void firePropertyChange(String propertyName, byte oldValue,
			byte newValue)
	{
	}

	public void firePropertyChange(String propertyName, char oldValue,
			char newValue)
	{
	}

	public void firePropertyChange(String propertyName, short oldValue,
			short newValue)
	{
	}

	public void firePropertyChange(String propertyName, int oldValue,
			int newValue)
	{
	}

	public void firePropertyChange(String propertyName, long oldValue,
			long newValue)
	{
	}

	public void firePropertyChange(String propertyName, float oldValue,
			float newValue)
	{
	}

	public void firePropertyChange(String propertyName, double oldValue,
			double newValue)
	{
	}

	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue)
	{
	}

}
