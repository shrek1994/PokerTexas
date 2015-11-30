package client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class ConnectionScreen implements Screen{
	
	private Game game;
	private Stage stage;
	SpriteBatch batch;
	Texture cardImage;
	Texture backgroundImage;
	private OrthographicCamera camera;
	//private Rectangle card;
	private Rectangle background;
	private BitmapFont font;
	String txtVal;
	private TextButton btnConnect;
	private TextField txfAddress;
	private TextField txfPort;
	private GameClient client;
	
	public ConnectionScreen(Game nevada, GameClient c){
		client = c;
		stage= new Stage();
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		btnConnect = new TextButton("Connect", skin);
		btnConnect.setPosition(450, 230);
		btnConnect.setSize(100, 80);
		stage.addActor(btnConnect);
		cardImage = new Texture(Gdx.files.internal("card.jpg"));
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		background  = new Rectangle();
		background.width = 800;
		background.height = 480;
		btnConnect.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button){
				processConnection();
			}
		});
		
		txfAddress = new TextField("localhost",skin);
		txfPort = new TextField("6068",skin);
		txfAddress.setPosition(140, 250);
		txfAddress.setSize(200,60);
		txfPort.setSize(200,60);
		txfPort.setPosition(140, 170);
		stage.addActor(txfPort);
		stage.addActor(txfAddress);
		font = new BitmapFont();
        font.setColor(Color.BLACK);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Metoda wywolujaca polaczenie z serwerem
	 */
	public void processConnection(){
		boolean connection = client.setUpConnection(txfAddress.getText(), txfPort.getText());
		if (connection){
			client.connectionEstablished = true;
			game.setScreen(new GameScreenWait());
		}
		//else
			//connection failed
		//System.out.println(txfAddress.getText() + " "+ txfPort.getText());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(backgroundImage, background.x, background.y);
		font.draw(batch, "IP:", 70, 290);
		font.draw(batch, "PORT:", 70, 210);
		batch.end();
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// metoda tylko dla androida
		
	}

	@Override
	public void resume() {
		// metoda tylko dla androida
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
