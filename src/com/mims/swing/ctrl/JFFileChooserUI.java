/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalFileChooserUI;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-11
 * 
 */
public class JFFileChooserUI extends MetalFileChooserUI
{

	public static ComponentUI createUI(JComponent c) {
        return new JFFileChooserUI((JFileChooser) c);
    }
	
	public JFFileChooserUI(JFileChooser filechooser)
	{
		super(filechooser);
	}

}
