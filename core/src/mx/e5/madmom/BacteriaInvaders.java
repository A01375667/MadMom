package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private int numBacterias = 4;
    private int bacteriasAtrapadas = 0;

    // Burbujas para lanzar
    private Texture texturaBurbuja;
    private Array<Burbuja> arrBurbujas;
    private Sprite spriteEncerrada;
    private Texture texturaEncerrada;

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;
    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;
    // Tiempo de recarga para disparar
    private float tiempoCarga = 1;

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
        texturaBacteria = manager.get("spriteBacterias.png");
        texturaBurbuja = manager.get("burbuja.png");
        texturaBotella = manager.get("botella.png");
        texturaEncerrada = manager.get("bacteriaEncerrada.png");
        spriteEncerrada = new Sprite(texturaEncerrada);
        fondo = new Fondo(texturaFondo);


        crearBacterias();
        botella = new Botella(texturaBotella, ANCHO/2 - texturaBotella.getWidth(), 0);

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearBacterias() {
        float area = ANCHO/numBacterias;
        // Crea las bacterias y las guarda en el arreglo
        for (int i = 0; i < numBacterias ; i++) {
            float posx = MathUtils.random(ANCHO - (area*(numBacterias-i)), area*i);
            float posy = MathUtils.random(ALTO/2, ALTO - texturaBacteria.getHeight());
            Bacteria bacteria = new Bacteria(texturaBacteria, posx, posy);
            arrBacterias.add(bacteria);
        }
    }


    @Override
    public void render(float delta) {

        if(bacteriasAtrapadas >= numBacterias){
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.PROGRESO));
        }

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
            // Prueba choque contra todas las bacterias
            for (int j=arrBacterias.size-1; j>=0; j--) {
                Bacteria bacteria = arrBacterias.get(j);
                if (burbuja.chocaCon(bacteria)) {
                    // Encerrar bacteria en burbuja
                    float posx = arrBacterias.get(j).sprite.getX();
                    float posy = arrBacterias.get(j).sprite.getY();
                    arrBacterias.removeIndex(j);
                    arrBurbujas.get(i).sprite.set(spriteEncerrada);
                    arrBurbujas.get(i).sprite.setPosition(posx, posy);
                    bacteriasAtrapadas++;
                    madMom.puntosJugador += 100;
                    //arrBurbujas.removeIndex(i);
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