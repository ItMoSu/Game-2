package customer;

import java.util.Scanner;

import implementation.UserHelper;

public class Helper implements UserHelper {
	Scanner scan = new Scanner(System.in);
	
	public Helper() {
	}

	@Override
	public void skip() {
		System.out.println("\n".repeat(20));
	}

	@Override
	public void sleep() {
		try{
		    Thread.sleep(1050);
		}catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}

	@Override
	public void enter() {
		System.out.print("                        Press Enter to Continue...");
		scan.nextLine();
	}

}
