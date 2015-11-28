package messages;

public class ActionMsg {
	private ActionType action;
	private double money;

	public ActionMsg(ActionType action, double money)
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
