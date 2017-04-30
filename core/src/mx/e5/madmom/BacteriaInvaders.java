package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private int numBacterias = 3;
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
    private float tiempoCarga = 0;

    // Botella
    Botella botella;
    private Texture texturaBotella;

    // Textos
    private Texto textoInstruccion;
    private Texto textoTiempo;

    // PAUSAR
    private Texture texturaBtnPausa;
    private Objeto btnPausa;
    private EstadoJuego estado = EstadoJuego.JUGANDO;
    private BacteriaInvaders.EscenaPausa escenaPausa;

    // Procesador
    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada();




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
        texturaBtnPausa = manager.get("btnPausa.png");
        spriteEncerrada = new Sprite(texturaEncerrada);
        fondo = new Fondo(texturaFondo);
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);

        crearBacterias();
        botella = new Botella(texturaBotella, ANCHO/2 - texturaBotella.getWidth(), 0);

        textoInstruccion = new Texto("fuenteTextoInstruccion.fnt");
        textoTiempo = new Texto("fuenteTiempo.fnt");

        Gdx.input.setInputProcessor(procesadorEntrada);
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
        tiempoCarga -= delta;

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

        tiempoMiniJuego -= delta;
        textoTiempo.mostrarMensaje(batch, "TIEMPO: ", 8*ANCHO/10, 5*ALTO/32);
        textoTiempo.mostrarMensaje(batch, String.format("%.0f", tiempoMiniJuego), 10*ANCHO/11, 5*ALTO/32);

        tiempoVisibleInstrucciones -= delta;
        if(tiempoVisibleInstrucciones > 0){
            textoInstruccion.mostrarMensaje(batch, "LIMPIA! \n  (TAP)", ANCHO/2, 3*ALTO/4);
        }
        if(tiempoMiniJuego <= 0){
            madMom.vidasJugador--;
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.PROGRESO));
        }

        btnPausa.dibujar(batch);

        batch.end();

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }
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

            if (tiempoCarga <= 0) {
                disparar();
                tiempoCarga += 1f;
            }

            if (btnPausa.contiene(v)) {
                // Se pausa el juego
                estado = estado==EstadoJuego.PAUSADO?EstadoJuego.JUGANDO:EstadoJuego.PAUSADO;
                if (estado==EstadoJuego.PAUSADO) {
                    // Activar escenaPausa y pasarle el control
                    if (escenaPausa==null) {
                        escenaPausa = new BacteriaInvaders.EscenaPausa(vista, batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                }
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