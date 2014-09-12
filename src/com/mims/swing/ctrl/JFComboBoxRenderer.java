/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;
import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-23
 * 
 */
public class JFComboBoxRenderer extends JFLabel
implements ListCellRenderer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2522790221065912895L;
	
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private final static Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
	
	public JFComboBoxRenderer(){
		super();
        setOpaque(true);
        setBorder(getNoFocusBorder());
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		if (isSelected)
		{
			setBackground(SwingConst.COMBOBOX_SELECTEDITEM_BACKGROUND);
			setForeground(list.getSelectionForeground());
			list.setSelectionBackground(SwingConst.COMBOBOX_SELECTEDITEM_BACKGROUND);
		} else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setFont(list.getFont());

		if (value instanceof Icon)
		{
			setIcon((Icon) value);
		} else
		{
			setText((value == null) ? "" : value.toString());
		}
		return this;
	}
    
    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }
    
    public Dimension getPreferredSize() {
        Dimension size;
        
        if ((this.getText() == null) || (this.getText().equals( "" ))) {
            setText( " " );
            size = super.getPreferredSize();
            setText( "" );
        }
        else {
            size = super.getPreferredSize();
        }
        
        return size;
    }
	
}
