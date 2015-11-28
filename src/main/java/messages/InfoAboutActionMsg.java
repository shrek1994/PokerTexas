package messages;

/**
 * Created by maciek on 28.11.15.
 */
public class InfoAboutActionMsg {
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
}
