package e1.tools.suite.effsyn;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.mims.core.CoreUI;
import com.mims.swing.ctrl.JFButton;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

import e1.tools.utils.FileCopyer;

public class EffSynUI extends CoreUI 
{
	private JTextArea txtError = null;
	
	private JFTextField txtSrc = null;

	private JFTextField txtDest = null;

	private JFButton btnSrc = null;

	private JButton btnDest = null;

	private JFButton btnProcess = null;

	private JFileChooser fileChooser = null;

	private File fileSrc;
	private File fileDest;
	private File dataFile;
	
	private StringBuilder errorMsg;

	private Map<String, String> datas = new HashMap<String, String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 7205086244423754718L;

	public EffSynUI(Map uiContext, String title) 
	{
		super(uiContext, title);
		errorMsg = new StringBuilder();
		
		txtError = new JTextArea();
		txtSrc = new JFTextField();
		txtSrc.setEditable(false);
		txtDest = new JFTextField();
		txtDest.setEditable(false);
		btnSrc = new JFButton("Source");

		btnDest = new JFButton("Dest");
		btnProcess = new JFButton("Process");

		this.setLayout(new FlexLayout());
		this.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 400, 400));

		txtSrc.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 280, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		txtDest.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 52, 280, 30, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT));
		btnSrc.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 10, 90, 30, LayoutProperty.TOP
						| LayoutProperty.RIGHT));
		btnDest.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 52, 90, 30, LayoutProperty.TOP
						| LayoutProperty.RIGHT));
		txtError.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 84, 378, 245, LayoutProperty.RIGHT
						| LayoutProperty.BOTTOM | LayoutProperty.LEFT | LayoutProperty.BOTTOM));
		btnProcess.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(300, 340, 90, 30, LayoutProperty.RIGHT
						| LayoutProperty.BOTTOM));
		
		fileChooser = new JFileChooser();

		this.add(txtDest);
		this.add(txtSrc);
		this.add(btnSrc);
		this.add(btnDest);
		this.add(btnProcess);
		this.add(txtError);

		initActions();
		
		initProperties();
		
		startDrag(txtSrc);
		startDrag(txtDest);
	}

	private void initProperties()
	{
		File file = new File("e1effsyn.ini");
		BufferedReader reader = null;
		String line;
		try 
		{
			reader = new BufferedReader(new InputStreamReader
					(new FileInputStream(file)));

			while ((line = reader.readLine()) != null) 
			{
				String[] values = line.split("=");
				if (values[0].equalsIgnoreCase("src"))
				{
					txtSrc.setText(values[1]);
				}
				else if (values[0].equalsIgnoreCase("dest"))
				{
					txtDest.setText(values[1]);
				}
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initActions() 
	{
		btnProcess.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					process(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSrc.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				selectActionPerformanced(e);
			}
		});
		btnDest.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				selectActionPerformanced(e);
			}
		});
	}

	private void process(ActionEvent e) throws IOException 
	{
		errorMsg.setLength(0);
		datas.clear();
		if (fileSrc == null)
		{
			fileSrc = new File(txtSrc.getText());
		}
		if (fileDest == null)
		{
			fileDest = new File(txtDest.getText());
		}
		
		readData();

		prcocessFile(fileSrc);
		Iterator<String> it = datas.keySet().iterator();
		String key = null;
		while (it.hasNext())
		{
			key = it.next();
			errorMsg.append(key);
			errorMsg.append(" is error!");
			errorMsg.append("\n");
		}
		txtError.setText(errorMsg.toString());
	}
	
	private void prcocessFile(File srcFile) throws IOException
	{
		File file;
		File[] files = srcFile.listFiles();
		File destFile;
		String fileFullName;
		String name;
		int index = -1;
		for (int i = 0; i < files.length; i++) 
		{
			file = files[i];
			if (file.isDirectory())
			{
				prcocessFile(file);
			}
			else
			{
				name = file.getName();
				index = name.indexOf(".eff"); 
				if (index == -1)
				{
					continue;
				}
				name = name.substring(0, name.length() - 4);
				if (datas.get(name) == null)
				{
					continue;
				}
				
				fileFullName = fileDest.getAbsolutePath() + "/" + datas.get(name) + ".eff";
				destFile = new File(fileFullName);
				FileCopyer.copyFile(file, destFile);
				datas.remove(name);
			}
			
		}
	}

	private void readData() 
	{
		dataFile = new File(fileSrc.getAbsolutePath() + "/effectsyn.csv");
		BufferedReader reader = null;
		String line;
		try 
		{
			reader = new BufferedReader(new InputStreamReader
					(new FileInputStream(dataFile), "utf-8"));

			while ((line = reader.readLine()) != null) 
			{
				String[] values = line.split(",");
				datas.put(values[0], values[1]);
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			errorMsg.append(e.getMessage());
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				errorMsg.append(e1.getMessage());
				e1.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			errorMsg.append(e.getMessage());
			try 
			{
				reader.close();
			} 
			catch (IOException e1) 
			{
				errorMsg.append(e1.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e) {
			errorMsg.append(e.getMessage());
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				errorMsg.append(e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				errorMsg.append(e.getMessage());
			}
		}
	}

	private void selectActionPerformanced(ActionEvent e) {
		fileChooser.setFileSelectionMode(1);
		int state = fileChooser.showOpenDialog(null);
		if (state == 1) {
			return;
		} else {
			File file = fileChooser.getSelectedFile();
			if (e.getSource() == btnSrc) {
				fileSrc = file;
				txtSrc.setText(file.getAbsolutePath());
			} else {
				fileDest = file;
				txtDest.setText(file.getAbsolutePath());
			}
		}

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
                        String temp="";
                        for(File file:list)
                            temp+=file.getAbsolutePath()+";\n";
                        ((JFTextField)dtde.getDropTargetContext().getComponent()).setText(temp);
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

}
