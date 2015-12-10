package server.game;

import messages.Settings;

import java.io.IOException;

/**
 * Created by maciek on 09.12.15.
 */
public class Main {

    public static void main(String args[])
    {
        // args[0] = small blind
        // args[1] = big blind
        if ( args.length != 2)
        {
            printHelp();
        }

        Settings settings = new Settings(); //TODO read from file
        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.create(settings);
        try {
            game.run();
        } catch (IOException e) {
            printConnectionError();
        }
    }

    private static void printConnectionError() {

    }


    private static void printHelp() {

    }


}
