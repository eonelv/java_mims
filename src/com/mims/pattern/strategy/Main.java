package com.mims.pattern.strategy;

public class Main {
	public static void main(String[] args) {
		Context ctx = new Context();
		ctx.setStrategy(new StrategyA());
		ctx.performance();
		ctx.setStrategy(new StrategyB());
		ctx.performance();
	}

}
