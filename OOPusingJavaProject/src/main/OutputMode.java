package main;

import java.io.*;
import java.util.Vector;
import java.util.Random;
import java.util.Scanner;
import customer.Helper;
import customer.Ingredient;
import customer.Order;
import customer.Customer;
import customer.CasualCustomer;
import customer.VipCustomer;
import implementation.FileLogic;
import customer.Inventory;

public class OutputMode implements FileLogic{
    Scanner scan = new Scanner(System.in);
    Helper help = new Helper();
    Vector<Order> activeOrders = new Vector<>();
    Inventory inventory = new Inventory();
    String nameInput;
    int score = 0;
    Random rand = new Random();
    volatile boolean stopGame = false;

    String[] customerNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank"};

    public void inputName() {
        help.skip();
        boolean errorInput = false;

        while(!errorInput) {
            System.out.print("Please enter your name (Cannot be empty): ");
            nameInput = scan.nextLine();

            if(nameInput.isBlank()) {
                System.out.println("Error: Input cannot be empty!");
                continue;
            }

            errorInput = true;
        }
    }

    public void displayMainMenu() {
        help.skip();
        System.out.println("\t         " + nameInput + "'s Restaurant");
        System.out.println("╔═══════╦═════════════════╦══════════════════════╦════════╦═══════╗");
        System.out.println("║ No.   ║ Customer        ║ Order                ║ Reward ║ Time  ║");
        System.out.println("╠═══════╬═════════════════╬══════════════════════╬════════╬═══════╣");

        int i = 1;
        for(Order order : activeOrders) {
            if(order.getCustomer() instanceof VipCustomer && !order.getCustomer().getName().contains("VIP")) {
                order.toStringVip(i);
            }else {
                order.toStringCasual(i);
            }
            i++;
        }
        System.out.println("╚═══════╩═════════════════╩══════════════════════╩════════╩═══════╝");

        System.out.println("\n╔══════ Current Inventory ═════╗");
        System.out.println("║ Ingredient      │ Quantity   ║");
        System.out.println("╠═════════════════╪════════════╣");

        for(Ingredient ingredient : inventory.getIngredients()) {
            System.out.printf("║ %-15s │ %-10d ║%n", ingredient.getName(), ingredient.getQuantity());
        }

        System.out.println("╚═════════════════╧════════════╝");

        System.out.println("\n╔══════════════════ Game Stats ══════════════════╗");
        System.out.printf("║    Current Score: $%-28d║\n", score);
        System.out.println("╚════════════════════════════════════════════════╝");
        
        System.out.print("\t  [Press 'Enter' to Exit Game]");
    }

    public void displayFinalScore() {
        help.skip();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║                          YOUR FINAL SCORE IS : %-1d                             ║\n", score);
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
        help.enter();
        help.skip();
        new Main();
    }

    public void mainGame() {
        int randomOrderTime = rand.nextInt(8) + 10;
        int tickCounter = 0;

        Thread inputThread = new Thread(() -> {
            scan.nextLine();
            stopGame = true;
        });

        inputThread.start();

        while(!stopGame) {
            if(stopGame) break;           
            help.sleep();
            tickCounter++;

            if(stopGame) break;
            for(int i = 0; i < activeOrders.size(); i++) {
                Order order = activeOrders.get(i);
                order.setTime(order.getTime() - 1);
                if(order.getTime() <= 0) {
                    activeOrders.remove(i);
                    i--;
                }
            }
            loadActiveOrders();
            updateOrders();
            if(stopGame) break;
            if(activeOrders.isEmpty()) {
            	generateOrder();
            	updateOrders();
            } 
            
            if(tickCounter >= randomOrderTime && activeOrders.size() < 4) {
                generateOrder();
                updateOrders();
                randomOrderTime = rand.nextInt(8) + 10;
                tickCounter = 0;
            }
            if(stopGame) break;
            loadScore();
            loadInventory();
            loadActiveOrders();
            displayMainMenu();
        }

        displayFinalScore();
    }

    
    public void generateOrder() {
        if(activeOrders.size() >= 4) {
            return;
        }

        Customer customer = getRandomCustomer();
        String recipe = getRandomRecipe();

        boolean isVip = rand.nextBoolean();
        Order newOrder = new Order(customer, recipe, isVip);

        activeOrders.add(newOrder);
        updateOrders();
    }

    public Customer getRandomCustomer() {
        String name = getRandomCustomerName();
        if(rand.nextBoolean()) {
            return new VipCustomer(name, 0);
        }else {
            return new CasualCustomer(name, 0);
        }
    }

    public String getRandomRecipe() {
        String[] recipes = {"Cheeseburger", "Bacon Burger", "Veggie Burger", "Deluxe Burger"};
        return recipes[rand.nextInt(recipes.length)];
    }

    public OutputMode() {
        inputName();
        loadScore();
        loadInventory();
        loadActiveOrders();
        if(activeOrders.isEmpty()) generateOrder();
        updateOrders();
        mainGame();
    }

    public String getRandomCustomerName() {
        return customerNames[rand.nextInt(customerNames.length)];
    }
    
    @Override
	public void loadScore() {
    	try(BufferedReader reader = new BufferedReader(new FileReader("src/main/score.txt"))) {
            String line = reader.readLine();
            if(line != null) {
                score = Integer.parseInt(line);
            }
        }catch (IOException e) {
            System.out.println("Error reading score.txt.");
        }
	}

    @Override
	public void loadActiveOrders() {
    	if(!activeOrders.isEmpty()) activeOrders.clear();
    	
	        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/restaurant.txt"))) {
	            String line;
	            while((line = reader.readLine()) != null) {
	                String[] parts = line.split("#");
	                String customerName = parts[1];
	                String orderName = parts[2];
	                int reward = Integer.parseInt(parts[3]);
	                int time = Integer.parseInt(parts[4]);

	                Customer customer = orderName.contains("VIP") ? new VipCustomer(customerName, reward) : new CasualCustomer(customerName, reward);
	                Order orders = new Order(customer, orderName, orderName.contains("VIP") ? true : false);
	                orders.setTime(time);
	                activeOrders.add(orders);
	            }
	        }catch (IOException e) {
	            System.out.println("Error reading restaurant.txt.");
	        }	
	}


	@Override
	public void loadInventory() {
		inventory.loadInventory();
	}
	
	@Override
	public void updateOrders() {
		try(PrintWriter writer = new PrintWriter(new FileWriter("src/main/restaurant.txt"))) {
            int i = 1;
            for(Order order : activeOrders) {
                writer.println(order.toFile(i));
                i++;
            }
        }catch (IOException e) {
            System.out.println("Error updating restaurant.txt.");
        }
	}
}
