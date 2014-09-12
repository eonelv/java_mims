package com.mims.thread;

import org.apache.log4j.Logger;

public class ThreadDeadLockA implements Runnable{

	private static Logger logger = Logger.getLogger(ThreadDeadLockA.class);
	
	public Object obja;
	public Object objb;
	
	public ThreadDeadLockA(Object obja,Object objb){
		this.obja = obja;
		this.objb = objb;
	}
	public void run() {
		synchronized(obja){
			logger.info("Thread A is Running! -- ObjA");
			synchronized(objb){
				logger.info("Thread A is Running! -- ObjB");
			}
		}
	}

}
