package customer;

import implementation.CustomerLogic;

public class VipCustomer extends Customer implements CustomerLogic{
	private int bonus;
	public VipCustomer(String name, int bonus) {
		super(name);
		this.bonus = bonus + getBonusReward();
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public int getBonusReward() {
		return 10;
	}

}
