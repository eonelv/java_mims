/**
 * 
 */
package com.mims.swing.ctrl.event;

import java.util.EventListener;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public interface DataChangeListener extends EventListener
{
	public static int DATA_CHANGE_EVENT = 100;
	
	public void dataChanged(DataChangeEvent event);
}
