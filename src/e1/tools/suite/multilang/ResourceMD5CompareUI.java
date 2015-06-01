package e1.tools.suite.multilang;

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
import java.util.concurrent.CountDownLatch;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

import com.mims.core.CoreUI;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFLabel;
import com.mims.swing.ctrl.JFLabelContainer;
import com.mims.swing.ctrl.JFOptionPane;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

import e1.tools.betraygods.multilang.MultiMD5Check;
import e1.tools.suite.IProgress;
import e1.tools.suite.texture.SpriteSheetThread;

public class ResourceMD5CompareUI extends CoreUI implements IProgress
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5808696221826159213L;

	private JFPanel panelContainer;
	
	private JFTextField txtNewVerFolder;
	private JFLabelContainer labelNewVerFolder;
	
	private JFTextField txtPreVerFolder;
	private JFLabelContainer labelPreVerFolder;
	
	private JFTextField txtResultFolder;
	private JFLabelContainer labelResultFolder;
	
	private JFButton btnCommit;
	
	private JFLabel labelFolderDes;
	
	private JProgressBar progressBarTotal;
	private JProgressBar progressBarCurrent;
	
	public ResourceMD5CompareUI(Map uiContext, String title) 
	{
		super(uiContext, title);
		
		initCtrls();
		initActions();
		startDrag(txtNewVerFolder);
		startDrag(txtPreVerFolder);
		startDrag(txtResultFolder);
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
						String newVerFolder = txtNewVerFolder.getText().trim();
						String preVerFolder = txtPreVerFolder.getText().trim();
						String resultFolder = txtResultFolder.getText().trim();
						if (newVerFolder == null || newVerFolder.equalsIgnoreCase(""))
						{
							JFOptionPane.showMessageDialog(ResourceMD5CompareUI.this, "New Version folder can't be null.", "Error", JFOptionPane.ERROR_MESSAGE);
							return;
						}
						if (preVerFolder == null || preVerFolder.equalsIgnoreCase(""))
						{
							JFOptionPane.showMessageDialog(ResourceMD5CompareUI.this, "Pre Version folder can't be null.", "Error", JFOptionPane.ERROR_MESSAGE);
							return;
						}
						if (resultFolder == null || resultFolder.equalsIgnoreCase(""))
						{
							JFOptionPane.showMessageDialog(ResourceMD5CompareUI.this, "Result folder can't be null.", "Error", JFOptionPane.ERROR_MESSAGE);
							return;
						}
						
						btnCommit.setEnabled(false);
						
						CountDownLatch countDown = new CountDownLatch(1);
						
						new ResourceMD5CompareThread((IProgress)ResourceMD5CompareUI.this, newVerFolder, preVerFolder, resultFolder, countDown).start();
						
						try 
						{
							countDown.await();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						btnCommit.setEnabled(true);
					}
				}).start();
			}
		});
	}

	public void startDrag(JComponent target)//定义的拖拽方法
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
                        ((JFTextField)dtde.getDropTargetContext().getComponent()).setText(list.get(0).getAbsolutePath());
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
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		panelContainer.setBackground(Color.LIGHT_GRAY);
		panelContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelContainer);
		panelContainer.setLayout(new FlexLayout());
		panelContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 416));
		
		labelNewVerFolder = new JFLabelContainer("新版本目录");
		labelNewVerFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtNewVerFolder = new JFTextField();
		txtNewVerFolder.setEditable(false);
		labelNewVerFolder.setEditor(txtNewVerFolder);
		panelContainer.add(labelNewVerFolder);
		
		labelPreVerFolder = new JFLabelContainer("合并版本目录");
		labelPreVerFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtPreVerFolder = new JFTextField();
		txtPreVerFolder.setEditable(false);
		labelPreVerFolder.setEditor(txtPreVerFolder);
		panelContainer.add(labelPreVerFolder);
		
		labelResultFolder = new JFLabelContainer("目标目录");
		labelResultFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtResultFolder = new JFTextField();
		txtResultFolder.setEditable(false);
		labelResultFolder.setEditor(txtResultFolder);
		panelContainer.add(labelResultFolder);
		
		labelFolderDes = new JFLabel();
		StringBuilder msgDes = new StringBuilder();
		msgDes.append("<html><font color='#A31A00'>");
		msgDes.append("* 比较'新版本目录'与'合并版本目录',将前者新增和与后者MD5不一样的文件复制到'目标目录'.<br>");
		msgDes.append("* 拖动最新版本、上次合并版本及资源输出目录到对应文本框内.<br>");
		msgDes.append("</font></html>");
		labelFolderDes.setText(msgDes.toString());
		labelFolderDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 100, 520, 54, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelContainer.add(labelFolderDes);
		
		btnCommit = new JFButton();
		btnCommit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(760, 536, 120, 27, LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		add(btnCommit);
		btnCommit.setText("提交");
		
		progressBarTotal = new JProgressBar();
		progressBarTotal.setOrientation(JProgressBar.HORIZONTAL);
		progressBarTotal.setMinimum(0);
		progressBarTotal.setMaximum(100);
		progressBarTotal.setValue(0);
		progressBarTotal.setString("");
		progressBarTotal.setStringPainted(true);
		
		progressBarTotal.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 496, 880, 12, LayoutProperty.BOTTOM  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		add(progressBarTotal);
		
		progressBarCurrent = new JProgressBar();
		progressBarCurrent.setOrientation(JProgressBar.HORIZONTAL);
		progressBarCurrent.setMinimum(0);
		progressBarCurrent.setMaximum(100);
		progressBarCurrent.setValue(0);
		progressBarCurrent.setString("");
		progressBarCurrent.setStringPainted(true);
	       
		progressBarCurrent.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 516, 880, 12, LayoutProperty.BOTTOM  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		add(progressBarCurrent);
		
	}

	public void notifyProgress(int value, int max, String text) 
	{
		progressBarCurrent.setValue(value);
		progressBarCurrent.setMaximum(max);
		progressBarCurrent.setString(text);
	}

	public void notifyProgressTotal(int value, int max, String text) 
	{
		progressBarTotal.setValue(value);
		progressBarTotal.setMaximum(max);
		progressBarTotal.setString(text);
	}
}
