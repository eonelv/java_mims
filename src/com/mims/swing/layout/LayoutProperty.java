package com.mims.swing.layout;

import java.awt.Rectangle;

public class LayoutProperty {
	
	public static String LAYOUT_CHILD_PROPERTY = "Layout_child_property";
	
	public static String LAYOUT_PARENT_PROPERTY = "Layout_parent_property";
	
	public static int TOP = 1;
	
	public static int TOP_RESIZE = 2;
	
	public static int BOTTOM = 4;
	
	public static int BOTTOM_RESIZE = 8;
	
	public static int LEFT = 16;
	
	public static int LEFT_RESIZE = 32;
	
	public static int RIGHT = 64;
	
	public static int RIGHT_RESIZE = 128;
	
	private int policy;
	
	private Rectangle rectangle = null;
	
	public LayoutProperty(int x,int y,int width,int height,int policy)
	{
		rectangle = new Rectangle(x,y,width,height);
		this.policy = policy;
	}
	
	public LayoutProperty(int x,int y,int width,int height)
	{
		this(x,y,width,height,0);
	}

	public int getPolicy() {
		return policy;
	}

	public void setPolicy(int policy) {
		this.policy = policy;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
}
