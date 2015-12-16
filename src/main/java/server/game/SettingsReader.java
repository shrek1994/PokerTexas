package server.game;

import messages.GameType;
import messages.Settings;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by maciek on 16.12.15.
 */
public class SettingsReader {
    private static SettingsReader ourInstance = new SettingsReader();

    public static SettingsReader getInstance() {
        return ourInstance;
    }

    private SettingsReader() {}

    public Settings readXml(String path)
    {
        Settings settings = new Settings();
        if(path == null)
        {
            return settings;
        }
        try {

            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("settings");

            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;

            settings.port = Integer.parseInt(eElement.getElementsByTagName("port").item(0).getTextContent());
            settings.numberOfPlayers = Integer.parseInt(eElement.getElementsByTagName("numberOfPlayers").item(0).getTextContent());
            settings.numberOfBots = Integer.parseInt(eElement.getElementsByTagName("numberOfBots").item(0).getTextContent());
            settings.smallBlind = Integer.parseInt(eElement.getElementsByTagName("smallBlind").item(0).getTextContent());
            settings.bigBlind = Integer.parseInt(eElement.getElementsByTagName("bigBlind").item(0).getTextContent());
            settings.gameType = GameType.getGameType(Integer.parseInt(eElement.getElementsByTagName("gameType").item(0).getTextContent()));
            settings.moneyOnStart = Integer.parseInt(eElement.getElementsByTagName("moneyOnStart").item(0).getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }
}
