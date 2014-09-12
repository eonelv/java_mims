/**
 * 
 */
package com.mims.swing.ctrl.win;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.apache.log4j.Logger;

import com.mims.core.CoreUI;
import com.mims.core.log.LogSystem;
import com.mims.swing.ctrl.JFMenuItem;
import com.mims.swing.ctrl.JFPopupMenu;
import com.mims.swing.ctrl.SwingConst;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-9-16
 * 
 */
public class WinTabbedPaneUI extends BasicTabbedPaneUI
{

	private static Logger logger = LogSystem.getLogger(WinTabbedPaneUI.class);
	/**
	 * 每个页签是否可见
	 */
	private boolean[] isTabVisiable = new boolean[0];

	/**
	 * 开始可见的位置
	 */
	private int visiableBegin = -1;

	/**
	 * 最后可见的位置
	 */
	private int visiableEnd = -1;

	/**
	 * 上次窗口大小
	 */
	private Dimension oldWinSize = new Dimension(0, 0);

	/**
	 * 是否创建了按钮
	 */
	private boolean isCreateBtn = false;

	/**
	 * 按钮区域宽度
	 */
	private int btn_width = 249;

	private int tab_height = 36;

	// private int tab_height_offset = 6;
	//
	// private int tab_height_off = 4;

	// private boolean isMainFrame = false;
	//
	// private JFTabbedPaneButton btnExit = null;
	//
	// private JFTabbedPaneButton btnPre = null;
	//
	// private JFTabbedPaneButton btnNext = null;
	//
	// private JFTabbedPaneButton btnMore = null;

	private JFPopupMenu menu;

	public static ComponentUI createUI(JComponent x)
	{
		return new WinTabbedPaneUI();
	}

	protected void installDefaults()
	{
		super.installDefaults();
		tabPane.setOpaque(false);
	}

	protected void installComponents()
	{
		super.installComponents();

		createButton();

	}

	private void createButton()
	{
		menu = new JFPopupMenu();
	}

	protected void assureRectsCreated(int tabCount)
	{
		int rectArrayLen = rects.length;
		if (tabCount != rectArrayLen)
		{
			isTabVisiable = new boolean[tabCount];
			Rectangle[] tempRectArray = new Rectangle[tabCount];
			for (int i = 0; i < tabCount; i++)
			{
				tempRectArray[i] = new Rectangle();
			}
			rects = tempRectArray;
		}
	}
	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
	{
		int tabCount = tabPane.getTabCount();

		Rectangle iconRect = new Rectangle(), textRect = new Rectangle();
		Rectangle clipRect = g.getClipBounds();

		for (int j = visiableBegin; j <= visiableEnd; j++)
		{
			if (j != selectedIndex && rects[j].intersects(clipRect))
			{
				paintTab(g, tabPlacement, rects, j, iconRect, textRect);
			}
		}

		if (selectedIndex >= 0 && rects[selectedIndex].intersects(clipRect))
		{
			paintTab(g, tabPlacement, rects, selectedIndex, iconRect, textRect);
		}
	}

