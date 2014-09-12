package com.mims.pattern.observer;

public class ObserverA implements IObserver {

	public void change() {
		System.out.println("第一个观察者.");
	}

}
