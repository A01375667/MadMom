package mx.itesm.madmom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MadMom extends Game
{
	SpriteBatch batch;
    private final AssetManager assetManager;
	public int vidasJugador = 3;
	public int puntosJugador = 0;
	public EstadoMusica estadoMusica = EstadoMusica.PLAY;
	public mx.itesm.madmom.Dificultades nivel;
    public float tiempoJuego=10;
    public int countJuegos=0;

	public MadMom() {
		assetManager = new AssetManager();
	}

	@Override
	public void create () {
		// Pone la pantalla inicial (Splash)
		setScreen(new mx.itesm.madmom.PantallaInicial(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void dispose(){
	}
}
