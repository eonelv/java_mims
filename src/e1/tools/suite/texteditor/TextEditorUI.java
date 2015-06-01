package e1.tools.suite.texteditor;

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mims.core.CoreUI;
import com.mims.core.IDragProcessor;
import com.mims.core.UIUtils;
import com.mims.swing.ctrl.JFOptionPane;
import com.mims.swing.ctrl.JFScrollBar;
import com.mims.swing.ctrl.JFScrollPane;
import com.mims.swing.ctrl.JFScrollPaneLayout;
import com.mims.swing.ctrl.JFTextField;
import com.mims.swing.ctrl.JFToolButton;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.ctrl.table.IRow;
import com.mims.swing.ctrl.table.JFTable;
import com.mims.swing.ctrl.table.JFTableHeaderInfo;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

public class TextEditorUI extends CoreUI implements IDragProcessor
{

	private JFTextField txtEditor;
	private JFTable tblMain = null;
	
	private JFileChooser chooser = null;
	
	private JFToolButton btnFilter = null;
	private JFToolButton btnSave = null;
	
	private File file = null;
	
	private List tools = new ArrayList();
	
	private List datas = new ArrayList();
	
	private int editRow = -1;
	private int editCol = -1;
	
	public TextEditorUI(Map uiContext)
	{
		this(uiContext, null);
	}
	
	public TextEditorUI(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
		initAction();
		startDrag(this);
		initTableData(this);
	}

