package messages;

/**
 * Created by maciek on 28.11.15.
 */
public class InfoAboutContinuingGameMsg {
    private boolean playerContinue;

    public boolean isPlayerContinue() {
        return playerContinue;
    }

    public InfoAboutContinuingGameMsg(boolean playerContinue) {

        this.playerContinue = playerContinue;
    }
}
