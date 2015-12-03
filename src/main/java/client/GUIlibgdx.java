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
		ConnectionScreen conScreen = new ConnectionScreen(this, client);
		this.setScreen(conScreen);
	}
	
	@Override
	public void render () {
		super.render();
	}
}
