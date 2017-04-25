package mx.e5.madmom;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Jorge Jiménez on 13/02/17.
 * Created by Ana Paula M on 16/02/17.
 */

public class MataCucarachas extends Pantalla
{

    private final MadMom madMom;
    private final AssetManager manager;

    //Texturas
    private Texture texturaFondoBanio;
    private Texture texturaBtnPausa;

    //Personaje
    private Texture texturaCucaracha;
    private Cucaracha cucaracha;

    // Audio
    private Sound efectoGolpe;  // Cuando el usuario golpea a la cucaracha

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;

    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;

    // Posiciones cucarachas
    float posx;
    float posy;

    private EstadoJuego estado = EstadoJuego.JUGANDO;
    //private EscenaPausa escenaPausa;


    // Las 10 cucarachas en el juego
    private int Num_Cucarachas = 6;
    private Array<Objeto> arrCucarachas;

    // Dibujar
    private SpriteBatch batch;

    //Escenas
    private Stage escenaMataCucarachas;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();



    public MataCucarachas(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        // Definir quién atiende los eventos de touch
        Gdx.input.setInputProcessor(procesadorEntrada);


    }

    private void cargarTexturas() {
        texturaFondoBanio= new Texture("fondoBaño.jpg");
        texturaBtnPausa=new Texture("btnPausa.png");
        texturaCucaracha= new Texture("cucarachaSprite.png");



    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMataCucarachas = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoBanio);
        escenaMataCucarachas.addActor(imgFondo);

        // Crea las cucarachas y las guarda en el arreglo

        arrCucarachas = new Array <Objeto> ();

        long inicio = System.nanoTime();

        for (int i=0; i<Num_Cucarachas; i++){
            // OPTIMIZACIÓN: Se pusieron las posiciones de las
            // nuevas cucarachas como variables de instancia.
            // El tiempo mejora aproximadamente 10%.
            /* float */ posx= MathUtils.random(0, ANCHO - texturaCucaracha.getWidth());
            /* float */ posy=MathUtils.random(0, ALTO - texturaCucaracha.getHeight());
            cucaracha=new Cucaracha(texturaCucaracha, posx, posy);
            arrCucarachas.add(cucaracha);
            }

        long fin = System.nanoTime();
        Gdx.app.log("creandoCucas", "Tiempo: " + (fin - inicio)/1000);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaMataCucarachas.draw();
        actualizarObjeto(delta);
        

        batch.begin();
        dibujarObjetos(arrCucarachas);
        batch.end();

    }

    private void actualizarObjeto(float delta) {
        // Cucarachas
        for (Objeto cucaracha : arrCucarachas) {
            Cucaracha c = (Cucaracha)cucaracha;
            c.actualizar(delta);
        }
    }

    private void dibujarObjetos(Array<Objeto> arreglo) {
        // Dibujar los objetos
        for (Objeto objeto : arreglo) {
            objeto.dibujar(batch);


        }
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


    private class Procesador implements InputProcessor {
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
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX, screenY, 0);
            camara.unproject(v);
            for (Objeto obj : arrCucarachas){
                Cucaracha cucaracha = (Cucaracha) obj;
            if (cucaracha.contiene(v)) {
                cucaracha.setEstadoMovimiento(Cucaracha.EstadoMovimiento.QUIETO);
                cucaracha.setEstadoMovimientoVertical(Cucaracha.EstadoMovimientoVertical.NORMAL);
            }
            }
            return true;
        }

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
