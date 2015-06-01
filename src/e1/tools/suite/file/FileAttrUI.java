package e1.tools.suite.file;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
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

import e1.tools.utils.FileUtils;

public class FileAttrUI extends CoreUI implements IDragProcessor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -644371614664888178L;
	
	private JFPanel panelContainer;
	private JFLabel labelFolderDes;
	
	private JTextArea txtAreaFiles;
	
	private JFButton btnCommit;
	
	private List<File> files = null;
	
	public FileAttrUI(Map uiContext, String title) 
	{
		super(uiContext, title);
		initCtrl();
		initAction();
		startDrag(this);
	}

	private void initAction()
	{
		btnCommit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (files == null)
				{
					return;
				}
				for (File file:files)
		        {
		        	FileUtils.setFilesWriteable(file);
		        }
			}
		});
	}

	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		panelContainer = new JFPanel(); 
		panelContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 400, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		panelContainer.setBackground(Color.LIGHT_GRAY);
		panelContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelContainer);
		panelContainer.setLayout(new FlexLayout());
		panelContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 400));
		
		txtAreaFiles = new JTextArea();
		txtAreaFiles.setLineWrap(true);
		txtAreaFiles.setEditable(false);
		
		JScrollPane srcollPanel = new JScrollPane(txtAreaFiles);
		srcollPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 860, 200, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panelContainer.add(srcollPanel);
		
		labelFolderDes = new JFLabel();
		StringBuilder msgDes = new StringBuilder();
		msgDes.append("<html><font color='#A31A00'>");
		msgDes.append("* 拖动目录到空白处，会将所有其包含的文件属性改为可写.");
		msgDes.append("</font></html>");
		labelFolderDes.setText(msgDes.toString());
		labelFolderDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 230, 520, 130, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelContainer.add(labelFolderDes);
		
		btnCommit = new JFButton();
		btnCommit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(760, 556, 120, 27, LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		add(btnCommit);
		btnCommit.setText("提交");
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
	
	public void processDrop(List<File> files) 
	{
		if (files.size() == 0)
		{
			return;
		}
		this.files = files;
		StringBuilder msgDes = new StringBuilder();
		
        for (File file:files)
        {
        	msgDes.append(file.getAbsolutePath()).append("\n");
        }
		txtAreaFiles.setText(msgDes.toString());
	}
}
