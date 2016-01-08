package client;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import messages.ActionMsg;
import messages.ActionType;
import messages.InfoAboutContinuingGameMsg;
import messages.RankingMsg;
/**
 * 
 * @author erinu
 * 443 all
 * 153 now
 * 222 to get
 * 68 remaining
 * 91 accounting for penalty
 * 61 for pokers
 */
public class GameScreenEnd implements Screen, Observer {
	
	private Stage stage;
	private SpriteBatch batch;
	private Texture cardImage;
	private Texture backgroundImage;
	private Rectangle card[];
	private Rectangle background;
	private BitmapFont font;
	private String txtVal;
	private GameClient client;
	private boolean updated;
	private Game game;
	private TextButton continueButton;
	private BitmapFont text;
	private RankingMsg rank;
	
	public GameScreenEnd(GameClient c, Game g){
		this.client = c;
		this.game = g;
		this.rank = client.getGameData().getRankingMsg();
		client.getGameData().addObserver(this);
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("cards.fnt"),Gdx.files.internal("cards.png"),false);
		text = new BitmapFont(Gdx.files.internal("text.fnt"),Gdx.files.internal("text.png"),false);
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		continueButton = new TextButton("Contiue", skin);
		continueButton.setPosition(400, 230);
		continueButton.setSize(70, 50);
		stage.addActor(continueButton);
		continueButton.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button){
	           	 GameData oldData = client.getGameData();
	           	 oldData.setMoneyOfPlayerX(rank.getPlayerIdWhoWin(), rank.getMoney()+oldData.getMoneyOfPlayerX(rank.getPlayerIdWhoWin()));
	           	 GameData data = new GameData(oldData.getNumberOfPlayers());
	           	 data.setGameType(oldData.getGameType());
          	 	 data.setBigBlind(oldData.getBigBlind());
          	 	 data.setNumberOfPlayers(oldData.getNumberOfPlayers());
          	 	 data.setSmallBlind(oldData.getSmallBlind());
          	 	 data.setPlayerNumber(oldData.getPlayerNumber());
          	 	 data.setMoneyOfPlayers(oldData.getMoneyOfPlayers());
          	 	 data.setAllInChecker(false);
          	 	 data.setNumberOfCardsOnTable(0);
          	 	 client.getConnection().setData(data);
          	 	 client.getConnection().sendMove(new InfoAboutContinuingGameMsg(true));
				 client.getGameData().setStatus("WAIT");
				 game.setScreen(new GameScreenWait(client,game));
			}
		});
		batch = new SpriteBatch();
		background  = new Rectangle();
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		background.width = 800;
		background.height = 600;
		generateCardsFronts();
	}

	
	void generateCardsFronts(){
		int n = client.getGameData().getNumberOfCardsOnTable();
		card = new Rectangle[n+2];
		cardImage = new Texture(Gdx.files.internal("card.jpg"));
		for (int i=0; i<n+2; i++){
			card[i] = new Rectangle();
			card[i].width = 50;
			card[i].height = 75;
		}
		for (int i=0; i<n+2; i++)
			card[i].setPosition(160+i*60,350);
		
	}
	void batchCardsFronts(){
		batch.begin();
		int n = client.getGameData().getNumberOfCardsOnTable();
		for (int i=0; i<n+2;i++){
			batch.draw(cardImage, card[i].getX(), card[i].getY());
			font.draw(batch, CardUtils.CardValue(client.getGameData().getCardsInHandANDOnTable(i)), card[i].getX()+5, card[i].getY()+50);
		}
		batch.end();
	}
	
	
	void batchAllText(){
		batch.begin();
		text.draw(batch, "Player "+rank.getPlayerIdWhoWin()+" won.", 250 , 560);
		text.draw(batch, String.valueOf(rank.getMoney()), 270 , 530);
		text.draw(batch, "with cards: ", 270 , 510);
		batch.end();
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundImage, background.x, background.y);
		batch.end();
		stage.act(delta);
		stage.draw();
		batchCardsFronts();
		batchAllText();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.updated = true;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// Android only

	}

	@Override
	public void resume() {
		// Android only

	}

	@Override
	public void hide() {
		// Android only

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}


}
