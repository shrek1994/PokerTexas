package messages;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class InfoAboutActionMsg implements Serializable {
    private int playerId;
    private ActionMsg action;

    public int getPlayerId() {
        return playerId;
    }

    public ActionMsg getAction() {
        return action;
    }

    public InfoAboutActionMsg(int playerId, ActionMsg action) {

        this.playerId = playerId;
        this.action = action;
    }

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof InfoAboutActionMsg)
        {
            InfoAboutActionMsg msg = (InfoAboutActionMsg)o;
            return msg.playerId == playerId &&
                   msg.action.equals(action);
        }
        return false;
    }
}
