package com.mims.pattern.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Context {
	private List observers;
	
	/**
	 * 
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2010-12-6
	 *
	 * @param observer
	 */
	public void regisit(IObserver observer){
		if(observers == null){
			observers = new ArrayList();
		}
		observers.add(observer);
	}
	
	/**
	 * 
	 * @������.<p>
	 * 
	 * @author ����
	 *
	 * @Date��2010-12-6
	 *
	 */
	public void change(){
		if(observers == null){
			System.out.println("No Observer.");
			return ;
		}
		Iterator it = observers.iterator();
		while(it.hasNext()){
			((IObserver)it.next()).change();
		}
	}
}
