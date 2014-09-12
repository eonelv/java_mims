package com.mims.thread;

import org.apache.log4j.Logger;

public class ThreadDeadLockB implements Runnable{
    
	private static Logger logger = Logger.getLogger(ThreadDeadLockA.class);
	
	public Object obja;
	public Object objb;
	
	public ThreadDeadLockB(Object obja,Object objb){
		this.obja = obja;
		this.objb = objb;
	}
	public void run() {
		synchronized(objb){
			logger.info("Thread B is Running! -- ObjB");
			synchronized(obja){
				logger.info("Thread B is Running! -- ObjA");
			}
		}
	}
}
