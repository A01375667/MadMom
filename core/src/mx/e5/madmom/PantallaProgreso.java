package mx.e5.madmom;

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
 * Created by Jorge Jiménez on 21/03/17.
 */

public class PantallaProgreso extends Pantalla {
    private final MadMom madMom;
    private final AssetManager manager;

    // Imágenes que se utilizarán
    private Texture texturaFondoProgreso;
    private Texture texturaBtnPausa;
    private Texture texturaVida;
    private Objeto vida1;
    private Objeto vida2;
    private Objeto vida3;
    private Objeto btnPausa;

    // Dibujar
    private SpriteBatch batch;

    // Texto
    private Texto textoPuntos;
    private float tiempoPantalla = 2;

    // Escenas
    private EstadoJuego estado = EstadoJuego.JUGANDO;
    private Stage escenaProgreso;
    private EscenaPausa escenaPausa;
    private EscenaPierde escenaPierde;
    private EscenaGana escenaGana;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();
    int num= (int) MathUtils.random(0, 3);

    public PantallaProgreso(MadMom madMom) {
        this.madMom = madMom;
        manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

        // Definir quién atiende los eventos de touch
        Gdx.input.setInputProcessor(procesadorEntrada);
    }

    private void cargarTexturas() {
        texturaFondoProgreso = manager.get("fondoAjustes.jpg", Texture.class);
        texturaBtnPausa = manager.get("btnPausa.png", Texture.class);
        texturaVida = manager.get("caraVida.png", Texture.class);
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaProgreso = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoProgreso);
        escenaProgreso.addActor(imgFondo);
        textoPuntos = new Texto("fuenteTextoInstruccion.fnt");

        //Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6 * texturaBtnPausa.getWidth() / 4, 18 * texturaBtnPausa.getHeight() / 4);


