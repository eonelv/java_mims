/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Point;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.MenuItemUI;
import javax.swing.plaf.PopupMenuUI;

import com.mims.swing.ctrl.painter.IComponentPainter;
import com.mims.swing.ctrl.util.ResourceUtils;

/**
 * 描述:.<p>
 *
 * @author 李威 
 *
 * @Date: 2011-8-14
 * 
 */
public class JFMenu extends JMenu implements IPainterVisitor
{
	private final String uiClassID = "JFMenuUI";
	
	private IComponentPainter painter;
	
	private JFPopupMenu popupMenu;
	
	private Point customMenuLocation = null;
	
	private static ImageIcon buttonmage = null;   //菜单标题图标

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFMenu()
	{
		this(null, null);
	}

	/**
	 * Creates a button with an icon.
	 * 
	 * @param icon
	 *            the Icon image to display on the button
	 */
	public JFMenu(Icon icon)
	{
		this(null, icon);
	}

	/**
	 * Creates a button with text.
	 * 
	 * @param text
	 *            the text of the button
	 */
	public JFMenu(String text)
	{
		this(text, null);
	}

	/**
	 * Creates a button where properties are taken from the <code>Action</code>
	 * supplied.
	 * 
	 * @param a
	 *            the <code>Action</code> used to specify the new button
	 * 
	 * @since 1.3
	 */
	public JFMenu(Action a)
	{
		this();
		setAction(a);
	}
	
	public JFMenu(String text, Icon icon)
	{
		// Create the model
		setModel(new DefaultButtonModel());

		// initialize
		init(text, icon);
		getPopupMenu().setBorder(null);

	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI() {
        setUI((MenuItemUI)UIManager.getUI(this));

        if ( popupMenu != null )
          {
            popupMenu.setUI((PopupMenuUI)UIManager.getUI(popupMenu));
          }

    }
	
	protected void paintBorder(Graphics g) {
        Border border = getBorder();
        if (border != null) {
            border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }
	
	public Border getBorder()
	{
		return null;
	}
	
	public boolean isPopupMenuVisible() {
        ensurePopupMenuCreated();
        return popupMenu.isVisible();
    }
	private void ensurePopupMenuCreated() {
        if (popupMenu == null) {            
            final JMenu thisMenu = this;
            this.popupMenu = new JFPopupMenu();
            popupMenu.setInvoker(this);
            popupListener = createWinListener(popupMenu);
        }
    }
	
	private Point getCustomMenuLocation() {
	 	return customMenuLocation;
	    } 
	
	public void setMenuLocation(int x, int y) {
	 	customMenuLocation = new Point(x, y);
	        if (popupMenu != null)
		    popupMenu.setLocation(x, y);
	    }
	public JMenuItem add(JMenuItem menuItem) {
        ensurePopupMenuCreated();
        return popupMenu.add(menuItem);
    }

    /**
     * Appends a component to the end of this menu.
     * Returns the component added.
     *
     * @param c the <code>Component</code> to add
     * @return the <code>Component</code> added
     */
    public Component add(Component c) {
        ensurePopupMenuCreated();
        popupMenu.add(c);
        return c;
    }

    /** 
     * Adds the specified component to this container at the given 
     * position. If <code>index</code> equals -1, the component will
     * be appended to the end.
     * @param     c   the <code>Component</code> to add
     * @param     index    the position at which to insert the component
     * @return    the <code>Component</code> added
     * @see	  #remove
     * @see java.awt.Container#add(Component, int)
     */
    public Component add(Component c, int index) {
        ensurePopupMenuCreated();
        popupMenu.add(c, index);
        return c;
    }

    /**
     * Creates a new menu item with the specified text and appends
     * it to the end of this menu.
     *  
     * @param s the string for the menu item to be added
     */
    public JMenuItem add(String s) {
        return add(new JFMenuItem(s));
    }
    
    public JMenuItem add(Action a) {
    	JMenuItem mi = createActionComponent(a);
            mi.setAction(a);
            add(mi);
            return mi;
        }
    
    protected JMenuItem createActionComponent(Action a) {
        JMenuItem mi = new JFMenuItem() {
	    protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
		PropertyChangeListener pcl = createActionChangeListener(this);
		if (pcl == null) {
		    pcl = super.createActionPropertyChangeListener(a);
		}
		return pcl;
	    }
	};
        mi.setHorizontalTextPosition(JButton.TRAILING);
        mi.setVerticalTextPosition(JButton.CENTER);
	return mi;
    }
    
    public void addSeparator()
    {
        ensurePopupMenuCreated();
        popupMenu.addSeparator();
    }

    /**
     * Inserts a new menu item with the specified text at a 
     * given position.
     *
     * @param s the text for the menu item to add
     * @param pos an integer specifying the position at which to add the 
     *               new menu item
     * @exception IllegalArgumentException when the value of 
     *			<code>pos</code> < 0
     */
    public void insert(String s, int pos) {
        if (pos < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }

        ensurePopupMenuCreated();
        popupMenu.insert(new JMenuItem(s), pos);
    }

    /**
     * Inserts the specified <code>JMenuitem</code> at a given position.
     *
     * @param mi the <code>JMenuitem</code> to add
     * @param pos an integer specifying the position at which to add the 
     *               new <code>JMenuitem</code>
     * @return the new menu item
     * @exception IllegalArgumentException if the value of 
     *			<code>pos</code> < 0
     */
    public JMenuItem insert(JMenuItem mi, int pos) {
        if (pos < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }
        ensurePopupMenuCreated();
        popupMenu.insert(mi, pos);
        return mi;
    }

    /**
     * Inserts a new menu item attached to the specified <code>Action</code> 
     * object at a given position.
     *
     * @param a the <code>Action</code> object for the menu item to add
     * @param pos an integer specifying the position at which to add the 
     *               new menu item
     * @exception IllegalArgumentException if the value of 
     *			<code>pos</code> < 0
     */
    public JMenuItem insert(Action a, int pos) {
        if (pos < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }

        ensurePopupMenuCreated();
        JMenuItem mi = new JMenuItem(a);
        mi.setHorizontalTextPosition(JButton.TRAILING);
        mi.setVerticalTextPosition(JButton.CENTER);
        popupMenu.insert(mi, pos);
        return mi;
    }

    /**
     * Inserts a separator at the specified position.
     *
     * @param       index an integer specifying the position at which to 
     *                    insert the menu separator
     * @exception   IllegalArgumentException if the value of 
     *                       <code>index</code> < 0
     */
    public void insertSeparator(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }

        ensurePopupMenuCreated();
        popupMenu.insert( new JPopupMenu.Separator(), index );
    }
    
