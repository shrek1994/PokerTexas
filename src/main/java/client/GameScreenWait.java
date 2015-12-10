package client;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class GameScreenWait implements Screen, Observer {
	
	private Stage stage;
	private SpriteBatch batch;
	private Texture cardImage;
	private Texture cardBackImage;
	private Texture backgroundImage;
	private Rectangle card;
	private Rectangle cardBack[];
	private Rectangle background;
	private BitmapFont font;
	private String txtVal;
	private GameClient client;
	private boolean updated;
	private Game game;
	
	
	public GameScreenWait(GameClient c, Game g){
		this.client = c;
		this.game = g;
		client.getGameData().addObserver(this);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		batch = new SpriteBatch();
		background  = new Rectangle();
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		background.width = 800;
		background.height = 600;
		generateCardsBacks(client.getGameData().getNumberOfPlayer());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.updated = true;
	}
	
	void generateCardsBacks(int n){
		if (client.getGameData().getRankingMsg() == null){
			cardBack = new Rectangle[n*2];
			cardBackImage = new Texture(Gdx.files.internal("cardback.png"));
			for (int i=0; i<n*2; i++){
				cardBack[i] = new Rectangle();
				cardBack[i].width = 50;
				cardBack[i].height = 75;
			}
			for(int i=0; i<n && i<3; i++){
				cardBack[i].setPosition(6, 150+(i*125));
				cardBack[i+n].setPosition(57, 150+(i*125));
			}
			if(n>2)
				for(int i=3; i<n && i<6; i++){
					cardBack[i].setPosition(150+((i-3)*170), 10);
					cardBack[i+n].setPosition(201+((i-3)*170), 10);
				}
			if(n>5)
				for(int i=6; i<n && i<9; i++){
					cardBack[i].setPosition(660, ((i-6)*125)+150);
					cardBack[i+n].setPosition(711, ((i-6)*125)+150);
				}
			if(n>8)
				for(int i=9; i<n; i++){
					cardBack[i].setPosition(150+((i-9)*170), 510);
					cardBack[i+n].setPosition(201+((i-9)*170), 510);
				}
		}
		cardBack[client.getGameData().getPlayerNumber()].setPosition(999,999);
		cardBack[client.getGameData().getPlayerNumber()+n].setPosition(999,999);
	}
	
	void batchCardBacks(int n){
		batch.begin();
		for (int i=0; i<n*2;i++)
			batch.draw(cardBackImage, cardBack[i].x, cardBack[i].y);
		batch.end();
	}
	
	void generateCardaFronts(){
		
	}
	
	
	@Override
	public void render(float delta) {
		client.getGameData().setStatus("MOVE");
		if (updated){
			if (client.getGameData().getStatus().equals("MOVE")){
				this.updated = false;
				System.out.println("zmiana");
				game.setScreen(new GameScreenMove(client,game));
			}
			//game.setScreen(new GameScreenWait(client,game));
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundImage, background.x, background.y);
		batch.end();
		batchCardBacks(client.getGameData().getNumberOfPlayer());
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