        vida1 = new Objeto(texturaVida, ANCHO / 5 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
        vida2 = new Objeto(texturaVida, ANCHO / 2 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
        vida3 = new Objeto(texturaVida, 4 * ANCHO / 5 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        escenaProgreso.draw();

        batch.setProjectionMatrix(camara.combined);



        batch.begin();

        textoPuntos.mostrarMensaje(batch, "PUNTUACION:", ANCHO / 2, 6 * ALTO / 7);
        textoPuntos.mostrarMensaje(batch, Integer.toString(madMom.puntosJugador), ANCHO / 2, 5 * ALTO / 7);
        dibujarVidas();
        tiempoPantalla -= delta;

        batch.end();

        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        } else if (estado == EstadoJuego.PIERDE) escenaPierde.draw();

        else if (estado == EstadoJuego.GANADO) escenaGana.draw();
        else if (tiempoPantalla <= 0){
            if (num==1)
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.MATACUCARACHAS, Pantallas.TipoPantalla.JUEGO));
            else if (num==2)
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.INVADERS,  Pantallas.TipoPantalla.JUEGO));
            else madMom.setScreen(new PantallaCargando(madMom,Pantallas.ATRAPAPLATOS,  Pantallas.TipoPantalla.JUEGO));
        }

    }

    private void dibujarVidas() {
        if (madMom.vidasJugador == 3) {
            vida1.dibujar(batch);
            vida2.dibujar(batch);
            vida3.dibujar(batch);
        } else if (madMom.vidasJugador == 2) {
            vida1.dibujar(batch);
            vida2.dibujar(batch);
        } else if (madMom.vidasJugador == 1) {
            vida1.dibujar(batch);
        } else if (madMom.vidasJugador <= 0) {
            estado = EstadoJuego.PIERDE;
            if (escenaPierde == null) {
                escenaPierde = new EscenaPierde(vista, batch);
            }
            Gdx.input.setInputProcessor(escenaPierde);
        }

        if (madMom.puntosJugador >= 1100) {
            estado = EstadoJuego.GANADO;
            if (escenaGana == null) {
                escenaGana = new EscenaGana(vista, batch);
            }
            Gdx.input.setInputProcessor(escenaGana);
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

    private class EscenaPausa extends Stage {


        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear fondo
            Texture texturaFondoPausa = new Texture("fondoPausa.png");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO / 2 - btnMenu.getWidth() / 2, 75);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU,  Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnContinuar = new Texture("btnMusica.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO / 2 - btnContinuar.getWidth() / 2, ALTO * 0.5f);
            btnContinuar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Continuar el juego
                    estado = EstadoJuego.JUGANDO;
                    // Regresa el control a la pantalla
                    Gdx.input.setInputProcessor(procesadorEntrada);
                }
            });
            this.addActor(btnContinuar);



            //Botón sonido ON
            final Texture textureBtnSonidoON=manager.get("cuadroPaloma.png");
            TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
            final ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
            btnSonidoOn.setPosition(ANCHO/2 + btnSonidoOn.getWidth()*3 - btnSonidoOn.getWidth()/2, ALTO/2 - btnSonidoOn.getHeight()/2);
            //agregar el actor a la pantalla
            this.addActor(btnSonidoOn);
            //no dejar visible en la pantalla
            btnSonidoOn.setVisible(false);


            //Botón sonido OFF
            Texture texturaBtnSonidoOFF= manager.get("cuadroVacio.png");
            final TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
            final ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
            btnSonidoOff.setPosition(ANCHO/2 + btnSonidoOff.getWidth()*3, ALTO/2 - btnSonidoOff.getHeight()/2);
            this.addActor(btnSonidoOff);
            btnSonidoOff.setVisible(false);

            if (madMom.estadoMusica==EstadoMusica.PLAY){ //Mostar el boton de sonido ON
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

                    madMom.estadoMusica = EstadoMusica.STOP;
                    Music musicaFondo = manager.get("musicaMenu.mp3");
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

                    madMom.estadoMusica = EstadoMusica.PLAY;
                    Music musicaFondo = manager.get("musicaMenu.mp3");
                    musicaFondo.play();
                    btnSonidoOff.setVisible(false);
                    btnSonidoOn.setVisible(true);
                    btnSonidoOn.setDisabled(false);



                }});
        }

    }

    private class EscenaPierde extends Stage {
        public EscenaPierde(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear fondo
            Texture texturaFondoPausa = new Texture("fondoPerdiste.jpg");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO / 2 - btnMenu.getWidth() / 2, ALTO * 0.2f);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU,  Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnReintentar = new Texture("btnMusica.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(ANCHO / 2 - btnReintentar.getWidth() / 2, ALTO * 0.5f);
            btnReintentar.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Reintentar el juego
                    estado = EstadoJuego.JUGANDO;
                    madMom.vidasJugador = 3;
                    madMom.puntosJugador = 0;
                    if (num==1)
                        madMom.setScreen(new PantallaCargando(madMom, Pantallas.MATACUCARACHAS, Pantallas.TipoPantalla.JUEGO));
                    else if (num==2)
                        madMom.setScreen(new PantallaCargando(madMom, Pantallas.INVADERS,  Pantallas.TipoPantalla.JUEGO));
                    else madMom.setScreen(new PantallaCargando(madMom,Pantallas.ATRAPAPLATOS,  Pantallas.TipoPantalla.JUEGO));
                    // Regresa el control a la pantalla


                }
            });
            this.addActor(btnReintentar);
        }


    }



    private class Procesador implements InputProcessor{
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

    private class EscenaGana extends Stage {
        public EscenaGana(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear fondo
            Texture texturaFondoPausa = new Texture("fondoPantallaGanaste.jpg");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO / 2 - btnMenu.getWidth() / 2, ALTO * 0.2f);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU, Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnReintentar = new Texture("btnMusica.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(ANCHO / 2 - btnReintentar.getWidth() / 2, ALTO * 0.5f);
            btnReintentar.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Reintentar el juego
                    estado = EstadoJuego.JUGANDO;
                    madMom.vidasJugador = 3;
                    madMom.puntosJugador = 0;
                    // Regresa el control a la pantalla
                    madMom.setScreen(new PantallaCargando(madMom, Pantallas.NIVEL, Pantallas.TipoPantalla.MENU));

                }
            });
            this.addActor(btnReintentar);

        }


    }
}
