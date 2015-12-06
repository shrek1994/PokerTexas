package messages;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class RankingMsg implements Serializable {
    private int playerIdWhoWin;


    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof RankingMsg)
        {
            RankingMsg msg = (RankingMsg)o;
            return msg.playerIdWhoWin == playerIdWhoWin;
        }
        return false;
    }
}
