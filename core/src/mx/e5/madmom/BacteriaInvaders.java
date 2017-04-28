package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    private final int NUM_BACTERIAS = 4;

    // Burbujas para lanzar
    private Texture texturaBurbuja;
    private Array<Burbuja> arrBurbujas;

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;
    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;

    // Botella
    Botella botella;
    private Texture texturaBotella;


    // Constructor
    public BacteriaInvaders(MadMom madMom){
        //super();
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
        texturaBotella = manager.get("botella.png");
        fondo = new Fondo(texturaFondo);


        crearBacterias();
        botella = new Botella(texturaBotella, ANCHO/2 - texturaBotella.getWidth(), 0);

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearBacterias() {
        // Crea las bacterias y las guarda en el arreglo
        for (int i = 0; i < NUM_BACTERIAS ; i++) {
            float posx = MathUtils.random(0, ANCHO - texturaBacteria.getWidth());
            float posy = MathUtils.random(ALTO/2, ALTO - texturaBacteria.getHeight());
            Bacteria bacteria = new Bacteria(texturaBacteria, posx, posy);
            arrBacterias.add(bacteria);
        }
    }


    @Override
    public void render(float delta) {

        actualizarBotella(delta);
        actualizarBurbujas(delta);

        // Dibujar
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        fondo.dibujar(batch, 0);

        // Dibujar botella
        botella.dibujar(batch);

        // Dibujar bacterias
        for (Bacteria bacteria : arrBacterias) {
            bacteria.dibujar(batch);
        }
        // Dibujar burbujas
        for (Burbuja burbuja : arrBurbujas) {
            burbuja.dibujar(batch);
        }

        batch.end();
    }

    private void actualizarBotella(float delta) {
        botella.mover(delta);
        if (botella.sprite.getX() >= ANCHO - botella.sprite.getWidth()/2){
            botella.velocidad_x = -500;
        } else if(botella.sprite.getX() <= 0 - botella.sprite.getWidth()/2){
            botella.velocidad_x = 500;
        }
    }

    private void actualizarBurbujas(float delta) {
        for(int i=arrBurbujas.size-1; i>=0; i--) {
            Burbuja burbuja = arrBurbujas.get(i);
            burbuja.mover(delta);
            if (burbuja.sprite.getY()>ALTO) {
                // Se salió de la pantalla
                arrBurbujas.removeIndex(i);
                break;
            }
            // Prueba choque contra todos los enemigos
            for (int j=arrBacterias.size-1; j>=0; j--) {
                Bacteria bacteria = arrBacterias.get(j);
                if (burbuja.chocaCon(bacteria)) {
                    // Borrar hongo, bala, aumentar puntos, etc.
                    arrBacterias.removeIndex(j);
                    arrBurbujas.removeIndex(i);
                    break;  // Siguiente burbuja, ésta ya no existe
                }
            }
        }
    }

    private void disparar() {
        Burbuja burbuja = new Burbuja(texturaBurbuja, botella.sprite.getX() + botella.sprite.getWidth()/2,
                botella.sprite.getY() + botella.sprite.getHeight());
        arrBurbujas.add(burbuja);
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
    private class ProcesadorEntrada implements InputProcessor
    {
        private Vector3 v = new Vector3();

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX, screenY, 0);
            camara.unproject(v);

            if (v.y<ALTO/2) {
                disparar();
            }

            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }

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
    }
}
