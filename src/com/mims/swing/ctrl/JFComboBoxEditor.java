/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * ����:.<p>
 *
 * @author ���� 
 *
 * @Date: 2011-8-23
 * 
 */
public class JFComboBoxEditor extends BasicComboBoxEditor
{
	protected JTextField createEditorComponent() {
        JTextField editor = new JFTextField("",5);
        return editor;
    }
	
	@Override
	public void setItem(Object anObject)
	{
		super.setItem(anObject);
	}
}