	public void processDrop(List<File> files) 
	{
		file = files.get(0);
		title = file.getName();
		boolean isSucc = processDatas(file);
		if (!isSucc)
		{
			JFOptionPane.showConfirmDialog(TextEditorUI.this, "�ļ���ʽ������ѡ��txt��csv", "���ļ�ʧ��", JFOptionPane.OK_OPTION);
			return;
		}
		initTable();
		JFScrollPane scrollPane = new JFScrollPane(tblMain);
		scrollPane.setLayout(new JFScrollPaneLayout());
		
		scrollPane.setVerticalScrollBar(new JFScrollBar());
		scrollPane.setHorizontalScrollBar(new JFScrollBar(Adjustable.HORIZONTAL));

		scrollPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 60, 880, 530, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT
						| LayoutProperty.BOTTOM));

		add(scrollPane);
		insertTab(file);
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

	private void initCtrl()
	{
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		txtEditor = new JFTextField();
		txtEditor.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 880, 30, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT));
		txtEditor.addFocusListener(new FocusListener()
		{

			public void focusGained(FocusEvent e)
			{
				
			}

			public void focusLost(FocusEvent e)
			{
				if (tblMain == null)
				{
					return;
				}
				int row = tblMain.getSelectedRow();
				int col = tblMain.getSelectedColumn();
				if (editRow == row && editCol == col && row != -1 && col != -1)
				{
					tblMain.setValueAt(txtEditor.getText(), row, col);
				}
			}
			
		});

		add(txtEditor);
		btnFilter = new JFToolButton("选择文件");
		btnSave = new JFToolButton("保存");
		
		chooser = UIUtils.getFileChooser(JFileChooser.FILES_ONLY, new String[]{"txt", "csv"}, "txt&csv");
		tools.add(btnFilter);
		tools.add(btnSave);
	}
	
	private void initTable()
	{
		TableColumnModel model = new DefaultTableColumnModel();
		TableColumn column = new TableColumn();

		JFTableHeaderInfo columnInfo = new JFTableHeaderInfo();
		List headersDatas = (List)datas.get(0);
		for (int i = 0; i < headersDatas.size(); i++)
		{
			columnInfo = new JFTableHeaderInfo();
			column = new TableColumn();
			columnInfo.setAlign(SwingConstants.LEFT);
			columnInfo.setName((String)headersDatas.get(i));
			columnInfo.setWidth(SwingConst.DEFAULT_HEADER_WIDTH);
			column.setHeaderValue(columnInfo);
			model.addColumn(column);
		}
		
		tblMain = new JFTable(model);
		tblMain.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent arg0) 
			{
				int row = tblMain.getSelectedRow();
				int col = tblMain.getSelectedColumn();
				editRow = row;
				editCol = col;
				if (col == 0)
				{
					return;
				}
				txtEditor.setText(tblMain.getValueAt(row, col).toString());
			}
		});
				
		tblMain.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				int row = tblMain.getSelectedRow();
				int col = tblMain.getSelectedColumn();
				editRow = row;
				editCol = col;
				if (col == 0)
				{
					return;
				}
				txtEditor.setText(tblMain.getValueAt(row, col).toString());
			}

			public void mouseEntered(MouseEvent e)
			{
				
			}

			public void mouseExited(MouseEvent e)
			{
				
			}

			public void mousePressed(MouseEvent e)
			{
				
			}

			public void mouseReleased(MouseEvent e)
			{
				
			}
			
		});
		tblMain.addKeyListener(new KeyListener()
		{

			public void keyPressed(KeyEvent e)
			{
				if (e.isControlDown())
				{
					if (e.getKeyCode() == KeyEvent.VK_F)
					{
						System.out.println("DDDD");
					}
				}
			}

			public void keyReleased(KeyEvent e)
			{
				
			}

			public void keyTyped(KeyEvent e)
			{
				
			}
			
		});
	}

	private void initAction()
	{		
		btnFilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				initTableData(TextEditorUI.this);
			}
		});
		btnSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int result = JFOptionPane.showConfirmDialog(TextEditorUI.this,"确定保存文件" + file.getName(), "确定保存文件 - " + file.getName(), JFOptionPane.YES_NO_OPTION);
				if (result == JFOptionPane.NO_OPTION)
				{
					return;
				}
				List datasWrite = new ArrayList();
				List rowDatas = null;
				
				rowDatas = new ArrayList();
				datasWrite.add(datas.get(0));
				int rowCount = tblMain.getRowCount();
				int colCount = tblMain.getColumnCount();
				for (int i=0; i < rowCount; i++)
				{
					rowDatas = new ArrayList();
					for (int j=1; j < colCount; j++)
					{
						rowDatas.add(tblMain.getValueAt(i, j));
					}
					datasWrite.add(rowDatas);
				}
				
				writeFile(file, datasWrite);
			}
			
		});
	}

	private void initTableData(Component parent)
	{
		if (getUiContext().get("UIParam") != null)
		{
			file = new File((String) getUiContext().get("UIParam"));
			insertTab(file);
			return;
		}
//		int state = chooser.showOpenDialog(parent);
//		if (state == 1)
//		{
//			return;
//		} 
//		else
//		{
//			file = chooser.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
//			title = file.getName();
//			boolean isSucc = processDatas(file);
//			if (!isSucc)
//			{
//				JFOptionPane.showConfirmDialog(TextEditorUI.this, "�ļ���ʽ������ѡ��txt��csv", "���ļ�ʧ��", JFOptionPane.OK_OPTION);
//				return;
//			}
//			initTable();
//			JFScrollPane scrollPane = new JFScrollPane(tblMain);
//			scrollPane.setLayout(new JFScrollPaneLayout());
//			
//			scrollPane.setVerticalScrollBar(new JFScrollBar());
//			scrollPane.setHorizontalScrollBar(new JFScrollBar(Adjustable.HORIZONTAL));
//
//			scrollPane.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
//					new LayoutProperty(10, 60, 880, 530, LayoutProperty.TOP
//							| LayoutProperty.RIGHT | LayoutProperty.LEFT
//							| LayoutProperty.BOTTOM));
//
//			add(scrollPane);
//			insertTab(file);
//		}
	}
	
	private void writeFile(File file, List datas)
	{
		StringBuffer outMsg = new StringBuffer();
		List rows;
		for (int i=0; i < datas.size(); i++)
		{
			rows = (List)datas.get(i);
			for (int j=0; j < rows.size(); j++)
			{
				outMsg.append(rows.get(j));
				outMsg.append("\t");
			}
			outMsg.append("\r\n");
		}
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.write(outMsg.toString());
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			JFOptionPane.showConfirmDialog(TextEditorUI.this,"文件" + file.getName() + "保存失败", "文件 - " + file.getName(), JFOptionPane.OK_OPTION);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			JFOptionPane.showConfirmDialog(TextEditorUI.this, "文件" + file.getName() + "保存失败", "文件 - " + file.getName(), JFOptionPane.OK_OPTION);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JFOptionPane.showConfirmDialog(TextEditorUI.this, "�ļ�" + file.getName() + "����ʧ�ܣ�", "�����ļ� - " + file.getName(), JFOptionPane.OK_OPTION);
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	private boolean processDatas(File file)
	{
		BufferedReader reader = null;
		String line = null;
		int index = file.getName().lastIndexOf('.');
		String ext = file.getName().substring(index+1);
		String spliter;
		if (ext.equalsIgnoreCase("txt"))
		{
			spliter = "\t";
		}
		else if (ext.equalsIgnoreCase("csv"))
		{
			spliter = " ";
		}
		else
		{
			return false;
		}
		try
		{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			while ((line = reader.readLine()) != null)
			{
				String[] columns = line.split(spliter);
				List columnList = new ArrayList();
				for (int i=0; i < columns.length; i++)
				{
					columnList.add(columns[i]);
				}
				datas.add(columnList);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}

	private void insertTab(File file)
	{

		List rowDatas = null;
		List tempDatas = null;
		int colCount = tblMain.getColumnCount();
		
		for (int i = 1; i < datas.size(); i++)
		{
			rowDatas = (List) datas.get(i);
			if (rowDatas.size() >= colCount)
			{
				tempDatas = rowDatas.subList(0, colCount - 1);
			}
			else
			{
				tempDatas = rowDatas;
			}
			IRow row = tblMain.addRow(tempDatas.toArray());
		}
//		IRow row = tblMain.addRow(new Object[]{});
//		JCheckBox editor = new JCheckBox();
//		editor.setSelected(true);
//		row.getCell(4).setCellEditor(new JFCellEditor(editor));
//		row.getCell(4).setCellRenderer(new JFCheckBoxCellRender());
//		
//		row.getCell(4).setValue(false);
//		row.getCell(4).setBackGround(Color.YELLOW);

	}
	
	public List getTool()
	{
		return tools;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
