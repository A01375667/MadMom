package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jorge Jiménez on 10/04/17.
 */

public class BacteriaInvaders extends Pantalla
{
    private final MadMom madMom;
    private final AssetManager manager;

    // Fondo
    private Fondo fondo;
    private Texture texturaFondo;

    // Enemigos
    private Texture texturaBacteria;
    private Array<Bacteria> arrBacterias;

    // Burbujas para lanzar
    private Texture texturaBurbuja;
    private Array<Burbuja> arrBurbujas;

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;
    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;

    // Imágenes que se utilizarán
    private Texture texturaBotella;

    // Dibujar
    private SpriteBatch batch;


    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();

    // Constructor
    public BacteriaInvaders(MadMom madMom){
        super();
        this.madMom = madMom;
        manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        arrBacterias = new Array<>();
        arrBurbujas = new Array<>();
        texturaFondo = manager.get("fondoInvaders.jpg");
        texturaBacteria = manager.get("bacteria.png");
        texturaBurbuja = manager.get("burbuja.png");

        fondo = new Fondo(texturaFondo);
    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }



    // Procesar entrada
    class Procesador implements InputProcessor
    {
        private Vector3 v = new Vector3();
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false;}

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
