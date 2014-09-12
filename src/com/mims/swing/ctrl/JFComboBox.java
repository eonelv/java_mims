/**
 * 
 */
package com.mims.swing.ctrl;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.mims.swing.ctrl.event.DataChangeEvent;
import com.mims.swing.ctrl.event.DataChangeListener;
import com.mims.swing.ctrl.event.DataChangeModel;
import com.mims.swing.ctrl.event.IDataChangeSupport;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-23
 * 
 */
public class JFComboBox extends JComboBox implements IDataChangeSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1386470690766893503L;
	
	private final String uiClassID = "JFComboBoxUI";
	
	private DataChangeModel dataChangeModel = null;
	
	private DataChangeEvent event = null;

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	public JFComboBox(ComboBoxModel aModel) {
		super(aModel);
		dataChangeModel = new DataChangeModel(listenerList);
    }

    public JFComboBox(final Object items[]) {
    	super(items);
    	dataChangeModel = new DataChangeModel(listenerList);
    }

    public JFComboBox(Vector<?> items) {
        super(items);
        dataChangeModel = new DataChangeModel(listenerList);
    }

    public JFComboBox() {
        super();
        dataChangeModel = new DataChangeModel(listenerList);
    }
    
    
    @Override
    public void setSelectedIndex(int anIndex)
    {
    	Object old = getSelectedItem();
    	super.setSelectedIndex(anIndex);
    	event = new DataChangeEvent(this, DataChangeListener.DATA_CHANGE_EVENT,
    			old,getSelectedItem());
    	fireDataChanged();
    }
    
    @Override
    public void setSelectedItem(Object anObject)
    {
    	event = new DataChangeEvent(this, DataChangeListener.DATA_CHANGE_EVENT,
    			getSelectedItem(),anObject);
    	super.setSelectedItem(anObject);
    	fireDataChanged();
    }

	public void fireDataChanged()
	{
		dataChangeModel.fireDataChanged(event);
	}

	public void addDataChangeListener(DataChangeListener listenter)
	{
		dataChangeModel.addDataChangeListener(listenter);
	}
}
