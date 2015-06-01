package e1.tools.suite.tps2tamf;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mims.core.CoreUI;
import com.mims.core.IDragProcessor;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFLabel;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

import e1.tools.suite.IProgress;

public class TPS2TamfUI extends CoreUI implements IDragProcessor, IProgress
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7091683700411498731L;
	
	private JFPanel panelContainer;
	
	private JTextArea txtAreaFiles;
	
	private JFButton btnCommit;
	
	private JFLabel labelFolderDes;
	
	private JProgressBar progressBar;
	
	private List<File> tpsFiles = new ArrayList<File>();
	
	public TPS2TamfUI(Map uiContext, String title) 
	{
		super(uiContext, title);
		initCtrls();
		initActions();
		startDrag(this);
	}
	
	private void initActions()
	{
		btnCommit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				new Thread(new Runnable()
				{
					public void run() 
					{
						btnCommit.setEnabled(false);
						TPS2Tamf amf = new TPS2Tamf(TPS2TamfUI.this);
						amf.process(tpsFiles);
						btnCommit.setEnabled(true);
					}
					
				}).start();
			}
		});
	}
	
	public void processDrop(List<File> files) 
	{
		tpsFiles.clear();
		int size = files.size();
		File file = null;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < size; i++)
		{
			file = files.get(i);
			if (file.isDirectory())
			{
				continue;
			}
			if (!file.getName().endsWith(".tps"))
			{
				continue;
			}
			sb.append(file.getAbsolutePath()).append("\r\n");
			tpsFiles.add(file);
		}
		txtAreaFiles.setText(sb.toString());
	}
	
	private void startDrag(JComponent target)//定义的拖拽方法
    {
        //panel表示要接受拖拽的控件
        new DropTarget(target, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() 
        {
            public void drop(DropTargetDropEvent dtde)//重写适配器的drop方法
            {
                try
                {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
                        List<File> list =  (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));

                        ((IDragProcessor)dtde.getDropTargetContext().getComponent()).processDrop(list);
                        dtde.dropComplete(true);//指示拖拽操作已完成
                    }
                    else
                    {
                        dtde.rejectDrop();//否则拒绝拖拽来的数据
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
	
	private void initCtrls()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		panelContainer = new JFPanel(); 
		panelContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 416, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panelContainer.setBackground(Color.LIGHT_GRAY);
		panelContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelContainer);
		panelContainer.setLayout(new FlexLayout());
		panelContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 416));
		
		txtAreaFiles = new JTextArea();
		txtAreaFiles.setLineWrap(true);
		txtAreaFiles.setEditable(false);
		
		JScrollPane srcollPanel = new JScrollPane(txtAreaFiles);
		srcollPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 860, 356, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panelContainer.add(srcollPanel);
		
		labelFolderDes = new JFLabel();
		StringBuilder msgDes = new StringBuilder();
		msgDes.append("<html><font color='#A31A00'>");
		msgDes.append("* 拖动一个或多个tps文件到文本框内.<br>");
		msgDes.append("</font></html>");
		labelFolderDes.setText(msgDes.toString());
		labelFolderDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 366, 520, 54, LayoutProperty.BOTTOM  
						| LayoutProperty.LEFT));
		panelContainer.add(labelFolderDes);
		
		btnCommit = new JFButton();
		btnCommit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(760, 536, 120, 27, LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		add(btnCommit);
		btnCommit.setText("提交");
		
		progressBar = new JProgressBar();
		progressBar.setOrientation(JProgressBar.HORIZONTAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setString("");
		progressBar.setStringPainted(true);
		
		progressBar.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 496, 880, 12, LayoutProperty.BOTTOM  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		add(progressBar);
		
	}

	public void notifyProgress(int value, int max, String text) 
	{
		progressBar.setValue(value);
		progressBar.setMaximum(max);
		progressBar.setString(value + "/" + max);
	}

	public void notifyProgressTotal(int value, int max, String text) 
	{
		
	}
}
