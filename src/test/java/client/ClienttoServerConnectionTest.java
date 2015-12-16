package client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ClienttoServerConnectionTest {

	ClienttoServerConnection csc;
	
	@Before
	public void setUp(){
		csc = new ClienttoServerConnection();
	}
	
	@Test
	public void testGetData() {
		GameData data = new GameData(12);
		assertSame(data.getNumberOfPlayers(),csc.getData().getNumberOfPlayers());
	}

	@Test 
	public void testGetGameSettings(){
		csc.getGameSettings();
	}

	@Test
	public void testConnectTo() throws IOException {
		assertEquals(false,csc.connectTo("address","22"));
	}

	
}
