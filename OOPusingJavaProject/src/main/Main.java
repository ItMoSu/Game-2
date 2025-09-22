package main;
import java.util.Scanner;
import customer.Helper;

public class Main {
	Scanner scan = new Scanner(System.in);
	Helper help = new Helper();
    
	public void menu() {
		help.skip();
		
		System.out.println("  /$$   /$$                                        /$$   /$$ /$$   /$$               /$$");
		System.out.println(" | $$  /$$/                                       | $$  /$$/|__/  | $$              | $$ ");
		System.out.println(" | $$ /$$/   /$$$$$$  /$$$$$$  /$$$$$$$$ /$$   /$$| $$ /$$/  /$$ /$$$$$$    /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$$ ");
		System.out.println(" | $$$$$/   /$$__  $$|____  $$|____ /$$/| $$  | $$| $$$$$/  | $$|_  $$_/   /$$_____/| $$__  $$ /$$__  $$| $$__  $$ ");
		System.out.println(" | $$  $$  | $$  \\__/ /$$$$$$$   /$$$$/ | $$  | $$| $$  $$  | $$  | $$    | $$      | $$  \\ $$| $$$$$$$$| $$  \\ $$ ");
		System.out.println(" | $$\\  $$ | $$      /$$__  $$  /$$__/  | $$  | $$| $$\\  $$ | $$  | $$ /$$| $$      | $$  | $$| $$_____/| $$  | $$ ");
		System.out.println(" | $$ \\  $$| $$     |  $$$$$$$ /$$$$$$$$|  $$$$$$$| $$ \\  $$| $$  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$  | $$ ");
		System.out.println(" |__/  \\__/|__/      \\_______/|________/ \\____  $$|__/  \\__/|__/   \\___/   \\_______/|__/  |__/ \\_______/|__/  |__/ ");
		System.out.println("                                         /$$  | $$\\ ");
		System.out.println("                                        |  $$$$$$/ ");
		System.out.println("                                         \\______/\\ ");
		
		System.out.println("Main Menu:");
		System.out.println("1. Output Mode (View orders and inventory)");
		System.out.println("2. Input Mode (Process orders)");
		System.out.println("3. How to Play");
		System.out.println("4. Exit");
		System.out.print(">> ");
		
		String option = scan.nextLine();
		if(option.isBlank()) {
			System.out.println("\nInvalid option. Please select again.");
			help.sleep();
			menu();
		}else if(option.equals("1")) {
			new OutputMode();
		}else if(option.equals("2")) {
			new InputMode();
		}else if(option.equals("3")) {
			new HowToPlay();
		}else if(option.equals("4")) {
			System.out.println("\nThanks for playing Krazy Kitchen!\n");
			help.sleep();
			System.exit(0);
		}else{
			System.out.println("\nInvalid option. Please select again.");
			help.sleep();
			menu();
		}
	}
	
	public Main() {
		menu();
	}

	public static void main(String[] args) {
		new Main();
	}
}
