package bot;

import server.game.IPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciek on 18.12.15.
 */
public class BotFactory {
    public List<IPlayer> create(int numberOfBots)
    {
        List<IPlayer> botList = new ArrayList<IPlayer>();
        for(int i = 0 ; i < numberOfBots ; i++)
        {
            botList.add(new DEPRECATED_Bot());
        }
        return botList;
    }
}
