/**
 * 
 */
package com.mims.swing.ctrl;

import java.util.Date;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.mims.swing.ctrl.event.DataChangeEvent;
import com.mims.swing.ctrl.event.DataChangeListener;
import com.mims.swing.ctrl.event.DataChangeModel;
import com.mims.swing.ctrl.event.IDataChangeSupport;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-12
 * 
 */
public class JFDatePicker extends JComponent implements IDataAceesser, IDataChangeSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5720346045276362424L;
	
	private DataChangeEvent event = null;
	
	private static String uiClassID = "JFDatePickerUI";
	
	private DataChangeModel dataChangeModel = null;
	
	public String getUIClassID()
	{
		return uiClassID;
	}
	
	private IDatePickerEdtor editor = null;
	
	public JFDatePicker(){
		updateUI();
		dataChangeModel = new DataChangeModel(listenerList);
	}

	public void setUserObject(Object obj)
	{
		Object old = editor.getData();
		editor.setData(obj);
		event = new DataChangeEvent(this, DataChangeListener.DATA_CHANGE_EVENT, old, obj);
		fireDataChanged();
	}

	public Object getUserObject()
	{
		return editor.getData();
	}
	
	public Date getData(){
		return (Date)editor.getData();
	}

	public IDatePickerEdtor getEditor()
	{
		return editor;
	}

	public void setEditor(IDatePickerEdtor editor)
	{
		this.editor = editor;
	}
	
	public void updateUI() {
        setUI((JFDatePickerUI)UIManager.getUI(this));
        invalidate();
    }

    public JFDatePickerUI getUI() {
        return (JFDatePickerUI)ui;
    }


    public void setUI(JFDatePickerUI ui) {
        super.setUI(ui);
    }
    
    public void addDataChangeListener(DataChangeListener listenter){
    	dataChangeModel.addDataChangeListener(listenter);
    }
    
    public void fireDataChanged() 
    {
    	if (event == null) {
        	event = new DataChangeEvent(this, DataChangeListener.DATA_CHANGE_EVENT,
        			editor.getData(),editor.getData());
        }
    	dataChangeModel.fireDataChanged(event);
    }   
	
}
