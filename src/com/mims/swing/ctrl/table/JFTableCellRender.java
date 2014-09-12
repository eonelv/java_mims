package com.mims.swing.ctrl.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import sun.swing.DefaultLookup;

import com.mims.swing.ctrl.SwingConst;

public class JFTableCellRender extends JLabel implements TableCellRenderer
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

	public JFTableCellRender()
	{
		super();
		setOpaque(true);
        setBorder(getNoFocusBorder());
        setName("Table.cellRenderer");
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{

		if (isSelected)
		{
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else
		{
//			Color background = unselectedBackground != null ? unselectedBackground
//					: table.getBackground();
//			if (background == null
//					|| background instanceof javax.swing.plaf.UIResource)
//			{
//				Color alternateColor = DefaultLookup.getColor(this, ui,
//						"Table.alternateRowColor");
//				if (alternateColor != null && row % 2 == 0)
//					background = alternateColor;
//			}
//			super.setForeground(unselectedForeground != null ? unselectedForeground
//					: table.getForeground());
//			super.setBackground(background);
			super.setForeground(table.getForeground());
			super.setBackground(table.getBackground());
			if (column == 0 )
			{
				super.setBackground(SwingConst.TABLE_COLUMN_INDEX);
			}
		}

		setFont(table.getFont());

		if (hasFocus)
		{
			Border border = null;
			if (isSelected)
			{
				border = DefaultLookup.getBorder(this, ui,
						"Table.focusSelectedCellHighlightBorder");
			}
			if (border == null)
			{
				border = DefaultLookup.getBorder(this, ui,
						"Table.focusCellHighlightBorder");
			}
			if (column != 0)
			{
				setBorder(border);
			}

			if (!isSelected && table.isCellEditable(row, column))
			{
				Color col;
				col = DefaultLookup.getColor(this, ui,
						"Table.focusCellForeground");
				if (col != null)
				{
					super.setForeground(col);
				}
				col = DefaultLookup.getColor(this, ui,
						"Table.focusCellBackground");
				if (col != null)
				{
					super.setBackground(col);
				}
			}
		} else
		{
			setBorder(getNoFocusBorder());
		}

		JTableHeader header = table.getTableHeader();
		setTableColumnAlign(header, column);

		this.setText((value == null) ? "" : value.toString());
		return this;
	}

	private void setTableColumnAlign(JTableHeader header, int col)
	{
		TableColumnModel columns = header.getColumnModel();
		TableColumn column = columns.getColumn(col);
		column.getModelIndex();
		Object value = column.getHeaderValue();
		JFTableHeaderInfo info = (JFTableHeaderInfo) value;

		if (info != null)
		{
			setHorizontalAlignment(info.getAlign());
		} else
		{
			setHorizontalAlignment(0);
		}

	}
	
	private Border getNoFocusBorder() {
        Border border = DefaultLookup.getBorder(this, ui, "Table.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else {
            if (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER) {
                return border;
            }
            return noFocusBorder;
        }
    }

	
	public void invalidate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     */
    public void validate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     */
    public void revalidate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     */
    public void repaint(long tm, int x, int y, int width, int height) {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     */
    public void repaint(Rectangle r) { }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     *
     * @since 1.5
     */
    public void repaint() {
    }
    
    public boolean isOpaque() { 
    	Color back = getBackground();
    	Component p = getParent(); 
    	if (p != null) { 
    	    p = p.getParent(); 
    	}
            
    	// p should now be the JTable. 
    	boolean colorMatch = (back != null) && (p != null) && 
    	    back.equals(p.getBackground()) && 
    			p.isOpaque();
    	return !colorMatch && super.isOpaque(); 
        }
    
    public void updateUI() {
        super.updateUI(); 
	setForeground(null);
	setBackground(null);
    }
}
