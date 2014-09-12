package com.mims.pattern.strategy;

public class Context {
	private IStrategy strategy;

	public IStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void performance(){
		strategy.performace();
	}
	
}
