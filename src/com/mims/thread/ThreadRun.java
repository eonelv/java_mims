package com.mims.thread;

import org.apache.log4j.Logger;

public class ThreadRun {

	private static Logger logger = Logger.getLogger(ThreadRun.class);
	
	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 *
	 * @Date£º2011-1-3
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Object obja = new Object();
		Object objb = new Object();
		Thread threadA = new Thread(new ThreadDeadLockA(obja,objb),"Thread ThreadDeadLockA");
		logger.info("Create Thread ThreadDeadLockA Success");
		Thread threadB = new Thread(new ThreadDeadLockB(obja,objb),"Thread ThreadDeadLockB");
		logger.info("Create Thread ThreadDeadLockB Success");
		threadA.start();
		threadB.start();
	}

}
