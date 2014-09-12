package com.mims.pattern.adapter;


/**
 * 
 * 描述:(Adapter)适配器模式.<p>
 * 实现IShape的类很多（Line，Square)，现在要加入一个Circle.<p>
 * 其中最重要的一个方法是draw，但发现在其他类(OtherCircle)里面已经有一个类似的方法.<p>
 * 只是接口不同，方法名称不同，如果使用这个类，带来了多态的不便.<p>
 * 但不用这个类又需要重新定义一分想通的代码，所以增加一个适配器.<p>
 * 在Circle的draw方法中调用已有类的paint方法.<p>
 *
 * @auther 李威
 * @Date: 2010-12-6
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IShape line = new Line();
		IShape square = new Square();
		IShape circle = new Circle(new OtherCircle());
		
		line.draw();
		square.draw();
		circle.draw();

	}

}
