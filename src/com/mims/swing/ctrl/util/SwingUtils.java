/**
 * 
 */
package com.mims.swing.ctrl.util;

import java.awt.Component;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 
 *
 * @Date: 2011-8-20
 * 
 */
public class SwingUtils
{

	public static boolean isLeftToRight( Component c ) {
        return c.getComponentOrientation().isLeftToRight();
    }
}
