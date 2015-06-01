package com.mims.app.aim;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.mims.app.test.TblTestUI;
import com.mims.core.CoreUI;
import com.mims.core.IUIHelper;
import com.mims.core.JFFrame;
import com.mims.core.TreeNodeInfo;
import com.mims.core.UIFactory;
import com.mims.swing.ctrl.JFTabbedPaneManager;
import com.mims.swing.ctrl.JFTree;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

public class AimUI extends CoreUI
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTree menuTree = null;
	
	private MouseListener treeClickListerner = null;
	
	public AimUI(Map uiContext)
	{
		this(uiContext, null);
	}
	public AimUI(Map uiContext, String title)
	{
		super(uiContext, title);
		initCtrl();
		initAction();
		setSize(900, 600);
		setBackground(SwingConst.MAIN_PANEL_BACKGROUND);
	}
	
	private void initCtrl()
	{		
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		menuTree = new JFTree(root);
		menuTree.setRootVisible(false);
		menuTree.setBorder(new JFBorders.TextFieldBorder());
		DefaultMutableTreeNode nodeAcco = new DefaultMutableTreeNode();
		TreeNodeInfo nodeAccoInfo = new TreeNodeInfo();
		nodeAccoInfo.setName("�˺�");
		nodeAccoInfo.setUiName("��ѯ");
		nodeAcco.setUserObject(nodeAccoInfo);
		
				
		DefaultTreeModel model = (DefaultTreeModel)menuTree.getModel();
		DefaultMutableTreeNode nodeView = new DefaultMutableTreeNode();
		TreeNodeInfo node = new TreeNodeInfo();
		node.setName("�˺Ų�ѯ  - ������ҳǩ");
		node.setUiName("��ѯ");
		node.setClassName(AimListUI.class.getName());
		node.setModel(IUIHelper.UIMODEL_NEWTAB);
		nodeView.setUserObject(node);
		
		
		
		DefaultMutableTreeNode nodeProcess = new DefaultMutableTreeNode();
		TreeNodeInfo nodePocs = new TreeNodeInfo();
		nodePocs.setName("�˺Ŵ��� - �����´���");
		nodePocs.setUiName("����");
		nodePocs.setClassName(AimProcessUI.class.getName());
		nodePocs.setModel(IUIHelper.UIMODEL_NEWWIN);
		nodeProcess.setUserObject(nodePocs);
		
		nodeAcco.insert(nodeView, nodeAcco.getChildCount());
		nodeAcco.insert(nodeProcess, nodeAcco.getChildCount());
		
		DefaultMutableTreeNode nodeTbl = new DefaultMutableTreeNode();
		TreeNodeInfo nodeTblInfo = new TreeNodeInfo();
		nodeTblInfo.setName("Table");
		nodeTblInfo.setUiName("��ѯ");
		nodeTblInfo.setClassName(TblTestUI.class.getName());
		nodeTblInfo.setModel(IUIHelper.UIMODEL_NEWTAB);
		nodeTbl.setUserObject(nodeTblInfo);
		
		/**
		 * Dialog����
		 */
		DefaultMutableTreeNode nodeDialog = new DefaultMutableTreeNode();
		TreeNodeInfo nodeDialogInfo = new TreeNodeInfo();
		nodeDialogInfo.setName("Dialog - �����¶Ի���");
		nodeDialogInfo.setUiName("Dialog����  - �����¶Ի���");
		nodeDialogInfo.setClassName(TblTestUI.class.getName());
		nodeDialogInfo.setModel(IUIHelper.UIMODEL_NEWDIA);
		nodeDialog.setUserObject(nodeDialogInfo);
		
		model.insertNodeInto(nodeAcco, root, root.getChildCount());
		model.insertNodeInto(nodeTbl, root, root.getChildCount());
		model.insertNodeInto(nodeDialog, root, root.getChildCount());

		TreeNode[] tempNodes = model.getPathToRoot(nodeView);   
        TreePath path = new TreePath(tempNodes);   
        menuTree.scrollPathToVisible(path);
        menuTree.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 300, 580, LayoutProperty.TOP | LayoutProperty.LEFT 
						| LayoutProperty.BOTTOM));
        menuTree.setBackground(getBackground());

        add(menuTree);
	}
	
	private void initAction()
	{
		treeClickListerner = new MouseAdapter()
		{
			
			public void mouseClicked(MouseEvent e) {
				int count = e.getClickCount();
				if (count == 2)
				{
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)menuTree.getLastSelectedPathComponent();
					TreeNodeInfo menuInfo = (TreeNodeInfo)treeNode.getUserObject();
					if (menuInfo.getClassName() != null)
					{
						CoreUI panel = (CoreUI)JFTabbedPaneManager.getManager().getOpenedTabs().get(menuInfo.getClassName());
						if (panel == null)
						{
							if (menuInfo.getModel() == IUIHelper.UIMODEL_NEWTAB)
							{
								AimUI.this.getUiContext().put("UIParam", menuInfo.getUiParam());
								panel = (CoreUI)UIFactory.getUI(menuInfo.getClassName(), IUIHelper.UIMODEL_NEWTAB, 
										AimUI.this.getUiContext(), menuInfo.getUiName(),AimUI.this);
								panel.setMainUI(AimUI.this);
								panel.registMain(frame);
								
								frame.getMainTablePane().addTab(panel.getTitle(), panel);
								JFTabbedPaneManager.getManager().getOpenedTabs().put(menuInfo.getClassName(), panel);

								frame.getMainTablePane().setSelectedComponent(panel);
							}
							else
							{
								IUIHelper ui = UIFactory.getUI(menuInfo.getClassName(), menuInfo.getModel(),
										AimUI.this.getUiContext(),AimUI.this);
								ui.showUI();
							}
						}
						else
						{
							frame.getMainTablePane().setSelectedComponent(panel);
						}
					}
				}
			}
		};
		menuTree.addMouseListener(treeClickListerner);
	}
	public JComponent getUIObject()
	{
		return this;
	}
	
	public String getTitle()
	{
		return "AIM";
	}

}
