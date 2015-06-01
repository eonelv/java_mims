package e1.tools.suite.texture;

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

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JProgressBar;

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

import e1.tools.utils.FileUtils;

public class Png2TextureUI extends CoreUI implements IDragProcessor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5046042645848199705L;
	
	private JFPanel panelNormalContainer;
	private JFTextField txtWidth;
	private JFTextField txtHeight;
	private JFTextField txtNameFormat;
	
	private JFLabelContainer labelWidth;
	private JFLabelContainer labelHeight;
	private JFLabelContainer labelNameFormat;
	
	private JFLabel labelNameFormaDes;
	private JCheckBox checkBoxSpriteSheet;
	private JCheckBox checkBoxDXT5;
	
	private JFPanel panelFolderContainer;
	
	private JFTextField txtPNGFolder;
	private JFLabelContainer labelPNGFolder;
	
	private JFTextField txtATFFolder;
	private JFLabelContainer labelATFFolder;
	private JCheckBox checkBoxRemoveATFFolder;
	
	private JFTextField txtSVNFolder;
	private JFLabelContainer labelSVNFolder;
	
	private JFLabel labelFolderDes;
	
	private JFButton btnCommit;
	
	private JProgressBar progressBarTotal;
	private JProgressBar progressBarCurrent;
	
	
	private int twidth;
	private int theight;
	private String nameFormat;
	private String rootFolder;
	
	public Png2TextureUI(Map uiContext, String title) 
	{
		super(uiContext, title);
		initCtrls();
		initActions();
		startDrag(this);
	}
	
	private void initCtrls()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		panelNormalContainer = new JFPanel(); 
		panelNormalContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 150, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		panelNormalContainer.setBackground(Color.LIGHT_GRAY);
		panelNormalContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelNormalContainer);
		
		panelNormalContainer.setLayout(new FlexLayout());
		panelNormalContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 150));
		
		labelWidth = new JFLabelContainer("Width");
		labelWidth.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtWidth = new JFTextField();
		txtWidth.setText("64");
		labelWidth.setEditor(txtWidth);
		panelNormalContainer.add(labelWidth);
		
		labelHeight = new JFLabelContainer("Height");
		labelHeight.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 10, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtHeight = new JFTextField();
		txtHeight.setText("64");
		labelHeight.setEditor(txtHeight);
		panelNormalContainer.add(labelHeight);
		
		labelNameFormat = new JFLabelContainer("Name Format");
		labelNameFormat.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtNameFormat = new JFTextField();
		txtNameFormat.setText("#");
		labelNameFormat.setEditor(txtNameFormat);
		panelNormalContainer.add(labelNameFormat);
		
		labelNameFormaDes = new JFLabel();
		labelNameFormaDes.setText("为空使用原始名称. item-#.atf, #为替换部分");
		labelNameFormaDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 40, 270, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelNormalContainer.add(labelNameFormaDes);
		
		checkBoxSpriteSheet = new JCheckBox();
		checkBoxSpriteSheet.setText("生成Sprite Sheet");
		checkBoxSpriteSheet.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelNormalContainer.add(checkBoxSpriteSheet);
		checkBoxSpriteSheet.setSelected(true);
		
		checkBoxDXT5 = new JCheckBox();
		checkBoxDXT5.setText("块压缩");
		checkBoxDXT5.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 100, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelNormalContainer.add(checkBoxDXT5);
		checkBoxDXT5.setSelected(true);
		
		panelFolderContainer = new JFPanel(); 
		panelFolderContainer.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 168, 880, 318, LayoutProperty.TOP  
						| LayoutProperty.LEFT | LayoutProperty.RIGHT | LayoutProperty.BOTTOM));
		panelFolderContainer.setBackground(Color.LIGHT_GRAY);
		panelFolderContainer.setBorder(new JFBorders.JFComboBoxListBorder());
		add(panelFolderContainer);
		panelFolderContainer.setLayout(new FlexLayout());
		panelFolderContainer.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 880, 318));
		
		labelPNGFolder = new JFLabelContainer("PNG Folder");
		labelPNGFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 380, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtPNGFolder = new JFTextField();
		txtPNGFolder.setEditable(false);
		labelPNGFolder.setEditor(txtPNGFolder);
		panelFolderContainer.add(labelPNGFolder);
		
		labelATFFolder = new JFLabelContainer("ATF Folder");
		labelATFFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 40, 380, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		txtATFFolder = new JFTextField();
		txtATFFolder.setEditable(false);
		labelATFFolder.setEditor(txtATFFolder);
		panelFolderContainer.add(labelATFFolder);
		
		labelSVNFolder = new JFLabelContainer("SVN Folder");
		labelSVNFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 70, 380, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		
		checkBoxRemoveATFFolder = new JCheckBox();
		checkBoxRemoveATFFolder.setText("删除临时文件及目录");
		checkBoxRemoveATFFolder.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(400, 40, 300, 27, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFolderContainer.add(checkBoxRemoveATFFolder);
		checkBoxRemoveATFFolder.setSelected(true);
		
		txtSVNFolder = new JFTextField();
		txtSVNFolder.setEditable(false);
		labelSVNFolder.setEditor(txtSVNFolder);
		panelFolderContainer.add(labelSVNFolder);
		
		labelFolderDes = new JFLabel();
		StringBuilder msgDes = new StringBuilder();
		msgDes.append("<html><font color='#A31A00'>");
		msgDes.append("* 将PNG Folder中的png图片打包成宽高为2的幂,并生成对应的描述XML及ATF到svn目录.<br>");
		msgDes.append("* 拖动包含原始png图片的目录到空白出,会自动填充对应的工作目录.<br>");
		msgDes.append("* 拖动的目录内包含src、dest、svn子目录.");
		msgDes.append("</font></html>");
		labelFolderDes.setText(msgDes.toString());
		labelFolderDes.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 100, 568, 60, LayoutProperty.TOP  
						| LayoutProperty.LEFT));
		panelFolderContainer.add(labelFolderDes);
		
		btnCommit = new JFButton();
		btnCommit.setText("提交");
		btnCommit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(760, 536, 120, 27, LayoutProperty.BOTTOM  
						| LayoutProperty.RIGHT));
		add(btnCommit);
		
		progressBarTotal = new JProgressBar();
		progressBarTotal.setOrientation(JProgressBar.HORIZONTAL);
		progressBarTotal.setMinimum(0);
		progressBarTotal.setMaximum(2);
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
	
	private void initActions()
	{
		btnCommit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String strWidth = txtWidth.getText();
				String strHeight = txtWidth.getText();
				
				try
				{
					twidth = Integer.parseInt(strWidth);
					theight = Integer.parseInt(strHeight);
				}
				catch (Exception e)
				{
					JFOptionPane.showMessageDialog(Png2TextureUI.this, "width or height must be number", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				nameFormat = txtNameFormat.getText();
				if (nameFormat == null || "".equalsIgnoreCase(nameFormat))
				{
					JFOptionPane.showMessageDialog(Png2TextureUI.this, "name format can't be null", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (rootFolder == null || rootFolder.equalsIgnoreCase(""))
				{
					JFOptionPane.showMessageDialog(Png2TextureUI.this, "workspace folder can't be null", "Error", JFOptionPane.ERROR_MESSAGE);
					return;
				}
				
				btnCommit.setEnabled(false);
				File srcFile = new File(rootFolder + "\\src");
				int count = FileUtils.calcFileCount(srcFile, 0);
				progressBarCurrent.setMaximum(count);
				progressBarTotal.setMaximum(2);
				
				new Thread(new Runnable() 
				{
					public void run() 
					{
						progressBarTotal.setValue(0);
						progressBarTotal.setString("0/2");
						
						progressBarCurrent.setValue(0);
						progressBarCurrent.setString("0/"+progressBarCurrent.getMaximum());
						
						File destFile = new File(rootFolder + "\\dest");
						if (destFile.exists())
						{
							FileUtils.clearFiles(destFile);
						}
						destFile.mkdirs();
						File svnFile = new File(rootFolder+"\\svn");
						if (svnFile.exists())
						{
							FileUtils.clearFiles(svnFile);
						}
						svnFile.mkdirs();
						File srcFile = new File(rootFolder+"\\src");
						File temp = null;
						
						CountDownLatch countDown = null;
						String renameSrcPath = null;
						if (checkBoxSpriteSheet.isSelected())
						{
							countDown = new CountDownLatch(1);
							
							new SpriteSheetThread(new File(rootFolder + "\\src"), progressBarCurrent, rootFolder, countDown).start();
							
							try 
							{
								countDown.await();
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
							renameSrcPath = rootFolder+"\\dest";
							temp = destFile;
						}
						else
						{
							renameSrcPath = rootFolder+"\\src";
							temp = srcFile;
						}
						
						progressBarTotal.setValue(1);
						progressBarTotal.setString("1/2");
						
						countDown = new CountDownLatch(1);
						
						int count = FileUtils.calcFileCount(temp, 0);
						
						progressBarCurrent.setValue(0);
						progressBarCurrent.setMaximum(count);
						
						new RenameThread(progressBarCurrent,renameSrcPath, nameFormat, rootFolder+"\\svn", countDown, checkBoxDXT5.isSelected()).start();
						
						try 
						{
							countDown.await();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						progressBarTotal.setValue(2);
						progressBarTotal.setString("2/2");
						btnCommit.setEnabled(true);
					}
				}).start();
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
		rootFolder = path;
		txtPNGFolder.setText(path + "\\src");
		txtATFFolder.setText(path + "\\dest");
		txtSVNFolder.setText(path + "\\svn");
	}

}
