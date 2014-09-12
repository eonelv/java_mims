/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.UIResource;

import com.mims.swing.ctrl.painter.IComponentPainter;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-9
 * 
 */
public class JFToolBar extends JToolBar implements IPainterVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6702666797323430698L;

	private final String uiClassID = "JFToolBarUI";
	
	private IComponentPainter painter;
	
	private JFArrowButton arrowBtn = null;

//	static
//	{
//		UIManager.getDefaults().put("JFToolBarUI",
//				"com.mims.swing.ctrl.JFToolBarUI");
//	}

	public String getUIClassID()
	{
		return uiClassID;
	}

	public JFToolBar()
	{
		this(HORIZONTAL);
	}

	/**
	 * Creates a new tool bar with the specified <code>orientation</code>. The
	 * <code>orientation</code> must be either <code>HORIZONTAL</code> or
	 * <code>VERTICAL</code>.
	 * 
	 * @param orientation
	 *            the orientation desired
	 */
	public JFToolBar(int orientation)
	{
		this(null, orientation);
	}
	
	public Component add(Component comp) {
		
		//由于有的应用程序有动态改变菜单的需求，可能删除全部所有工具按钮，以致JFArrowButton也没有了
		//当添加第一个控件时，动态加上
		if (arrowBtn == null || getComponentCount() == 0){
			arrowBtn = new JFArrowButton(SwingConstants.EAST);
			addImpl(arrowBtn, null, -1);
		}
        addImpl(comp, null, -1);
        return comp;
    }

	/**
	 * Creates a new tool bar with the specified <code>name</code>. The name is
	 * used as the title of the undocked tool bar. The default orientation is
	 * <code>HORIZONTAL</code>.
	 * 
	 * @param name
	 *            the name of the tool bar
	 * @since 1.3
	 */
	public JFToolBar(String name)
	{
		this(name, HORIZONTAL);
	}

	/**
	 * Creates a new tool bar with a specified <code>name</code> and
	 * <code>orientation</code>. All other constructors call this constructor.
	 * If <code>orientation</code> is an invalid value, an exception will be
	 * thrown.
	 * 
	 * @param name
	 *            the name of the tool bar
	 * @param orientation
	 *            the initial orientation -- it must be either
	 *            <code>HORIZONTAL</code> or <code>VERTICAL</code>
	 * @exception IllegalArgumentException
	 *                if orientation is neither <code>HORIZONTAL</code> nor
	 *                <code>VERTICAL</code>
	 * @since 1.3
	 */
	public JFToolBar(String name, int orientation)
	{
		super(name, orientation);
//		setBackground(UIManager.getDefaults().getColor("TOOLBAR_BACKGROUND"));
		JFToolBarLayout layout = new JFToolBarLayout(orientation);
		setLayout(layout);
		
		addPropertyChangeListener(layout);
		updateUI();
		//正常情况直接在构造函数中增加箭头按钮,如果有应用有移除所有工具栏的操作，使用Add方法增加箭头按钮
		add(new JFArrowButton(SwingConstants.EAST));
		
		setBorder(null);
	}

	public void removeAll()
	{
		if (arrowBtn != null){
			arrowBtn.removeAll();
		}
		JFToolBarLayout layout = new JFToolBarLayout(getOrientation());
		setLayout(layout);
		super.removeAll();
	}

	private class JFToolBarLayout implements LayoutManager2, Serializable,
			PropertyChangeListener, UIResource
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5735232434007558751L;
		
		Component[] components = null;
		BoxLayout lm;

		private int toolBarWidth = 0;

		private int totalWidth = 0;

		private JFArrowButton btnArrow = null;

		private int count = 0;

		private final int SHOW_ALL = -1;

		private int oldIndex = -1;

		JFToolBarLayout(int orientation)
		{
			if (orientation == JToolBar.VERTICAL)
			{
				lm = new BoxLayout(JFToolBar.this, BoxLayout.PAGE_AXIS);
			} else
			{
				lm = new BoxLayout(JFToolBar.this, BoxLayout.LINE_AXIS);
			}
		}

		public void addLayoutComponent(String name, Component comp)
		{
		}

		public void addLayoutComponent(Component comp, Object constraints)
		{
			if (comp instanceof JFArrowButton)
			{
				btnArrow = (JFArrowButton) comp;
			}
			count++;
		}

		public void removeLayoutComponent(Component comp)
		{
			count--;
		}

		public Dimension preferredLayoutSize(Container target)
		{
			return lm.preferredLayoutSize(target);
		}

		public Dimension minimumLayoutSize(Container target)
		{
			return new Dimension(10, 10);
		}

		public Dimension maximumLayoutSize(Container target)
		{
			return preferredLayoutSize(target);
		}

		private int findArrowIndex(Component[] comps)
		{
			for (int i=0;i<comps.length;i++){
				if (comps[i] instanceof JFArrowButton){
					return i;
				}
			}
			return -1;
		}
		public void layoutContainer(Container parent)
		{
			JComponent parentCom = (JComponent) parent;

			toolBarWidth = parentCom.getWidth();
			totalWidth = 0;

			Component[] children = parentCom.getComponents();
			Component[] arrowChildren = btnArrow.getIArrowPop().getComponents();
			Component[] temps = new Component[children.length + arrowChildren.length];
			System.arraycopy(children, 0, temps, 0, children.length);
			System.arraycopy(arrowChildren,0 , temps, children.length, arrowChildren.length);

			int arrowIndex = findArrowIndex(temps);
			
			components = new Component[temps.length - 1];
			System.arraycopy(temps, 0, components, 0, arrowIndex);
			
			if (arrowIndex != temps.length - 1){
				System.arraycopy(temps, arrowIndex + 1 ,components , arrowIndex, temps.length - arrowIndex - 1);
			}
			
			int btnHeight = getMaxIconHeight();
			int showIndex = getShowIndex();
			// 全部显示
			if (showIndex == SHOW_ALL)
			{
				btnArrow.setVisible(false);
				arrowToBar(showIndex,parentCom);
			}
			// 窗口变小，可能需要增加控件到小按钮
			else if (oldIndex == -1 || oldIndex > showIndex)
			{
				btnArrow.setVisible(true);
				barToArrow(showIndex, parentCom);

			}
			// 窗口变大，需要增加空见到ToolBar
			else if (oldIndex < showIndex)
			{
				btnArrow.setVisible(true);
				arrowToBar(showIndex, parentCom);
			}
			oldIndex = showIndex;

			if (btnArrow.isVisible())
			{
				// btnArrow.setBounds(toolBarWidth - 20 , 0, 12, 30);
				btnArrow.setBounds(parentCom.getWidth() - 22, 0, 20, 30);
			}
			for (Component child : components)
			{
				if (child instanceof JFArrowButton){
					continue;
				}
				JComponent childCom = (JComponent) child;
				int c_width = getCompSize(childCom);
				childCom.setBounds(totalWidth, 0, c_width, btnHeight);
				totalWidth += c_width;
			}
		}

		private void arrowToBar(int showIndex, JComponent parent)
		{
			int end = -1;
			if (showIndex == SHOW_ALL){
				end = btnArrow.getIArrowPop().getSize();
			}else{
				end = showIndex - oldIndex;
			}
			StringBuffer sb=new StringBuffer();
			sb.append("Move To ToolBar:");
			for (int i = oldIndex; i < oldIndex + end; i++)
			{
				btnArrow.getIArrowPop().remove(components[i]);
				parent.add(components[i]);
				sb.append(((JFToolButton)components[i]).getText());
				sb.append(" ");
			}
		}

		private void barToArrow(int showIndex, JComponent parent)
		{
			int end = 0;
			if (oldIndex != -1)
			{
				// 已经设置过，showIndex到oldIndex
				end = oldIndex;
			} else
			{
				// 从showIndex开始全部添加
				end = components.length;
			}
//			end = components.length;
//			btnArrow.getIArrowPop().removeAll();
			StringBuffer sb=new StringBuffer();
			sb.append("Move To Arrow Button:");
			int pos = 0;
			for (int i = showIndex; i < end; i++)
			{
//				if (components[i] instanceof JFArrowButton){
//					continue;
//				}
				btnArrow.add((JFToolButton) components[i], pos);
				parent.remove(components[i]);
				pos++;
				sb.append(((JFToolButton)components[i]).getText());
				sb.append(" ");
			}
		}

		private boolean isCalSize(Component com)
		{
			JComponent temp = (JComponent) com;
			if (Boolean.TRUE.equals(temp.getClientProperty("isCal")))
			{
				return true;
			}
			return false;
		}

		private int getShowIndex()
		{
			int index = -1;
			int total = 0;
			for (int i = 0; i < components.length; i++)
			{
//				if (components[i] instanceof JFArrowButton){
//					continue;
//				}
				int temp = getCompSize(components[i]);
				int arrowSize = btnArrow == null ? 0 : btnArrow.getWidth();
				if (total + temp + 5> toolBarWidth
						|| total + arrowSize + 5> toolBarWidth)
				{
					index = i;
					break;
				} else
				{
					total += temp;
				}
			}
			return index;
		}
		
		private int getMaxIconHeight()
		{
			int size = 0;
			int temp = 0;
			for (int i = 0; i < components.length; i++)
			{
				
				if (!(components[i] instanceof JFToolButton)){
					continue;
				}
				JFToolButton btn = (JFToolButton) components[i];
				Insets insets = btn.getInsets();
				if (size == 0){
					FontMetrics metrics = btn.getFontMetrics(btn.getFont());
					size = metrics.getHeight() + insets.bottom + insets.top;
				}
				
				Icon icon = btn.getIcon();
				if (icon !=null){
					if (temp < icon.getIconHeight() + insets.bottom + insets.top){
						temp = icon.getIconHeight() + insets.bottom + insets.top;;
					}
				}
				
			}
			if (temp > size){
				size = temp;
			}
			return size;
		}

		private int getCompSize(Component com)
		{
			int c_width = 0;
			if (com instanceof JFToolButton)
			{
				JFToolButton btn = (JFToolButton) com;
				String text = btn.getText();
				Insets insets = btn.getInsets();
				FontMetrics metrics = btn.getFontMetrics(btn.getFont());
				c_width = metrics.stringWidth(text);
				c_width += (insets.left + insets.right);
				
				Icon btnIcon = btn.getIcon();
				
				if (btnIcon != null){
					c_width += btnIcon.getIconWidth() + 5;
				}

				if (btn.getPopCount() > 0)
				{
					c_width += 12;
				}
			}
			return c_width;
		}

		public float getLayoutAlignmentX(Container target)
		{
			return 10;
		}

		public float getLayoutAlignmentY(Container target)
		{
			return 10;
		}

		public void invalidateLayout(Container target)
		{
		}

		public void propertyChange(PropertyChangeEvent e)
		{
			String name = e.getPropertyName();
			if (name.equals("orientation"))
			{
				int o = ((Integer) e.getNewValue()).intValue();

				if (o == JFToolBar.VERTICAL)
					lm = new BoxLayout(JFToolBar.this, BoxLayout.PAGE_AXIS);
				else
				{
					lm = new BoxLayout(JFToolBar.this, BoxLayout.LINE_AXIS);
				}
			}
		}
	}
	
	public IComponentPainter getPainter()
	{
		return painter;
	}

	public void setPainter(IComponentPainter painter)
	{
		this.painter = painter;
	}
}
