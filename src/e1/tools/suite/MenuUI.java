package e1.tools.suite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.mims.core.CoreUI;
import com.mims.core.IUIHelper;
import com.mims.core.TreeNodeInfo;
import com.mims.core.UIFactory;
import com.mims.swing.ctrl.JFTabbedPaneManager;
import com.mims.swing.ctrl.JFTree;
import com.mims.swing.ctrl.SwingConst;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;


public class MenuUI extends CoreUI
{
	private static Logger logger = Logger.getLogger(MenuUI.class);
	
	private JTree menuTree = null;
	
	private MouseListener treeClickListerner = null;
	
	private Map<String, DefaultMutableTreeNode> menuInfos;
	private List<TreeNodeInfo> rootNodeInfos = new ArrayList<TreeNodeInfo>();
	
	public MenuUI(Map uiContext)
	{
		this(uiContext, null);
	}
	
	public MenuUI(Map uiContext, String title)
	{
		super(uiContext, title);
		try 
		{
			initCtrl();
		} 
		catch (URISyntaxException e) 
		{
			logger.error(e.getMessage(), e);
		} 
		catch (IOException e) 
		{
			logger.error(e.getMessage(), e);
		}
		initAction();
		setSize(900, 600);
		setBackground(SwingConst.MAIN_PANEL_BACKGROUND);
	}
	
	private void initCtrl() throws URISyntaxException, IOException
	{		
		setLayout(new FlexLayout());
		putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 900, 600));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		menuTree = new JFTree(root);
		menuTree.setRootVisible(false);
		menuTree.setBorder(new JFBorders.TextFieldBorder());
		
		loadMenus();
		
		DefaultTreeModel model = (DefaultTreeModel)menuTree.getModel();
		
		DefaultMutableTreeNode treeNode = null;
		
		buildTree(rootNodeInfos, model, root);

		treeNode = menuInfos.get(rootNodeInfos.get(0).getID());
		TreeNode[] tempNodes = model.getPathToRoot(treeNode);   
        TreePath path = new TreePath(tempNodes);   
        menuTree.scrollPathToVisible(path);
        menuTree.expandPath(path);
        
        menuTree.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 10, 300, 580, LayoutProperty.TOP | LayoutProperty.LEFT 
						| LayoutProperty.BOTTOM));
        menuTree.setBackground(getBackground());

        add(menuTree);
	}
	
	private void buildTree(List<TreeNodeInfo> nodes, DefaultTreeModel model, DefaultMutableTreeNode root)
	{
		int size = nodes.size();
		TreeNodeInfo node = null;
		DefaultMutableTreeNode treeNode = null;
		DefaultMutableTreeNode parentTreeNode = null;
		List<TreeNodeInfo> children;
		for (int i=0; i < size; i++)
		{
			node = nodes.get(i);
			treeNode = menuInfos.get(node.getID());
			parentTreeNode = menuInfos.get(node.getParentID());
			if (parentTreeNode == null)
			{
				model.insertNodeInto(treeNode, root, root.getChildCount());
			}
			else
			{
				parentTreeNode.insert(treeNode, parentTreeNode.getChildCount());
			}
			children = node.getChildren();
			Collections.sort(children, Collections.reverseOrder());
			if (children != null)
			{
				buildTree(children, model, root);
			}
		}
	}
	
	private void loadMenus() throws URISyntaxException, IOException
	{
		menuInfos = new HashMap<String, DefaultMutableTreeNode>();
		URL url = MenuUI.class.getClassLoader().getResource("menu.properties");
		
		File file = new File(url.toURI());
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "utf-8"));
		
		String[] menuInfo;
		DefaultMutableTreeNode treeNode = null;
		TreeNodeInfo node = null;
		Map<String, TreeNodeInfo> nodes = new HashMap<String, TreeNodeInfo>();
		while ((line = reader.readLine()) != null) 
		{
			if (line.startsWith("#"))
			{
				continue;
			}
			menuInfo = line.split("-");
			treeNode = new DefaultMutableTreeNode();
			node = new TreeNodeInfo();
			node.setID(menuInfo[0]);
			node.setParentID(menuInfo[1]);
			
			node.setName(menuInfo[2]);
			node.setUiName(menuInfo[3]);
			node.setClassName(menuInfo[4]);
			node.setModel(Integer.parseInt(menuInfo[5]));
			
			nodes.put(node.getID(), node);
			
			treeNode.setUserObject(node);
			
			menuInfos.put(node.getID(), treeNode);
		}
		Iterator<String> itID = nodes.keySet().iterator();
		String nodeID = null;
		TreeNodeInfo parentNode = null;
		while (itID.hasNext())
		{
			nodeID = itID.next();
			node = nodes.get(nodeID);
			parentNode = nodes.get(node.getParentID());
			
			if (parentNode == null)
			{
				rootNodeInfos.add(node);
			}
			else
			{
				parentNode.getChildren().add(node);
			}
		}
	}
	
	private void initAction()
	{
		treeClickListerner = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) 
			{
				int count = e.getClickCount();
				if (count != 2)
				{
					return;
				}
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)menuTree.getLastSelectedPathComponent();
				if (treeNode == null)
				{
					return;
				}
				TreeNodeInfo menuInfo = (TreeNodeInfo)treeNode.getUserObject();
				if (menuInfo.getClassName() == null)
				{
					return;
				}
				CoreUI panel = (CoreUI)JFTabbedPaneManager.getManager().getOpenedTabs().get(menuInfo.getClassName());
				if (panel == null)
				{
					if (menuInfo.getModel() == IUIHelper.UIMODEL_NEWTAB)
					{
						MenuUI.this.getUiContext().put("UIParam", menuInfo.getUiParam());
						panel = (CoreUI)UIFactory.getUI(menuInfo.getClassName(), IUIHelper.UIMODEL_NEWTAB, 
								MenuUI.this.getUiContext(), menuInfo.getUiName(),MenuUI.this);
						if (panel == null)
						{
							return;
						}
						panel.setMainUI(MenuUI.this);
						panel.registMain(frame);
						
						frame.getMainTablePane().addTab(panel.getTitle(), panel);
						JFTabbedPaneManager.getManager().getOpenedTabs().put(menuInfo.getClassName(), panel);

						frame.getMainTablePane().setSelectedComponent(panel);
					}
					else
					{
						IUIHelper ui = UIFactory.getUI(menuInfo.getClassName(), menuInfo.getModel(),
								MenuUI.this.getUiContext(),MenuUI.this);
						ui.showUI();
					}
				}
				else
				{
					frame.getMainTablePane().setSelectedComponent(panel);
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
		return "客户端工具集";
	}
}
