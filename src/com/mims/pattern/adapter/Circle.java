package com.mims.pattern.adapter;


/**
 * 
 * ����:������.<p>
 * ʵ�ֽӿ�IShape����ʵ�ַ���draw���ܺõ�ʵ���˶�̬.<p>
 * ��draw��������������ķ���ʵ��.�ܺõ����������д���.<p>
 * ����һ���������������еĴ���ͨ�����ʵĽӿڵ���������������.<p>
 *
 * @author ����
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