    public void remove(JMenuItem item) {
        if (popupMenu != null)
	    popupMenu.remove(item);
    }

    /**
     * Removes the menu item at the specified index from this menu.
     *
     * @param       pos the position of the item to be removed
     * @exception   IllegalArgumentException if the value of 
     *                       <code>pos</code> < 0, or if <code>pos</code>
     *			     is greater than the number of menu items
     */
    public void remove(int pos) {
        if (pos < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }
        if (pos > getItemCount()) {
            throw new IllegalArgumentException("index greater than the number of items.");
        }
        if (popupMenu != null)
	    popupMenu.remove(pos);
    }

    /**
     * Removes the component <code>c</code> from this menu.
     *
     * @param       c the component to be removed
     */
    public void remove(Component c) {
        if (popupMenu != null)
	    popupMenu.remove(c);
    }

    /**
     * Removes all menu items from this menu.
     */
    public void removeAll() {
        if (popupMenu != null)
	    popupMenu.removeAll();
    }

    /**
     * Returns the number of components on the menu.
     *
     * @return an integer containing the number of components on the menu
     */
    public int getMenuComponentCount() {
        int componentCount = 0;
        if (popupMenu != null)
            componentCount = popupMenu.getComponentCount();
        return componentCount;
    }

    /**
     * Returns the component at position <code>n</code>.
     *
     * @param n the position of the component to be returned
     * @return the component requested, or <code>null</code>
     *			if there is no popup menu
     *
     */
    public Component getMenuComponent(int n) {
        if (popupMenu != null)
            return popupMenu.getComponent(n);
        
        return null;
    }

    /**
     * Returns an array of <code>Component</code>s of the menu's
     * subcomponents.  Note that this returns all <code>Component</code>s
     * in the popup menu, including separators.
     *
     * @return an array of <code>Component</code>s or an empty array
     *		if there is no popup menu
     */
    public Component[] getMenuComponents() {
        if (popupMenu != null)
            return popupMenu.getComponents();
        
        return new Component[0];
    }

    /**
     * Returns true if the menu is a 'top-level menu', that is, if it is
     * the direct child of a menubar.
     *
     * @return true if the menu is activated from the menu bar;
     *         false if the menu is activated from a menu item
     *         on another menu
     */
    public boolean isTopLevelMenu() {
        if (getParent() instanceof JFMenuBar)
            return true;
        
        return false;
    }
    
    public JPopupMenu getPopupMenu() {
        ensurePopupMenuCreated();
        return popupMenu;
    }
    
    public MenuElement[] getSubElements() {
        if(popupMenu == null)
            return new MenuElement[0];
        else {
            MenuElement result[] = new MenuElement[1];
            result[0] = popupMenu;
            return result;
        }
    }
    
    public Component getComponent() {
        return this;
    }
    
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        
        if ( popupMenu != null ) {
            int ncomponents = getMenuComponentCount();
            for (int i = 0 ; i < ncomponents ; ++i) {
                getMenuComponent(i).applyComponentOrientation(o);
            }
            popupMenu.setComponentOrientation(o);
        }
    }

    public void setComponentOrientation(ComponentOrientation o) {
        super.setComponentOrientation(o);
        if ( popupMenu != null ) {
            popupMenu.setComponentOrientation(o);
        }
    }

	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}
}
