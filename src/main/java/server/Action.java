package server;

public class Action {
	private ActionType action;
	private double money;

	public Action(ActionType action, double money)
	{
		this.action = action;
		this.money = money;
	}
	
	
	public ActionType getAction() {
		return action;
	}

	public double getMoney() {
		return money;
	}
}
