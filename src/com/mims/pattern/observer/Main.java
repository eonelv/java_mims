package com.mims.pattern.observer;

public class Main {
	public static void main(String[] args) {
		
		Context ctx = new Context();
		ctx.change();
		ctx.regisit(new ObserverA());
		ctx.regisit(new ObserverB());
		ctx.change();
	}
}
