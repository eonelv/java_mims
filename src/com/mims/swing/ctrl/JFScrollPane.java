/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-11
 * 
 */
public class JFScrollPane extends JScrollPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4199653145726682526L;

	public JFScrollPane(Component view, int vsbPolicy, int hsbPolicy) 
    {
		super(view, vsbPolicy, hsbPolicy);
    }
	public JFScrollPane(Component view) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
	
	public JFScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }

    public JFScrollPane() {
        this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    public JScrollBar createVerticalScrollBar() {
        return new JFScrollBar(JScrollBar.VERTICAL);
    }
    
    public JScrollBar createHorizontalScrollBar() {
        return new JFScrollBar(JScrollBar.HORIZONTAL);
    }
}
