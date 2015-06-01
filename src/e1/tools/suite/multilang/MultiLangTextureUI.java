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

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.mims.core.CoreUI;
import com.mims.core.IDragProcessor;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFLabel;
import com.mims.swing.ctrl.JFLabelContainer;
import com.mims.swing.ctrl.JFOptionPane;
import com.mims.swing.ctrl.JFPanel;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

import e1.tools.betraygods.multilang.MultiLang;
import e1.tools.suite.SystemParams;
import e1.tools.suite.texture.Png2TextureUI;

public class MultiLangTextureUI extends CoreUI implements IDragProcessor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5804472396370834476L;
	
	private JFPanel panelFlagContainer;
	private JFPanel panelFolderContainer;
	
	private JCheckBox checkBoxPublishTps;
	private JCheckBox checkBoxBuildAtf;
	private JCheckBox checkBoxBuildTamf;
	private JCheckBox checkBoxCopyFile;
	private JCheckBox checkBoxCopyMapFile;
	
	private JFTextField txtRootFolder;
	private JFLabelContainer labelRootFolder;
	
	private JFTextField txtTpsFolder;
	private JFLabelContainer labelTpsFolder;
	
	private JFTextField txtTpsPublishFolder;
	private JFLabelContainer labelTpsPublishFolder;
	
	private JFTextField txtTpsMapFolder;
	private JFLabelContainer labelTpsMapFolder;
	
	private JFTextField txtTamfFolder;
	private JFLabelContainer labelTamfFolder;
	
	private JFTextField txtOutFolder;
	private JFLabelContainer labelOutFolder;
	
	private JFTextField txtOutMapFolder;
	private JFLabelContainer labelOutMapFolder;
	
	private JFButton btnCommit;
	
	private JFLabel labelFolderDes;
	
	public MultiLangTextureUI(Map uiContext, String title) 
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
				int flag = 0;
				if (checkBoxPublishTps.isSelected())
				{
					flag |= 1;
				}
				if (checkBoxBuildAtf.isSelected())
				{
					flag |= 2;
				}
				if (checkBoxBuildTamf.isSelected())
				{
					flag |= 4;
				}
				if (checkBoxCopyFile.isSelected())
				{
					flag |= 8;
				}
				if (checkBoxCopyMapFile.isSelected())
				{
					flag |= 16;
				}
				String rootPath = txtRootFolder.getText();
				if (rootPath== null || rootPath.equalsIgnoreCase(""))
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace folder can't be null.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				File file = new File(rootPath);
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				file = new File(rootPath + "/ui/export");
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				file = new File(rootPath + "/ui/deploy");
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				file = new File(rootPath + "/ui/deploy/maps");
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				file = new File(rootPath + "/out/gui");
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				file = new File(rootPath + "/out/maps");
				if (!file.exists())
				{
					JFOptionPane.showMessageDialog(MultiLangTextureUI.this, "Workspace is not a correct folder.", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				btnCommit.setEnabled(false);
				MultiLang multiLang = new MultiLang();
				multiLang.process(rootPath, SystemParams.TEXTURE_PACKER, SystemParams.PNG2ATF, flag);
				btnCommit.setEnabled(true);
			}
		});
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
                        String temp="";
                        for(File file:list)
                            temp+=file.getAbsolutePath()+";\n";
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
		String path = files.get(0).getAbsolutePath();
		txtRootFolder.setText(path);
		txtTpsFolder.setText(path + "/ui/export");
		txtTpsPublishFolder.setText(path + "/ui/deploy");
		txtTpsMapFolder.setText(path + "/ui/deploy/maps");
		txtTamfFolder.setText(path + "/ui/deploy");
		txtOutFolder.setText(path + "/out/gui");
		txtOutMapFolder.setText(path + "/out/maps");
	}
	
	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		panelFlagContainer = new JFPanel(); 
		panelFlagContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 150, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		panelFlagContainer.setBackground(Color.LIGHT_GRAY);
		panelFlagContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelFlagContainer);
		panelFlagContainer.setLayout(new FlexLayout());
		panelFlagContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 150));
		
		checkBoxPublishTps = new JCheckBox();
		checkBoxPublishTps.setText("发布TPS");
		checkBoxPublishTps.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFlagContainer.add(checkBoxPublishTps);
		checkBoxPublishTps.setSelected(true);
		
		checkBoxBuildAtf = new JCheckBox();
		checkBoxBuildAtf.setText("发布ATF");
		checkBoxBuildAtf.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(320, 10, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFlagContainer.add(checkBoxBuildAtf);
		checkBoxBuildAtf.setSelected(true);
		
		checkBoxBuildTamf = new JCheckBox();
		checkBoxBuildTamf.setText("发布TAMF");
		checkBoxBuildTamf.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFlagContainer.add(checkBoxBuildTamf);
		checkBoxBuildTamf.setSelected(true);
		
		checkBoxCopyFile = new JCheckBox();
		checkBoxCopyFile.setText("复制TAMF");
		checkBoxCopyFile.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(320, 40, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFlagContainer.add(checkBoxCopyFile);
		checkBoxCopyFile.setSelected(true);
		
		checkBoxCopyMapFile = new JCheckBox();
		checkBoxCopyMapFile.setText("复制MAP");
		checkBoxCopyMapFile.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFlagContainer.add(checkBoxCopyMapFile);
		checkBoxCopyMapFile.setSelected(true);
		
		panelFolderContainer = new JFPanel(); 
		panelFolderContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 168, 880, 378, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panelFolderContainer.setBackground(Color.LIGHT_GRAY);
		panelFolderContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelFolderContainer);
		panelFolderContainer.setLayout(new FlexLayout());
		panelFolderContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 378));
		btnCommit = new JFButton();
		btnCommit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(760, 556, 120, 27, LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		add(btnCommit);
		btnCommit.setText("提交");
		
		labelRootFolder = new JFLabelContainer("工作目录");
		labelRootFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtRootFolder = new JFTextField();
		txtRootFolder.setEditable(false);
		labelRootFolder.setEditor(txtRootFolder);
		panelFolderContainer.add(labelRootFolder);
		
		labelTpsFolder = new JFLabelContainer("TPS目录");
		labelTpsFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtTpsFolder = new JFTextField();
		txtTpsFolder.setEditable(false);
		labelTpsFolder.setEditor(txtTpsFolder);
		panelFolderContainer.add(labelTpsFolder);
		
		labelTpsPublishFolder = new JFLabelContainer("TPS发布目录");
		labelTpsPublishFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtTpsPublishFolder = new JFTextField();
		txtTpsPublishFolder.setEditable(false);
		labelTpsPublishFolder.setEditor(txtTpsPublishFolder);
		panelFolderContainer.add(labelTpsPublishFolder);
		
		labelTpsMapFolder = new JFLabelContainer("TPS MAP目录");
		labelTpsMapFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 100, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtTpsMapFolder = new JFTextField();
		txtTpsMapFolder.setEditable(false);
		labelTpsMapFolder.setEditor(txtTpsMapFolder);
		panelFolderContainer.add(labelTpsMapFolder);
		
		labelTamfFolder = new JFLabelContainer("TAMF目录");
		labelTamfFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 130, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtTamfFolder = new JFTextField();
		txtTamfFolder.setEditable(false);
		labelTamfFolder.setEditor(txtTamfFolder);
		panelFolderContainer.add(labelTamfFolder);
		
		labelOutFolder = new JFLabelContainer("资源发布目录");
		labelOutFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 160, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtOutFolder = new JFTextField();
		txtOutFolder.setEditable(false);
		labelOutFolder.setEditor(txtOutFolder);
		panelFolderContainer.add(labelOutFolder);
		
		labelOutMapFolder = new JFLabelContainer("资源发布MAP");
		labelOutMapFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 190, 468, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtOutMapFolder = new JFTextField();
		txtOutMapFolder.setEditable(false);
		labelOutMapFolder.setEditor(txtOutMapFolder);
		panelFolderContainer.add(labelOutMapFolder);
		
		labelFolderDes = new JFLabel();
		StringBuilder msgDes = new StringBuilder();
		msgDes.append("<html><font color='#A31A00'>");
		msgDes.append("* 共包含5个功能:<br>");
		msgDes.append("1. 发布TPS目录中的所有tps文件<br>");
		msgDes.append("2. 将TPS发布目录下的所有png大图,通过目录中*.cmd定义的参数,生成atf<br>");
		msgDes.append("3. 将TPS MAP发布目录下的所有png大图,通过目录中*.cmd定义的参数,生成atf<br>");
		msgDes.append("4. 将TAMF目录下的所有atf&xml打包成tamf<br>");
		msgDes.append("5. 按out目录中的位置，复制ui目录中最新的资源文件到out目录<br>");
		msgDes.append("* 拖动包含资源根目录到空白出,会自动填充对应的工作目录,资源目录包含/ui、/out.<br>");
		msgDes.append("</font></html>");
		labelFolderDes.setText(msgDes.toString());
		labelFolderDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 210, 520, 130, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFolderContainer.add(labelFolderDes);
	}
}
