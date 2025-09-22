package customer;

import java.util.Vector;

public class Order {
    private Customer customer;
    private String orderName;
    private Vector<Ingredient> ingredients;
    private int reward;
    private int time;
    
    public Order(Customer customer, String orderName, boolean isVip) {
        this.customer = customer;
        this.orderName = orderName;
        this.ingredients = getIngredientsList();
        
        int baseReward = 5 + (ingredients.size() * 2); 
        if(isVip) {
            this.reward = baseReward + 10;
        }else {
            this.reward = baseReward + 0;
        }

        this.time = customer.getPatience();
    }

    public void toStringVip(int i) {
    	System.out.printf("║ %-6d║ %-6s%-9s ║ %-21s║ $%-6d║ %-2d%-3s ║%n", i,  this.getCustomer().getName(), "VIP", this.getOrderName(), this.getReward(), this.getTime(), "s");    
    }

    public void toStringCasual(int i) {
    	System.out.printf("║ %-6d║ %-16s║ %-21s║ $%-6d║ %-2d%-3s ║%n", i, this.getCustomer().getName(), this.getOrderName(), this.getReward(), this.getTime(), "s");
    }

    public String toFile(int orderNumber) {
    	if(customer instanceof VipCustomer && !customer.getName().contains("VIP")) return orderNumber + "#" + customer.getName() + " VIP#" + orderName + "#" + reward + "#" + time;
    	return orderNumber + "#" + customer.getName() + "#" + orderName + "#" + reward + "#" + time;
    }

    public Vector<Ingredient> getIngredientsList() {
        Vector<Ingredient> ingredientsList = new Vector<>();

        switch(this.orderName) {
            case "Cheeseburger":
                ingredientsList.add(new Ingredient("Bun", 2));
                ingredientsList.add(new Ingredient("Cheese", 1));
                ingredientsList.add(new Ingredient("Patty", 1));
                break;
            case "Bacon Burger":
                ingredientsList.add(new Ingredient("Bun", 2));
                ingredientsList.add(new Ingredient("Bacon", 1));
                ingredientsList.add(new Ingredient("Patty", 1));
                ingredientsList.add(new Ingredient("Lettuce", 1));
                ingredientsList.add(new Ingredient("Tomato", 1));
                break;
            case "Veggie Burger":
                ingredientsList.add(new Ingredient("Bun", 2));
                ingredientsList.add(new Ingredient("Lettuce", 1));
                ingredientsList.add(new Ingredient("Tomato", 1));
                ingredientsList.add(new Ingredient("Onions", 1));
                ingredientsList.add(new Ingredient("Pickles", 1));
                break;
            case "Deluxe Burger":
                ingredientsList.add(new Ingredient("Bun", 2));
                ingredientsList.add(new Ingredient("Cheese", 1));
                ingredientsList.add(new Ingredient("Bacon", 1));
                ingredientsList.add(new Ingredient("Patty", 1));
                ingredientsList.add(new Ingredient("Lettuce", 1));
                ingredientsList.add(new Ingredient("Tomato", 1));
                ingredientsList.add(new Ingredient("Onions", 1));
                break;
            default:
                System.out.println("Unknown order type!");
                break;
        }
        return ingredientsList;
    }

    public Vector<Ingredient> getIngredients() {
        return ingredients;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getReward() {
        return reward;
    }

    public int getTime() {
        return time;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setIngredients(Vector<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
