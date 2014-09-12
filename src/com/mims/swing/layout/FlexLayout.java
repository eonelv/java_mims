package com.mims.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class FlexLayout implements LayoutManager2{

	public void addLayoutComponent(String name, Component comp) {
		
	}

	public void removeLayoutComponent(Component comp) {
		
	}

	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	public void layoutContainer(Container parent) {
		JComponent parentCom = (JComponent)parent;
		Component []children = parentCom.getComponents();
		for (Component child : children)
		{
			JComponent childCom = (JComponent)child;
			layout(parentCom,childCom);
		}
	}
	
	private void layout(JComponent parentCom,JComponent childCom)
	{
		LayoutProperty parentProperty = (LayoutProperty)parentCom.getClientProperty(
				LayoutProperty.LAYOUT_PARENT_PROPERTY);
		LayoutProperty childProperty = (LayoutProperty)childCom.getClientProperty(
				LayoutProperty.LAYOUT_CHILD_PROPERTY);
		int policy = childProperty.getPolicy();
		Rectangle pRet = parentProperty.getRectangle();
		Rectangle cRet = childProperty.getRectangle();
		Rectangle newRet = (Rectangle)cRet.clone();
		int ox = childProperty.getRectangle().x;
		int oy = childProperty.getRectangle().y;
		
		double xRate = (double)(parentCom.getWidth() / parentProperty.getRectangle().getWidth());
		double yRate = (double)(parentCom.getHeight() / parentProperty.getRectangle().getHeight());
		
		boolean isYResize = false;
		if ((LayoutProperty.TOP & policy) == LayoutProperty.TOP)
		{
			newRet.y = oy;
			isYResize = true;
		}
		else if ((LayoutProperty.TOP_RESIZE & policy) == LayoutProperty.TOP_RESIZE)
		{
			newRet.y = (int)(oy * yRate);
			isYResize = true;
		}
		
		if ((LayoutProperty.BOTTOM & policy) == LayoutProperty.BOTTOM)
		{
			int bottom = (int)(parentCom.getHeight() - (pRet.getHeight() - cRet.getHeight() - cRet.getY()));
			if (isYResize)
			{
				newRet.height = bottom - newRet.y;
			}
			else
			{
				newRet.y = bottom - newRet.height;
			}
		}
		else if ((LayoutProperty.BOTTOM_RESIZE & policy) == LayoutProperty.BOTTOM_RESIZE)
		{
			int bottom = (int)(parentCom.getHeight() - (pRet.getHeight() - cRet.getHeight() - cRet.getY())*yRate);
			if (isYResize)
			{
				newRet.height = bottom - newRet.y;
			}
			else
			{
				newRet.y = bottom - newRet.height;
			}
		}
		
		boolean isXResize = false;
		if ((LayoutProperty.LEFT & policy) == LayoutProperty.LEFT)
		{
			newRet.x = ox;
			isXResize = true;
		}
		else if ((LayoutProperty.LEFT_RESIZE & policy) == LayoutProperty.LEFT_RESIZE)
		{
			newRet.x = (int)(ox * xRate);;
			isXResize = true;
		}
		
		if ((LayoutProperty.RIGHT & policy) == LayoutProperty.RIGHT)
		{
			int right = (int)(parentCom.getWidth() - (pRet.getWidth() - cRet.getWidth() - cRet.getX()));
			if (isXResize)
			{
				newRet.width = right - newRet.x;
			}
			else
			{
				newRet.x = right - newRet.width;
			}
		}
		else if ((LayoutProperty.RIGHT_RESIZE & policy) == LayoutProperty.RIGHT_RESIZE)
		{
			int right = (int)(parentCom.getWidth() - (pRet.getWidth() - cRet.getWidth() - cRet.getX())*xRate);
			
			if (isXResize)
			{
				newRet.width = right - newRet.x;
			}
			else
			{
				newRet.x = right - newRet.width;
			}
		}
		childCom.setBounds(newRet);
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		
	}

	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
		
	}
	
}
