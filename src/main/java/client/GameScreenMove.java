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

public class GameScreenMove implements Screen, Observer {
	
	Stage stage;
	SpriteBatch batch;
	Texture cardImage;
	Texture cardBackImage;
	Texture backgroundImage;
	Rectangle card;
	Rectangle cardBack[];
	Rectangle background;
	BitmapFont font;
	String txtVal;
	GameClient client;
	private boolean updated;
	private Game game;
	private TextField betValue;
	private TextButton actions[];
	
	public GameScreenMove(GameClient c, Game g){
		this.client = c;
		this.game = g;
		client.getGameData().addObserver(this);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		betValue = new TextField("",skin);
		betValue.setPosition(300, 170);
		stage.addActor(betValue);
		actions = new TextButton[6];
		actions[0] = new TextButton("Check", skin);
		actions[1] = new TextButton("Bet", skin);
		actions[2] = new TextButton("Raise", skin);
		actions[3] = new TextButton("Call", skin);
		actions[4] = new TextButton("Fold", skin);
		actions[5] = new TextButton("AllIn", skin);
		betValue.setText("0.0");
		for (int i=0; i<6; i++){
			actions[i].setPosition(150+i*80, 230);
			actions[i].setSize(70, 50);
			stage.addActor(actions[i]);
			actions[i].addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button){
					//this line
					try{
						client.getGameData().setActionOfPlayerX(client.getGameData().getPlayerNumber(),new ActionMsg(ActionType.valueOf(actions[client.getGameData().getPlayerNumber()].getText().toString()),Double.parseDouble(betValue.getText())));
					}
					catch(Exception ex){
						client.getGameData().setActionOfPlayerX(client.getGameData().getPlayerNumber(),new ActionMsg(ActionType.valueOf(actions[client.getGameData().getPlayerNumber()].getText().toString()),0.0));
					}
					game.setScreen(new GameScreenWait(client,game));
				}
			});
		}
		batch = new SpriteBatch();
		background  = new Rectangle();
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		background.width = 800;
		background.height = 600;
		generateCardsBacks(client.getGameData().getNumberOfPlayer());
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
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundImage, background.x, background.y);
		batch.end();
		stage.act(delta);
		stage.draw();
		batchCardBacks(client.getGameData().getNumberOfPlayer());
	}
	
	void batchCardBacks(int n){
		batch.begin();
		for (int i=0; i<n*2;i++)
			batch.draw(cardBackImage, cardBack[i].x, cardBack[i].y);
		batch.end();
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
