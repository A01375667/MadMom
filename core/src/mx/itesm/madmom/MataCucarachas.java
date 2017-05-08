package mx.itesm.madmom;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    private Texture texturaFondoBanio;
    private Texture texturaBtnPausa;
    private Objeto btnPausa;

    //Personaje
    private Texture texturaCucaracha;
    private mx.itesm.madmom.Cucaracha cucaracha;

    // Audio
    private Sound efectoAplastar;  // Cuando el usuario golpea a la cucaracha

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;

    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;

    // Textos
    private Texto textoInstruccion;
    private Texto textoTiempo;

    //Pausa
    private EstadoJuego estado = EstadoJuego.JUGANDO;
    private EscenaPausa escenaPausa;


    // Las 10 cucarachas en el juego
    private int Num_Cucarachas=8;
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
        switch (madMom.nivel){
            case FACIL:
                Num_Cucarachas=8;
                break;
            case DIFICIL:
                Num_Cucarachas=13;
                if (madMom.countJuegos==4) {
                    madMom.tiempoJuego-= madMom.tiempoJuego>5?1:0;;
                    madMom.countJuegos=0;

                }
                break;
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
        // Definir quién atiende los eventos de touch
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(procesadorEntrada);


    }

    private void cargarTexturas() {
        texturaFondoBanio= manager.get("fondoBaño.jpg");
        texturaBtnPausa=manager.get("btnPausa.png");
        texturaCucaracha= manager.get("cucarachaSprite.png");
        efectoAplastar=manager.get("Disparar.mp3");
        textoInstruccion = new Texto("fuenteTextoInstruccion.fnt");
        textoTiempo = new Texto("fuenteTiempo.fnt");



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
            cucaracha=new mx.itesm.madmom.Cucaracha(texturaCucaracha, posx, posy);

            arrCucarachas.add(cucaracha);
            }


        // Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaMataCucarachas.draw();
        if (arrCucarachas.size==0){
            madMom.puntosJugador+=150;
            madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
        }

        actualizarObjeto(delta);

        if (estado==EstadoJuego.JUGANDO){

        batch.begin();
        dibujarObjetos(arrCucarachas);

        tiempoMiniJuego -= delta;
        textoTiempo.mostrarMensaje(batch, "TIEMPO: ", 8*ANCHO/10, 5*ALTO/32);
        textoTiempo.mostrarMensaje(batch, String.format("%.0f", tiempoMiniJuego), 10*ANCHO/11, 5*ALTO/32);

        tiempoVisibleInstrucciones -= delta;
        if(tiempoVisibleInstrucciones > 0){
            textoInstruccion.mostrarMensaje(batch, "PARA LA INVASION", ANCHO/2, 3*ALTO/4);
            textoTiempo.mostrarMensaje(batch, "(Tap en las cucarachas)", ANCHO/2, ALTO/2);
        }
        if(tiempoMiniJuego <= 0){
            madMom.vidasJugador--;
            madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
        }


        batch.end();}

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

    }

    private void actualizarObjeto(float delta) {
        // Cucarachas
        for (Objeto cucaracha : arrCucarachas) {
            mx.itesm.madmom.Cucaracha c = (mx.itesm.madmom.Cucaracha)cucaracha;
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
                mx.itesm.madmom.Cucaracha cucaracha = (mx.itesm.madmom.Cucaracha) obj;
            if (cucaracha.contiene(v)) {
                if(madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                efectoAplastar.play();
                arrCucarachas.removeValue(cucaracha, true);
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
            Texture texturaFondoPausa =new Texture("fondoPausa.png");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Continuar
            Texture texturabtnContinuar = new Texture("btnReanudar.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO/2-btnContinuar.getWidth(),  ALTO/2 + btnContinuar.getHeight()/14);
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

            // Música
            Texture texturabtnMusica = new Texture("btnMusica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturabtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(ANCHO/2 - btnMusica.getWidth() - 50,  ALTO/2-110);
            this.addActor(btnMusica);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO/2 - btnMenu.getWidth(),115);
            btnMenu.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Music musicaFondoJuego = manager.get("SpaceSong.mp3");
                    musicaFondoJuego.stop();
                    Music musicaFondo=manager.get("musicaMenu.mp3");
                    musicaFondo.setLooping(true);
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)) musicaFondo.play();
                    // Regresa al menú
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            //Botón sonido ON
            final Texture textureBtnSonidoON=manager.get("cuadroPaloma.png");
            TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
            final ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
            btnSonidoOn.setPosition(ANCHO/2 - 50, ALTO/2 - btnSonidoOn.getHeight()/2 - 20);
            //agregar el actor a la pantalla
            this.addActor(btnSonidoOn);
            //no dejar visible en la pantalla
            btnSonidoOn.setVisible(false);


            //Botón sonido OFF
            Texture texturaBtnSonidoOFF= manager.get("cuadroVacio.png");
            final TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
            final ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
            btnSonidoOff.setPosition(ANCHO/2 - 50, ALTO/2 - btnSonidoOff.getHeight()/2 - 20);
            this.addActor(btnSonidoOff);
            btnSonidoOff.setVisible(false);

            if (madMom.estadoMusica== mx.itesm.madmom.EstadoMusica.PLAY){ //Mostar el boton de sonido ON
                btnSonidoOn.setVisible(true);
                //Deshabilitar el litsener del boton sonido Off
                btnSonidoOff.setDisabled(true);
            }

            else {
                btnSonidoOn.setDisabled(true);
                btnSonidoOff.setVisible(true);
            }

            //Accion boton sonido ON
            this.addActor(btnSonidoOn);
            btnSonidoOn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    madMom.estadoMusica = mx.itesm.madmom.EstadoMusica.STOP;
                    Music musicaFondo = manager.get("SpaceSong.mp3");
                    musicaFondo.stop();
                    btnSonidoOn.setVisible(false);
                    btnSonidoOff.setVisible(true);
                    btnSonidoOff.setDisabled(false);
                }
            });

            //Accion boton sonido OFF
            btnSonidoOff.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    madMom.estadoMusica = mx.itesm.madmom.EstadoMusica.PLAY;
                    Music musicaFondo = manager.get("SpaceSong.mp3");
                    musicaFondo.play();
                    btnSonidoOff.setVisible(false);
                    btnSonidoOn.setVisible(true);
                    btnSonidoOn.setDisabled(false);
                }});
        }

    }
}