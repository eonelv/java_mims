/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-8-23
 * 
 */
public class JFTextField extends JTextField implements IDataAceesser
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256000318377572840L;
	
	private Object userObject = null;

	private final String uiClassID = "JFTextFieldUI";
	
//	public JFTextField(){
//		super();
//		this.setBorder(new JFBorders.TextFieldBorder());
//	}
	
	public String getUIClassID()
	{
		return uiClassID;
	}
	
	@Override
	protected void paintBorder(Graphics g)
	{
		super.paintBorder(g);
	}
	
	public JFTextField() {
        this(null, null, 0);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text. A default model is created and the number of
     * columns is 0.
     *
     * @param text the text to be displayed, or <code>null</code>
     */
    public JFTextField(String text) {
        this(null, text, 0);
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * @param columns  the number of columns to use to calculate 
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */ 
    public JFTextField(int columns) {
        this(null, null, columns);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the
     * specified text and columns.  A default model is created.
     *
     * @param text the text to be displayed, or <code>null</code>
     * @param columns  the number of columns to use to calculate 
     *   the preferred width; if columns is set to zero, the
     *   preferred width will be whatever naturally results from
     *   the component implementation
     */
    public JFTextField(String text, int columns) {
        this(null, text, columns);
    }
    
    public JFTextField(Document doc, String text, int columns) {
    	super(doc,text,columns);
    }

	public void setUserObject(Object obj)
	{
		userObject = obj;
	}

	public Object getUserObject()
	{
		return userObject;
	}

}
