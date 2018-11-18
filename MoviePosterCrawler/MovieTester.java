package edu.doubler.toy.movie;

import java.util.concurrent.TimeUnit;

/**
 * Test 를 위한 클래스<br>
 * @author PASUDO
 *
 */
public class MovieTester {
	
	public static void main(String[]args) {
		
		MovieTester tester = new MovieTester();
//		tester.timeUnitTest();
		tester.patternTest();
	}
	
	private void patternTest() {
		String s = "*Hello";
		s = s.replace("*", "");
		System.out.println(s);
		
	}
	
	private void timeUnitTest() {
		
		
		System.out.println("Hi");
		
		/**
		 * TimeUnit 클래스 이용,
		 */
		try {
			
			TimeUnit.SECONDS.sleep(2);
			
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Bye");
	}
}
