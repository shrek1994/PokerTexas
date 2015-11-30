package client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;


/**
 * Klasa podstawowa dla GUI zrobionego przy pomocy libgdx
 * @author erinu
 *
 */
public class GUIlibgdx extends Game {
	
	GameClient client;
	

	@Override
	public void create() {
		client = new GameClient();
		this.setScreen(new ConnectionScreen(this, client));
		/*
		cardImage = new Texture(Gdx.files.internal("card.jpg"));
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		background  = new Rectangle();
		background.width = 800;
		background.height = 480;
		font = new BitmapFont();
        font.setColor(Color.BLACK);
        listener1 = new MyTextInputListener();
        listener2 = new MyTextInputListener();
		Gdx.input.getTextInput(listener1, "Podaj adres serwera: ", "localhost", "");
		Skin skin = new Skin();
		TextField textField= new TextField("textField Vallue", skin);

		        textField.setTextFieldListener(new TextFieldListener() {

		            @Override
		            public void keyTyped(TextField textField, char key) {
		                    txtVal= textField.getText();
		            }
		        });
		*/
		
		
	}
	
	@Override
	public void render () {
		super.render();
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*batch.begin();
		batch.draw(backgroundImage, background.x, background.y);
		font.draw(batch, "Hello World", 200, 200);
		batch.end();
		/*if (listener1.called)
			Gdx.input.getTextInput(listener2, "Podaj port serwera: ", "6001", "");*/
	}
}
