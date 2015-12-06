package messages;

import java.io.Serializable;

public class ActionMsg implements Serializable {
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

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof ActionMsg)
        {
            ActionMsg msg = (ActionMsg)o;
            return msg.action.equals(action) &&
                    msg.money == money;
        }
        return false;
    }
}
