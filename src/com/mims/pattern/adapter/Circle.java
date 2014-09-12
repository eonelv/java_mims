package com.mims.pattern.adapter;


/**
 * 
 * 描述:适配器.<p>
 * 实现接口IShape，并实现方法draw，很好的实现了多态.<p>
 * 而draw方法调用其他类的方法实现.很好的重用了已有代码.<p>
 * 就像一个适配器，将已有的代码通过合适的接口的适配来满足需求.<p>
 *
 * @author 李威
 * @Date: 2010-12-6
 *
 */
public class Circle implements IShape {

	private ICircle circle;
	
	public Circle(ICircle circle){
		this.circle = circle;
	}
	public void draw() {
		circle.paint();
	}

}
