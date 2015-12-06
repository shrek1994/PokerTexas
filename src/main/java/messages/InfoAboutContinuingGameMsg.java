package messages;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class InfoAboutContinuingGameMsg implements Serializable {
    private boolean playerContinue;

    public boolean isPlayerContinue() {
        return playerContinue;
    }

    public InfoAboutContinuingGameMsg(boolean playerContinue) {

        this.playerContinue = playerContinue;
    }

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof InfoAboutContinuingGameMsg)
        {
            InfoAboutContinuingGameMsg msg = (InfoAboutContinuingGameMsg)o;
            return msg.playerContinue == playerContinue;
        }
        return false;
    }
}
