package com.xj.test;

public class ThreadTest implements Runnable {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("threadTest start ");
		new Thread(new ThreadOne()).start();
		System.out.println("threadTest end ");
	}
	
	class ThreadOne implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("threadOne start");
			new Thread(new ThreadTwo()).start();
			System.out.println("threadTwo end");
		}
	}
	
	
	class ThreadTwo implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
