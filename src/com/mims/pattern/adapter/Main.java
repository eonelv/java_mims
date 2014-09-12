package com.mims.pattern.adapter;


/**
 * 
 * ����:(Adapter)������ģʽ.<p>
 * ʵ��IShape����ܶࣨLine��Square)������Ҫ����һ��Circle.<p>
 * ��������Ҫ��һ��������draw����������������(OtherCircle)�����Ѿ���һ�����Ƶķ���.<p>
 * ֻ�ǽӿڲ�ͬ���������Ʋ�ͬ�����ʹ������࣬�����˶�̬�Ĳ���.<p>
 * ���������������Ҫ���¶���һ����ͨ�Ĵ��룬��������һ��������.<p>
 * ��Circle��draw�����е����������paint����.<p>
 *
 * @auther ����
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
