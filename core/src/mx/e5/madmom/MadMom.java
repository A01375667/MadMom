package mx.e5.madmom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MadMom extends Game
{
	SpriteBatch batch;
    private final AssetManager assetManager;
	public int vidasJugador = 3;
	public int puntosJugador = 0;
	public EstadoMusica estadoMusica = EstadoMusica.PLAY;
	public Dificultades nivel;
    public float tiempoJuego=10;
    public int countJuegos=0;

	public MadMom() {
		assetManager = new AssetManager();
	}

	@Override
	public void create () {
		// Pone la pantalla inicial (Splash)
		setScreen(new PantallaInicial(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void dispose(){
	}
}
