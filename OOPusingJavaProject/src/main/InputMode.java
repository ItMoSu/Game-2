package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Scanner;
import customer.Order;
import customer.VipCustomer;
import implementation.FileLogic;
import customer.CasualCustomer;
import customer.Customer;
import customer.Helper;
import customer.Ingredient;
import customer.Inventory;

public class InputMode implements FileLogic{
    Scanner scan = new Scanner(System.in);
    Helper help = new Helper();
    Inventory inventory = new Inventory();
    Vector<Order> activeOrders = new Vector<>();
    int score = 0;

    public InputMode() {
        startInputMode();
    }

    public void startInputMode() {
        boolean exit = false;
        System.out.println();
        while(!exit) {
            System.out.println("\nEnter command (e.g., 'process <order_number>', 'restock', 'exit'):");
            System.out.print(">> ");
            String input = scan.nextLine();

            if(input.startsWith("process")) {
                processOrder(input);
            }else if (input.equalsIgnoreCase("restock")) {
                restockInventory();
            }else if (input.equalsIgnoreCase("exit")) {
                exit = true;
                new Main();
                break;
            }else {
                System.out.println("Invalid command! Please try again.");
            }
        }
    }

    private void processOrder(String input) {
        loadInventory();
        loadActiveOrders();
        String[] parts = input.split(" ");
        
        if(parts.length != 2) {
            System.out.println("Invalid input! Use 'process <order_number>'.");
            return;
        }

        try{
            int orderNumber = Integer.parseInt(parts[1]);
            if(orderNumber < 1 || orderNumber > activeOrders.size()) {
                System.out.println("Invalid order number.");
                return;
            }

            Order order = activeOrders.get(orderNumber - 1);
            Vector<Ingredient> orderIngredients = order.getIngredients();

            System.out.print("Ingredients needed: ");
            for(Ingredient ingredient : orderIngredients) {
                if(orderIngredients.lastElement().equals(ingredient)) {
                    System.out.print(ingredient.getName() + " (" + ingredient.getQuantity() + ").\n");
                }else {
                    System.out.print(ingredient.getName() + " (" + ingredient.getQuantity() + "), ");
                }
            }


            System.out.println("Enter the ingredients to complete the order [format: name quantity, name quantity]: ");
            System.out.print(">> ");
            String[] userIngredients = scan.nextLine().split(",");

            for(int i = 0; i < userIngredients.length; i++) {
                userIngredients[i] = userIngredients[i].trim();
            }
            
            if(orderMatches(order, userIngredients)) {
                Vector<Ingredient> ingredientsNeeded = order.getIngredients();

                if(inventory.hasIngredients(ingredientsNeeded)) {
                    inventory.useIngredients(ingredientsNeeded);
                    loadScore();
                    score += order.getReward();
                    activeOrders.remove(orderNumber - 1);
                    updateOrders();
                    System.out.println("Order complete! You earned $" + order.getReward());
                    updateScore();
                }else {
                    System.out.println("Not enough ingredients to complete the order.");
                }
            }else {
                System.out.println("Incorrect ingredients or order has expired.");
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid order number format.");
        }
    }

    private boolean orderMatches(Order order, String[] userIngredients) {
        Vector<Ingredient> orderIngredients = order.getIngredients();
        Vector<Ingredient> orderList = new Vector<>(orderIngredients);

        if(userIngredients.length != orderIngredients.size()) {
            return false;
        }

        for(String userIngredient : userIngredients) {
            boolean found = false;

            for(Ingredient orderIngredient : orderList) {
                String[] userIngredientParts = userIngredient.split(" ");
                String userIngredientName = userIngredientParts[0].trim();
                int userIngredientQty = Integer.parseInt(userIngredientParts[1].trim());

                if(orderIngredient.getName().equalsIgnoreCase(userIngredientName) 
                        && orderIngredient.getQuantity() == userIngredientQty) {
                    found = true;
                    orderList.remove(orderIngredient);
                    break;
                }
            }

            if(!found) {
                return false;
            }
        }

        return true;
    }

    private void restockInventory() {
    	loadScore();
    	loadInventory();
        if(score >= 30) {
            inventory.restock();
            score -= 30;
            updateScore();
        }else {
            System.out.println("Insufficient money! You need at least 30 points to restock.");
        }
    }

    private void updateScore() {
        inventory.updateInventoryFile();

        try(FileWriter writes = new FileWriter("src/main/score.txt")) {
            writes.write(score + "\n");
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
