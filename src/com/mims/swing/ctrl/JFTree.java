/**
 * 
 */
package com.mims.swing.ctrl;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-9-3
 * 
 */
public class JFTree extends JTree
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1626794741574062624L;
	
	private final String uiClassID = "JFTreeUI";

	public String getUIClassID()
	{
		return uiClassID;
	}
	
	public JFTree() {
        super();
    }
	
	public JFTree(Object[] value) {
		super(value);
	}
	public JFTree(TreeModel newModel) {
		super(newModel);
	}
	
	public JFTree(TreeNode root) {
		super(root);
	}
}
