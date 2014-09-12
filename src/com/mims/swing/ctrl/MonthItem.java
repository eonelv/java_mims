/**
 * 
 */
package com.mims.swing.ctrl;

import java.util.ArrayList;
import java.util.List;

/**
 * ÃèÊö:.
 * <p>
 * 
 * @author ÀîÍþ
 * 
 * @Date: 2011-9-12
 * 
 */
public class MonthItem
{

	private String name;

	private int value;

	public static MonthItem January = new MonthItem("1", 0);
	public static MonthItem February = new MonthItem("2", 1);
	public static MonthItem March = new MonthItem("3", 2);
	public static MonthItem April = new MonthItem("4", 3);
	public static MonthItem May = new MonthItem("5", 4);
	public static MonthItem June = new MonthItem("6", 5);
	public static MonthItem July = new MonthItem("7", 6);
	public static MonthItem August = new MonthItem("8", 7);
	public static MonthItem September = new MonthItem("9", 8);
	public static MonthItem October = new MonthItem("10", 9);
	public static MonthItem November = new MonthItem("11", 10);
	public static MonthItem December = new MonthItem("12", 11);

	MonthItem(String name, int value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public static List getItems()
	{
		List list = new ArrayList();
		list.add(January);
		list.add(February);
		list.add(March);
		list.add(April);
		list.add(May);
		list.add(June);

		list.add(July);
		list.add(August);
		list.add(September);
		list.add(October);
		list.add(November);
		list.add(December);
		
		return list;
	}
	
	public String toString()
	{
		return name;
	}
}
