/**
 * 
 */
package com.mims.swing.ctrl.event;

import javax.swing.event.EventListenerList;

/**
 * 
 * ÃèÊö:.<p>
 *
 * @author ÀîÍş 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class DataChangeModel
{
	
	private EventListenerList listenerList = null;
	
	public DataChangeModel(EventListenerList lists){
		listenerList = lists;
	}
	
	public void addDataChangeListener(DataChangeListener listenter){
		listenerList.add(DataChangeListener.class, listenter);
	}
	
	public void fireDataChanged(DataChangeEvent event) 
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == DataChangeListener.class) {
                ((DataChangeListener)listeners[i+1]).dataChanged(event);
            }          
        }
    } 
}
