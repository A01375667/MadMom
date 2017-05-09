package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Sandy on 5/9/2017.
 */

public class BuscaAnillo extends Pantalla {
    private final MadMom madMom;
    private final AssetManager manager;

    //Texturas
    private Texture texturaFondoBusca;
    private Texture texturaBtnPausa;
    private Texture texturaAnillo;
    private Objeto btnPausa;


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

    //Escenas
    private Stage escenaBuscaAnillo;

    //Objetos
    private Anillo anillo;

    // Dibujar
    private SpriteBatch batch;

    //Escenas
    private Stage escenaMataCucarachas;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();

    //Ubicacion
    float posx;
    float scale;

    public BuscaAnillo(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();

        switch (madMom.nivel){
            case FACIL:
                scale=0.1f;
                break;
            case DIFICIL:
                if (madMom.countJuegos==4) {
                    madMom.tiempoJuego-= madMom.tiempoJuego>5?1:0;;
                    madMom.countJuegos=0;
                    scale=0.05f;

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

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaBuscaAnillo = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoBusca);
        escenaBuscaAnillo.addActor(imgFondo);

        posx= MathUtils.random(0, ANCHO);

        anillo=new Anillo(texturaAnillo,posx,ALTO/2-50, scale);


        // Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);
    }

    private void cargarTexturas() {
        texturaFondoBusca=manager.get("fondoEcontrarAnillo.jpg");
        texturaAnillo=manager.get ("anilloEnc.png");
        texturaBtnPausa=manager.get ("btnPausa.png");
        textoInstruccion = new Texto("fuenteTextoInstruccion.fnt");
        textoTiempo = new Texto("fuenteTiempo.fnt");



    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaBuscaAnillo.draw();

        if (estado==EstadoJuego.JUGANDO){

            batch.begin();
            dibujarObjetos(batch);
            tiempoMiniJuego -= delta;
            textoTiempo.mostrarMensaje(batch, "TIEMPO: ", 180, 18*ALTO/20 + 10);
            textoTiempo.mostrarMensaje(batch, String.format("%.0f", tiempoMiniJuego), 295, 18*ALTO/20 + 10);

            tiempoVisibleInstrucciones -= delta;
            if(tiempoVisibleInstrucciones > 0){
                textoInstruccion.mostrarMensaje(batch, "BUSCA EL ANILLO", ANCHO/2, 3*ALTO/4);
                //textoTiempo.mostrarMensaje(batch, "(Tap en el anillo)", ANCHO/2, ALTO/2 + 100);
            }
            if(tiempoMiniJuego <= 0){
                madMom.vidasJugador--;
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom,
                        mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
            }


            batch.end();}

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

    }

    private void dibujarObjetos(SpriteBatch batch) {
        anillo.dibujar(batch);
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
        manager.unload("fondoEcontrarAnillo.jpg");
        manager.unload("anilloEnc.png");
        manager.unload("fondoPausa.png");
        manager.unload("btnMusica.png");
        manager.unload("btnMENUU.png");
        manager.unload("cuadroVacio.png");
        manager.unload("cuadroPaloma.png");

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

           if(anillo.contiene(v)){

                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
                madMom.puntosJugador+=100;
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
