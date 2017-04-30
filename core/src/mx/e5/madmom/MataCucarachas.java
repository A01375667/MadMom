package mx.e5.madmom;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Jorge Jiménez on 13/02/17.
 * Created by Ana Paula M on 16/02/17.
 */

public class MataCucarachas extends Pantalla
{

    private final MadMom madMom;
    private final AssetManager manager;

    //Texturas
    private Texture texturaFondoMenu;
    private Texture texturaFondoBanio;
    private Texture texturaBtnPausa;
    private Objeto btnPausa;

    //Personaje
    private Texture texturaCucaracha;
    private Cucaracha cucaracha;

    // Audio
    private Sound efectoAplastar;  // Cuando el usuario golpea a la cucaracha


    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;

    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;



    private EstadoJuego estado = EstadoJuego.JUGANDO;
    private EscenaPausa escenaPausa;


    // Las 10 cucarachas en el juego
    private int Num_Cucarachas=1;
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
        texturaFondoBanio= manager.get("fondoBaño.jpg");
        texturaBtnPausa=manager.get("btnPausa.png");
        texturaCucaracha= manager.get("cucarachaSprite.png");
        efectoAplastar=manager.get("Disparar.mp3");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMataCucarachas = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoBanio);
        escenaMataCucarachas.addActor(imgFondo);
        // Crea las cucarachas y las guarda en el arreglo


        arrCucarachas = new Array <Objeto> ();

        for (int i=0; i<Num_Cucarachas; i++){
            float posx= MathUtils.random(0, ANCHO/2);
            float posy=MathUtils.random(0, ALTO/2);
            cucaracha=new Cucaracha(texturaCucaracha, posy, posx);

            arrCucarachas.add(cucaracha);
            }


        // Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaMataCucarachas.draw();
        actualizarObjeto(delta);
        

        batch.begin();
        dibujarObjetos(arrCucarachas);
        batch.end();

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

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

        btnPausa.dibujar(batch);

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
                efectoAplastar.play();
                cucaracha.setEstadoMovimiento(Cucaracha.EstadoMovimiento.QUIETO);
                cucaracha.setEstadoMovimientoVertical(Cucaracha.EstadoMovimientoVertical.NORMAL);
            }
            }

            if (btnPausa.contiene(v)) {
                // Se pausa el juego
                estado = estado==EstadoJuego.PAUSADO?EstadoJuego.JUGANDO:EstadoJuego.PAUSADO;
                if (estado==EstadoJuego.PAUSADO) {
                    // Activar escenaPausa y pasarle el control
                    if (escenaPausa==null) {
                        escenaPausa = new EscenaPausa(vista, batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
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



    // La escena que se muestra cuando el juego se pausa
   private class EscenaPausa extends Stage
    {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear fondo
            Texture texturaFondoPausa =new Texture("fondoPausa.jpg");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO/2-btnMenu.getWidth()/2, ALTO*0.2f);
            btnMenu.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    madMom.setScreen(new PantallaCargando(madMom,Pantallas.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnContinuar = new Texture("btnVolumen.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO/2-btnContinuar.getWidth()/2, ALTO*0.5f);
            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Continuar el juego
                    estado = EstadoJuego.JUGANDO;
                    // Regresa el control a la pantalla
                    Gdx.input.setInputProcessor(procesadorEntrada);
                }
            });
            this.addActor(btnContinuar);




            /*
            //Botón sonido ON
            Texture textureBtnSonidoON= new Texture("CuadroPaloma.png");
            TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
            ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
            btnSonidoOn.setPosition(ANCHO/2 + btnSonidoOn.getWidth()*3 - btnSonidoOn.getWidth()/2, ALTO/2 - btnSonidoOn.getHeight()/2);


            //Botón sonido OFF
            Texture texturaBtnSonidoOFF= new Texture("CuadroVacio.png");
            TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
            ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
            btnSonidoOff.setPosition(ANCHO/2 + btnSonidoOff.getWidth()*3, ALTO/2 - btnSonidoOff.getHeight()/2);

            /*switch (madMom.estadoMusica){
                case PLAY:
                    escenaPausa.addActor(btnSonidoOn);
                    btnSonidoOn.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            madMom.estadoMusica = EstadoMusica.STOP;
                            musicaFondo = manager.get("musicaMenu.mp3");
                            musicaFondo.stop();
                            madMom.setScreen(new PantallaAjustes(madMom));
                        }
                    });
                    break;
                case STOP:
                    escenaPausa.addActor(btnSonidoOff);
                    btnSonidoOff.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            madMom.estadoMusica = EstadoMusica.PLAY;
                            musicaFondo = manager.get("musicaMenu.mp3");
                            musicaFondo.setLooping(true);
                            musicaFondo.play();
                            madMom.setScreen(new PantallaAjustes(madMom));
                        }
                    });
                    break;
            }*/

        }

    }
}
