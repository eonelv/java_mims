/**
 * 
 */
package com.mims.swing.ctrl.util;

import java.awt.Component;

/**
 * ����:.<p>
 *
 * @author ���� 
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
