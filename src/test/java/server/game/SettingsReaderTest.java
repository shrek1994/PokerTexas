package server.game;

import messages.GameType;
import messages.Settings;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsReaderTest {
    SettingsReader sut = SettingsReader.getInstance();

    @Test
    public void shouldCorrectReadFile()
    {
        Settings settings = sut.readXml("src/test/data/serverSettings.xml");
        assertEquals(123, settings.port);
        assertEquals(12, settings.numberOfPlayers);
        assertEquals(23, settings.numberOfBots);
        assertEquals(34, settings.smallBlind);
        assertEquals(45, settings.bigBlind);
        assertEquals(GameType.getGameType(2), settings.gameType);
        assertEquals(456, settings.moneyOnStart);
    }

    @Test
    public void shouldReturnDefaultSettingsWhenPathIsNull()
    {
        Settings expectedSettings = new Settings();
        Settings settings = sut.readXml(null);
        assertEquals(expectedSettings.port, settings.port);
        assertEquals(expectedSettings.numberOfPlayers, settings.numberOfPlayers);
        assertEquals(expectedSettings.numberOfBots, settings.numberOfBots);
        assertEquals(expectedSettings.smallBlind, settings.smallBlind);
        assertEquals(expectedSettings.bigBlind, settings.bigBlind);
        assertEquals(expectedSettings.gameType, settings.gameType);
        assertEquals(expectedSettings.moneyOnStart, settings.moneyOnStart);
    }

}