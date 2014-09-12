/**
 * 
 */
package com.mims.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.UIResource;

import com.mims.swing.ctrl.JFArrowButton;

/**
 * 
 * 描述:.<p>
 *
 * @author 李威 	QQ:708888157
 *
 * @Date: 2011-9-18
 *
 */
public class BarLayout implements LayoutManager2, Serializable,
		PropertyChangeListener, UIResource
{

	private static final long serialVersionUID = -5735232434007558751L;

	private Component[] components = null;
	
	private int minSeq = -1;
	
	private int maxSeq = -1;
	
	private int oldWinWidth = -1;
	
	BoxLayout lm;
	
	private JFArrowButton btnNext = null;
	
	private JFArrowButton btnPre = null;
	
	private JComponent source = null;

	public BarLayout()
	{
		
	}

	public void addLayoutComponent(String name, Component comp)
	{
	}

	public void addLayoutComponent(Component comp, Object constraints)
	{
	}

	public void removeLayoutComponent(Component comp)
	{
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
	
	/**
	 * 
	 * @描述：从左开始计算，找到最大可以显示的序号.<p>
	 * 
	 * @author 李威
	 *
	 * @Date：2011-9-17
	 *
	 * @param from 从第几个控件开始计算
	 * @param w 工具栏宽度
	 * @return
	 */
	private int getMaxSeq(int from, int w){
		int temp = 0;
		for(int i = from; i < components.length; i++){
			temp += components[i].getWidth();
			
			if(temp >= w - 40){
				return i - 1;
			}
		}
		return components.length;
	}
	
	private int getMinSeq(int from, int w){
		int temp = 0;
		for(int i = from - 1; i >= 0; i--){
			temp += components[i].getWidth();
			
			if(temp >= w - 40){
				return i + 1;
			}
		}
		return 0;
	}

	private JFArrowButton createNextBtn(){
		JFArrowButton button = new JFArrowButton(SwingConstants.EAST);
		button.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				if (maxSeq < components.length){
					maxSeq++;
					source.putClientProperty("NEXT", new Integer(1));
					source.repaint();
					source.doLayout();
				}
				
			}
		});
		return button;
	}
	
	private JFArrowButton createPreBtn(){
		JFArrowButton button = new JFArrowButton(SwingConstants.WEST);
		button.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				if (minSeq > 0){
					minSeq--;
					source.putClientProperty("NEXT", new Integer(-1));
					source.repaint();
					source.doLayout();
					
				}
				
			}
		});
		return button;
	}
	
	public void layoutContainer(Container parent)
	{
		source = (JComponent)parent;
		if (btnNext == null){
			btnNext = createNextBtn();
			btnPre = createPreBtn();
		}
		
		JComponent parentCom = (JComponent) parent;
		int w = parentCom.getWidth();
		int h = parentCom.getHeight();
		Integer next = (Integer)parentCom.getClientProperty("NEXT");
		if (next == null){
			next = 0;
		}
		
		if (components == null){
			components = parentCom.getComponents(); 
		}
		
		
		//如果第一次进入，从第一个开始计算最大可显示的序号
		if (maxSeq == -1){
			minSeq = 0;
			maxSeq = getMaxSeq(0, w);
		}else{
		//分几种情况
			if (w > oldWinWidth){
			//窗口变大
				maxSeq = getMaxSeq(minSeq, w);
				minSeq = getMinSeq(maxSeq, w);
			}else if (w < oldWinWidth){
			//窗口变小,从最大的序号开始计算，找最小
				minSeq = getMinSeq(maxSeq, w);
			}else{
			//窗口没变，点击了前/后按钮
				if (next > 0){
					minSeq = getMinSeq(maxSeq, w);
				}else if (next < 0){
					
					maxSeq = getMaxSeq(minSeq, w);
				}
			}
		}
		
		parentCom.removeAll();
		
		int temp = 2;
		for (int i = minSeq; i < maxSeq; i++){
			parentCom.add(components[i]);
			Dimension cSize = components[i].getPreferredSize();
			if (cSize == null){
				cSize = new Dimension(30,20);
			}
			components[i].setBounds(temp, 2, (int)cSize.getWidth(), h - 4);
			temp += cSize.getWidth() + 2;
		}
		
		if (maxSeq != components.length){
			parentCom.add(btnNext);
			btnNext.setBounds(w - 10 - 15, 0, 15, h);
		}
		
		if(minSeq != 0){
			parentCom.add(btnPre);
			if (maxSeq != components.length){
				btnPre.setBounds(btnNext.getX() - 15 - 2 , 0, 15, h);
			}else{
				btnPre.setBounds(w - 10 - 15,0, 15, h);
			}
		}
		
		oldWinWidth = w;
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

			
		}
	}
}