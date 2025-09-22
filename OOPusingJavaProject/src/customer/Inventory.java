package customer;

import java.io.*;
import java.util.Vector;

public class Inventory {
    private Vector<Ingredient> ingredients;

    public Inventory() {
        ingredients = new Vector<>();
        ingredients.add(new Ingredient("Lettuce", 10));
        ingredients.add(new Ingredient("Onions", 10));
        ingredients.add(new Ingredient("Bun", 10));
        ingredients.add(new Ingredient("Cheese", 10));
        ingredients.add(new Ingredient("Tomato", 10));
        ingredients.add(new Ingredient("Bacon", 10));
        ingredients.add(new Ingredient("Patty", 10));
        ingredients.add(new Ingredient("Pickles", 10));
    }

    public void restock() {
        for(Ingredient ingredient : ingredients) {
            ingredient.setQuantity(ingredient.getQuantity() + 10);
        }
        System.out.println("Inventory restocked by 10 for each ingredient!");
        updateInventoryFile();
    }

    public boolean hasIngredients(Vector<Ingredient> requiredIngredients) {
        for(Ingredient required : requiredIngredients) {
            Ingredient available = findIngredient(required.getName());
            if(available == null || available.getQuantity() < required.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public void useIngredients(Vector<Ingredient> usedIngredients) {
        for(Ingredient used : usedIngredients) {
            Ingredient available = findIngredient(used.getName());
            if(available != null) {
                available.setQuantity(available.getQuantity() - used.getQuantity());
            }
        }
        updateInventoryFile();
    }

    private Ingredient findIngredient(String name) {
        for(Ingredient ingredient : ingredients) {
            if(ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return null;
    }

    public void loadInventory() {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/inventory.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("#");
                if(parts.length == 2) {
                    String ingredient = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    Ingredient loadedIngredient = findIngredient(ingredient);
                    if(loadedIngredient != null) {
                        loadedIngredient.setQuantity(quantity);
                    }else {
                        ingredients.add(new Ingredient(ingredient, quantity));
                    }
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("Inventory.txt file not found!");
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateInventoryFile() {
        try(FileWriter writer = new FileWriter("src/main/inventory.txt")) {
            for(Ingredient ingredient : ingredients) {
                writer.write(ingredient.getName() + "#" + ingredient.getQuantity() + "\n");
            }
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println(" ╔══════ Current Inventory ═════╗");
        System.out.println(" ║ Ingredient      │ Quantity   ║");
        System.out.println(" ╠═════════════════╪════════════╣");
        for(Ingredient ingredient : ingredients) {
            System.out.printf(" ║ %-15s │ %-10d ║%n", ingredient.getName(), ingredient.getQuantity());
        }
        System.out.println(" ╚═════════════════╧════════════╝");
    }

    public Vector<Ingredient> getIngredients() {
        return ingredients;
    }
}
