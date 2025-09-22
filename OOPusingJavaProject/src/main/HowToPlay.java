package main;
import java.util.Scanner;
import customer.Helper;

public class HowToPlay {
	Scanner scan = new Scanner(System.in);
	Helper help = new Helper();
	
	public void showHelp() {
		help.skip();
		
		System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
	    System.out.println("║                                HOW TO PLAY                                   ║");
	    System.out.println("║                               Krazy Kitchen                                  ║");
	    System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
	    System.out.println("║ Welcome, Master Chef! Your mission: Manage a burger joint, complete orders   ║");
	    System.out.println("║ accurately and quickly. You'll switch between Output and Input modes.        ║");
	    System.out.println("╟──────────────────────────────────────────────────────────────────────────────╢");
	    System.out.println("║ 1. Output Mode (View orders and inventory):                                  ║");
	    System.out.println("║    ? View active orders, inventory, and current score                        ║");
	    System.out.println("║    ? Monitor customer names, burger requests, timers, and potential rewards  ║");
	    System.out.println("║    ? Watch out for VIP orders - higher rewards!                              ║");
	    System.out.println("║    ? Keep an eye on your inventory                                           ║");
	    System.out.println("╟──────────────────────────────────────────────────────────────────────────────╢");
	    System.out.println("║ 2. Input Mode (Process orders):                                              ║");
	    System.out.println("║    ? Assemble burgers based on customer requests                             ║");
	    System.out.println("║    ? Follow recipes exactly (e.g., 'Cheeseburger' = Bun + Patty + Cheese)    ║");
	    System.out.println("║    ? Correct assembly = Points and rewards                                   ║");
	    System.out.println("╟──────────────────────────────────────────────────────────────────────────────╢");
	    System.out.println("║ 3. Scoring and Gameplay Tips:                                                ║");
	    System.out.println("║    ? Base reward for each completed order                                    ║");
	    System.out.println("║    ? Bonus rewards for VIP orders                                            ║");
	    System.out.println("║    ? Avoid expired orders                                                    ║");
	    System.out.println("║    ? Manage your time and inventory wisely                                   ║");
	    System.out.println("╟──────────────────────────────────────────────────────────────────────────────╢");
	    System.out.println("║ 4. Exiting the Game:                                                         ║");
	    System.out.println("║    ? Type 'exit' during Output Mode to leave                                 ║");
	    System.out.println("║    ? Return to main menu or switch modes anytime                             ║");
	    System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
	    System.out.println("               Good luck, Chef! Keep those burgers coming!");
		help.enter();
		new Main();
	}
	
	public HowToPlay() {
		showHelp();
	}
}
