package server.game;

import messages.Settings;

import java.io.IOException;

/**
 * Created by maciek on 09.12.15.
 */
public class Main {

    // args[0] - nazwa pliku z ustawieniami
    public static void main(String args[])
    {
        //Format logger
//        System.setProperty("java.util.logging.SimpleFormatter.format",
//                "%4$s %5$s%6$s%n");
        if ( args.length < 1)
        {
            printHelp();
        }

        Settings settings = SettingsReader.getInstance().readXml(args[0]);
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
