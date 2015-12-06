package messages;

import cards.Card;
import cards.Color;
import cards.Figure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MsgTests {
    private static ActionMsg actionMsg = new ActionMsg(ActionType.AllIn, 123);
    private static BlindMsg blindMsg = new BlindMsg(234);
    private static CardMsg cardMsg = new CardMsg(new Card(Figure.Ace, Color.Clubs));
    private static InfoAboutActionMsg infoAboutActionMsg = new InfoAboutActionMsg(345,actionMsg);
    private static InfoAboutContinuingGameMsg infoAboutContinuingGameMsg = new InfoAboutContinuingGameMsg(true);
    private static NotifyAboutActionMsg notifyAboutActionMsg = new NotifyAboutActionMsg();
    private static RankingMsg rankingMsg = new RankingMsg();
    private static SettingsMsg settingsMsg = new SettingsMsg(GameType.FixedLimit, 456,567,678,789);

    private Object msg;

    @Parameterized.Parameters
    public static Collection<Object[]> msg()
    {
        return Arrays.asList(new Object[][]
                {
                        {actionMsg},
                        {blindMsg},
                        {cardMsg},
                        {infoAboutActionMsg},
                        {infoAboutContinuingGameMsg},
                        {notifyAboutActionMsg},
                        {rankingMsg},
                        {settingsMsg}
                }
        );
    }

    public MsgTests(Object msg)
    {
        this.msg = msg;
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldEqualsMessages()
    {
        assertEquals(msg,msg);
        assertTrue(msg.equals(msg));
    }

    @Test
    public void shouldNotEqualsMessages()
    {
        Object someMessage = new BlindMsg(12345678);
        assertNotSame(msg, someMessage);

        assertFalse(msg.equals(someMessage));
    }
}