package customer;

import java.util.Random;

public abstract class Customer {
	Random rand = new Random();
	
	private String name;
    private int patience;

	public Customer(String name) {
		super();
		this.name = name;
        this.patience = rand.nextInt(20) + 15;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPatience() {
		return patience;
	}

	public void setPatience(int patience) {
		this.patience = patience;
	}
	
}