	protected void paintLeftTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected)
	{
		
	}

	protected void paintRightTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected)
	{

	}

	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;

		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		RenderingHints renderHints = g2.getRenderingHints();

		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		GradientPaint gp = new GradientPaint(w / 2, 0,
				SwingConst.MAIN_TABBED_BACKGROUND_SELECTED_1, w / 2, h / 2,
				SwingConst.MAIN_TABBED_BACKGROUND_SELECTED_2);

		if (isSelected)
		{

			gp = new GradientPaint(w / 2, h / 2,
					SwingConst.TABBED_BACKGROUND_SELECTED_1, w / 2, h,
					SwingConst.TABBED_BACKGROUND_SELECTED_2);
			g2.setPaint(gp);
			g2.fillRoundRect(x + 1, y, w - 2, h / 2, 5, 5);

			gp = new GradientPaint(w / 2, h / 2,
					SwingConst.TABBED_BACKGROUND_SELECTED_3, w / 2, h,
					SwingConst.TABBED_BACKGROUND_SELECTED_4);
			g2.setPaint(gp);
			g2.fillRoundRect(x + 1, y + h / 2, w - 2, h / 2, 5, 5);
		}
		

		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
	}

	protected void paintBottomTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected)
	{

	}

	protected void paintTopTabBorder(int tabIndex, Graphics g, int x, int y,
			int w, int h, int btm, int rght, boolean isSelected)
	{

	}

	public void paint(Graphics g, JComponent b)
	{

			
		int width = tabPane.getWidth();
		int height = tab_height;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D g2 = (Graphics2D) g;

		Color old = g2.getColor();
		Composite composite = g2.getComposite();
		Paint painter = g2.getPaint();
		RenderingHints renderHints = g2.getRenderingHints();

		g2.setRenderingHints(rh);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		GradientPaint gp = new GradientPaint(width / 2, 0, new Color(191, 216,
				249), width / 2, height, SwingConst.MENUBAR_BACKGROUND_4);
		g2.setPaint(gp);
		g2.fillRect(0, 0, width, tabPane.getHeight());

		g2.setRenderingHints(renderHints);
		g2.setColor(old);
		g2.setPaint(painter);
		g2.setComposite(composite);
		super.paint(g2, b);
	}

	

	protected int calculateTabHeight(int tabPlacement, int tabIndex,
			int fontHeight)
	{
		return tab_height;
	}

	protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount,
			int maxTabHeight)
	{
		return tab_height;
	}
	
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h)
	{
//		super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
	}
	
	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h)
	{
//		super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
	}
	
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h)
	{
//		super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
	}

	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h)
	{
//		super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
	}

	/**
	 * 
	 * @描述：页签的边框.<p>
	 * 
	 * @author 李威
	 * 
	 * @Date：2011-9-16
	 * 
	 * @param g
	 * @param tabPlacement
	 * @param tabIndex
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param isSelected
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTabBorder(java.awt.Graphics,
	 *      int, int, int, int, int, int, boolean)
	 */
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected)
	{
//		super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
	}

	@Override
	protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
			int tabIndex, Rectangle iconRect, Rectangle textRect)
	{
		// TODO Auto-generated method stub
		super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
	}

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement,
			int selectedIndex)
	{
		int width = tabPane.getWidth();
		int height = tabPane.getHeight();
		Insets insets = tabPane.getInsets();
		Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

		int x = insets.left;
		int y = insets.top;
		int w = width - insets.right - insets.left;
		int h = height - insets.top - insets.bottom;
		switch (tabPlacement)
		{
		case LEFT:
			x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
			
			w -= (x - insets.left);
			break;
		case RIGHT:
			w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
			
			break;
		case BOTTOM:
			h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
			
			break;
		case TOP:
		default:
			y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
			if (true)
			{
				y -= tabAreaInsets.bottom;
			}
			h -= (y - insets.top);
		}

		if (tabPane.getTabCount() > 0 && (true || tabPane.isOpaque()))
		{
			// Fill region behind content area
			Color color = UIManager.getColor("TabbedPane.contentAreaColor");
			if (color != null)
			{
				g.setColor(color);
			} else if (selectedIndex == -1)
			{
				g.setColor(tabPane.getBackground());
			} else
			{
//				g.setColor(selectedColor);
			}
//			g.setColor(Color.RED);
//			g.fillRect(x, y, w, h);
		}

		paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
		paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
		paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
		paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
	}

	protected LayoutManager createLayoutManager()
	{
		return new WinTabbedPaneLayout();
	}

	public class WinTabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout
	{

		public WinTabbedPaneLayout()
		{
			WinTabbedPaneUI.this.super();
		}

		protected void calculateTabRects(int tabPlacement, int tabCount)
		{
			FontMetrics metrics = getFontMetrics();
			Dimension size = tabPane.getSize();
			Insets insets = tabPane.getInsets();
			Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			int selectedIndex = tabPane.getSelectedIndex();

			int i, j;
			int x, y;
			int returnAt;

			switch (tabPlacement)
			{
			case LEFT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case RIGHT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement);
				x = size.width - insets.right - tabAreaInsets.right
						- maxTabWidth;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case BOTTOM:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = size.height - insets.bottom - tabAreaInsets.bottom
						- maxTabHeight;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			case TOP:
			default:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			}

			tabRunOverlay = getTabRunOverlay(tabPlacement);

			runCount = 0;
			selectedRun = -1;

			if (tabCount == 0)
			{
				return;
			}

			if (oldWinSize.width == 0)
			{
				oldWinSize = size;
			}
			if (size.width > oldWinSize.width)
			{
				calWinZoomOut(tabCount, tabPlacement, metrics, size, x, y,
						insets, returnAt);
			} else if (size.width < oldWinSize.width)
			{
				calWinZoomIn(selectedIndex, tabCount, tabPlacement, metrics,
						size, x, y, insets, returnAt);
			} else
			{
				calWinZoomCenter(selectedIndex, tabCount, tabPlacement,
						metrics, size, x, y, insets, returnAt);
			}

			oldWinSize = size;
		}

		private void calWinZoomIn(int selectedIndex, int tabCount,
				int tabPlacement, FontMetrics metrics, Dimension size, int x,
				int y, Insets insets, int returnAt)
		{
			Rectangle rect = new Rectangle();
			int begin = -1;
			int end = -1;
			for (int n = visiableEnd; n > 0; n--)
			{
				if ((rect.width + calculateTabWidth(tabPlacement, n, metrics)) < size.width
						- btn_width - 10)
				{
					rect.width = rect.width
							+ calculateTabWidth(tabPlacement, n, metrics);
				} else
				{
					begin = n + 1;
					break;
				}
			}

			if (begin != -1 && begin > selectedIndex)
			{
				begin = selectedIndex;
			}
			if (begin == -1)
			{
				begin = 0;
			}

			rect = new Rectangle();
			for (int n = begin; n < tabCount; n++)
			{
				if ((rect.width + calculateTabWidth(tabPlacement, n, metrics)) < size.width
						- btn_width - 10)
				{
					rect.width = rect.width
							+ calculateTabWidth(tabPlacement, n, metrics);
				} else
				{
					end = n;
					break;
				}
			}

			if (begin == -1)
			{
				begin = 0;
			}
			if (end == -1)
			{
				end = tabCount - 1;
			}

			calRect(tabCount, begin, end, rect, tabPlacement, metrics, x, y,
					insets, returnAt);
		}

		private void calWinZoomOut(int tabCount, int tabPlacement,
				FontMetrics metrics, Dimension size, int x, int y,
				Insets insets, int returnAt)
		{
			Rectangle rect = new Rectangle();
			int begin = -1;
			int end = -1;
			for (int n = visiableBegin; n < tabCount; n++)
			{
				if ((rect.width + calculateTabWidth(tabPlacement, n, metrics)) < size.width
						- btn_width - 10)
				{
					rect.width = rect.width
							+ calculateTabWidth(tabPlacement, n, metrics);
				} else
				{
					end = n;
					begin = visiableBegin;
					break;
				}
			}

			if (end == -1)
			{
				for (int n = visiableBegin - 1; n >= 0; n--)
				{
					if ((rect.width + calculateTabWidth(tabPlacement, n,
							metrics)) < size.width - btn_width - 10)
					{
						rect.width = rect.width
								+ calculateTabWidth(tabPlacement, n, metrics);
					} else
					{
						begin = n + 1;
						break;
					}
				}
				end = tabCount - 1;
			}

			if (begin == -1)
			{
				begin = 0;
			}

			calRect(tabCount, begin, end, rect, tabPlacement, metrics, x, y,
					insets, returnAt);
		}

		private void calWinZoomCenter(int selectedIndex, int tabCount,
				int tabPlacement, FontMetrics metrics, Dimension size, int x,
				int y, Insets insets, int returnAt)
		{
			Rectangle rect = null;
			if (isTabVisiable[selectedIndex])
			{
				int begin = -1;
				int end = -1;
				for (int n = 0; n < isTabVisiable.length; n++)
				{
					if (isTabVisiable[n])
					{
						begin = n;
						break;
					}
				}
				rect = new Rectangle();
				for (int n = begin; n < tabCount; n++)
				{
					if ((rect.width + calculateTabWidth(tabPlacement, n,
							metrics)) < size.width - btn_width - 10)
					{
						rect.width = rect.width
								+ calculateTabWidth(tabPlacement, n, metrics);
					} else
					{
						end = n;
						break;
					}
				}
				if (end == -1)
				{
					end = tabCount - 1;
				}
				calRect(tabCount, begin, end, rect, tabPlacement, metrics, x,
						y, insets, returnAt);
			} else
			{
				int begin = -1;
				int end = -1;
				for (int n = 0; n < selectedIndex; n++)
				{
					if (selectedIndex == tabCount - 1)
					{
						end = tabCount - 1;
						break;
					}
					if (isTabVisiable[n])
					{
						end = selectedIndex;
						break;
					}
				}
				rect = new Rectangle();
				if (end == -1)
				{
					begin = selectedIndex;
					for (int n = begin; n < tabCount; n++)
					{
						if ((rect.width + calculateTabWidth(tabPlacement, n,
								metrics)) < size.width - btn_width - 10)
						{
							rect.width = rect.width
									+ calculateTabWidth(tabPlacement, n,
											metrics);
						} else
						{
							end = n;
							break;
						}
					}
				} else
				{
					for (int n = end; n > 0; n--)
					{
						if ((rect.width + calculateTabWidth(tabPlacement, n,
								metrics)) < size.width - btn_width - 10)
						{
							rect.width = rect.width
									+ calculateTabWidth(tabPlacement, n,
											metrics);
						} else
						{
							begin = n;
							break;
						}
					}
				}
				if (begin == -1)
				{
					begin = 0;
				}
				if (end == -1)
				{
					end = tabCount - 1;
				}

				calRect(tabCount, begin, end, rect, tabPlacement, metrics, x,
						y, insets, returnAt);
			}
		}

		private void calRect(int tabCount, int begin, int end, Rectangle rect,
				int tabPlacement, FontMetrics metrics, int x, int y,
				Insets insets, int returnAt)
		{
			visiableBegin = begin;
			visiableEnd = end;
			isTabVisiable = new boolean[tabCount];
			for (int m = 0; m < rects.length; m++)
			{
				rects[m] = new Rectangle();
			}
			for (int i = begin; i <= end; i++)
			{
				isTabVisiable[i] = true;
				rect = rects[i];
				if (i > 0)
				{
					rect.x = rects[i - 1].x + rects[i - 1].width;
				} else
				{
					tabRuns[0] = 0;
					runCount = 1;
					maxTabWidth = 0;
					rect.x = x;
				}
				rect.width = calculateTabWidth(tabPlacement, i, metrics);
				maxTabWidth = Math.max(maxTabWidth, rect.width);
				if (rect.x != 2 + insets.left && rect.x + rect.width > returnAt)
				{
					if (runCount > tabRuns.length - 1)
					{
						expandTabRunsArray();
					}
					tabRuns[runCount] = i;
					runCount++;
					rect.x = x;
				}
				rect.y = y;
				rect.height = maxTabHeight;
			}
		}

		public void layoutContainer(Container parent)
		{
			setRolloverTab(-1);

			int tabPlacement = tabPane.getTabPlacement();
			Insets insets = tabPane.getInsets();
			int selectedIndex = tabPane.getSelectedIndex();
			Component visibleComponent = getVisibleComponent();

			calculateLayoutInfo();

			Component selectedComponent = null;
			if (selectedIndex < 0)
			{
				if (visibleComponent != null)
				{
					setVisibleComponent(null);
				}
			} else
			{
				selectedComponent = tabPane.getComponentAt(selectedIndex);
			}
			int cx, cy, cw, ch;
			int totalTabWidth = 0;
			int totalTabHeight = 0;
			Insets contentInsets = getContentBorderInsets(tabPlacement);

			boolean shouldChangeFocus = false;

			if (selectedComponent != null)
			{
				if (selectedComponent != visibleComponent
						&& visibleComponent != null)
				{
					if (SwingUtilities.findFocusOwner(visibleComponent) != null)
					{
						shouldChangeFocus = true;
					}
				}
				setVisibleComponent(selectedComponent);
			}

			Rectangle bounds = tabPane.getBounds();
			int numChildren = tabPane.getComponentCount();

			if (numChildren > 0)
			{
				switch (tabPlacement)
				{
				case LEFT:
					totalTabWidth = calculateTabAreaWidth(tabPlacement,
							runCount, maxTabWidth);
					cx = insets.left + totalTabWidth + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case RIGHT:
					totalTabWidth = calculateTabAreaWidth(tabPlacement,
							runCount, maxTabWidth);
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case BOTTOM:
					totalTabHeight = calculateTabAreaHeight(tabPlacement,
							runCount, maxTabHeight);
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case TOP:
				default:
					totalTabHeight = calculateTabAreaHeight(tabPlacement,
							runCount, maxTabHeight);
					cx = insets.left + contentInsets.left;
					cy = insets.top + totalTabHeight + contentInsets.top;
				}

				cw = bounds.width - totalTabWidth - insets.left - insets.right
						- contentInsets.left - contentInsets.right;
				ch = bounds.height - totalTabHeight - insets.top
						- insets.bottom - contentInsets.top
						- contentInsets.bottom;

				int buttonCount = 1;
				menu.removeAll();
				for (int i = 0; i < numChildren; i++)
				{

					Component child = tabPane.getComponent(i);

					if (child instanceof WinToolBarResource)
					{
						if (!child.isVisible())
						{
							continue;
						}
						int size = cw
								- (int) (buttonCount * child.getPreferredSize()
										.getWidth()) - 6 * buttonCount;
						int height = (int) child.getPreferredSize().getHeight();
						child.setBounds(size, cy / 2 - height / 2, (int) child
								.getPreferredSize().getWidth(), (int) child
								.getPreferredSize().getHeight());
						buttonCount++;
					} else
					{
						child.setBounds(1, totalTabHeight, parent.getWidth() - 2, parent.getHeight() - totalTabHeight - 1);
						// menu.add(new WinTabbedPaneMenuItem((CoreUI)child));
					}

				}
			}

		}

		protected void normalizeTabRuns(int tabPlacement, int tabCount,
				int start, int max)
		{
			if (tabPlacement == TOP || tabPlacement == BOTTOM)
			{
				super.normalizeTabRuns(tabPlacement, tabCount, start, max);
			}
		}

		// Don't rotate runs!
		protected void rotateTabRuns(int tabPlacement, int selectedRun)
		{
		}

		// Don't pad selected tab
		protected void padSelectedTab(int tabPlacement, int selectedIndex)
		{
		}
	}

	private class WinTabbedPaneMenuItem extends JFMenuItem
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 8795373392949802374L;

		public JComponent comp = null;

		public WinTabbedPaneMenuItem(final JComponent comp)
		{
			super(((CoreUI) comp).getTitle());

			addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					tabPane.setSelectedComponent(comp);
				}
			});
			this.comp = comp;
		}
	}

}