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
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Jorge Jiménez on 21/03/17.
 */

public class PantallaProgreso extends Pantalla {
    private final mx.itesm.madmom.MadMom madMom;
    private final AssetManager manager;

    // Imágenes que se utilizarán
    private Texture texturaFondoProgreso;
    private Texture texturaBtnPausa;
    private Texture texturaVida;
    private mx.itesm.madmom.Objeto vida1;
    private mx.itesm.madmom.Objeto vida2;
    private mx.itesm.madmom.Objeto vida3;
    private mx.itesm.madmom.Objeto btnPausa;

    // Dibujar
    private SpriteBatch batch;

    // Texto
    private mx.itesm.madmom.Texto textoPuntos;
    private float tiempoPantalla = 2;

    // Escenas
    private mx.itesm.madmom.EstadoJuego estado = mx.itesm.madmom.EstadoJuego.JUGANDO;
    private Stage escenaProgreso;
    private EscenaPausa escenaPausa;
    private EscenaPierde escenaPierde;
    private EscenaGana escenaGana;

    //Efectos
    private Sound efectoBoton;
    Music musicaFondoJuego;
    Music musicaFondo;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();
    int num =  MathUtils.random(0, 4);

    public PantallaProgreso(mx.itesm.madmom.MadMom madMom) {
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
        efectoBoton=manager.get("boton.mp3");
        musicaFondoJuego = manager.get("SpaceSong.mp3");
        musicaFondo=manager.get("musicaMenu.mp3");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaProgreso = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoProgreso);
        escenaProgreso.addActor(imgFondo);
        textoPuntos = new mx.itesm.madmom.Texto("fuenteTextoInstruccion.fnt");

        //Botón pausa
        btnPausa = new mx.itesm.madmom.Objeto(texturaBtnPausa, ANCHO - 6 * texturaBtnPausa.getWidth() / 4, 18 * texturaBtnPausa.getHeight() / 4);


        vida1 = new mx.itesm.madmom.Objeto(texturaVida, ANCHO / 5 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
        vida2 = new mx.itesm.madmom.Objeto(texturaVida, ANCHO / 2 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
        vida3 = new mx.itesm.madmom.Objeto(texturaVida, 4 * ANCHO / 5 - texturaVida.getWidth() / 2, 1 * ALTO / 5 - texturaVida.getHeight() / 4);
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

        if (estado== mx.itesm.madmom.EstadoJuego.JUGANDO)
            tiempoPantalla -= delta;
        batch.end();

        if (tiempoPantalla <= 0) {
            if (num==1)
            {
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MATACUCARACHAS, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                madMom.countJuegos++;
            }
            else if (num==2 ){
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.INVADERS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                madMom.countJuegos++;
            }
            else if (num==3){
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.ATRAPAPLATOS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                madMom.countJuegos++;}
            else {
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, Pantallas.BUSCARANILLO,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                madMom.countJuegos++;

            }
        }

        if (estado == mx.itesm.madmom.EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if (estado == mx.itesm.madmom.EstadoJuego.PIERDE) escenaPierde.draw();

        if (estado == mx.itesm.madmom.EstadoJuego.GANADO) escenaGana.draw();


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
            estado = mx.itesm.madmom.EstadoJuego.PIERDE;
            if (escenaPierde == null) {
                escenaPierde = new EscenaPierde(vista, batch);
            }
            Gdx.input.setInputProcessor(escenaPierde);
        }

        if (madMom.nivel.equals(mx.itesm.madmom.Dificultades.FACIL)&&madMom.puntosJugador >= 1500) {
            estado = mx.itesm.madmom.EstadoJuego.GANADO;
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
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                        efectoBoton.play();
                    // Continuar el juego
                    estado = mx.itesm.madmom.EstadoJuego.JUGANDO;
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
                    musicaFondoJuego.stop();
                    musicaFondo.setLooping(true);
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)){
                        efectoBoton.play();
                        musicaFondo.play();}

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
                    efectoBoton.play();
                    madMom.estadoMusica = mx.itesm.madmom.EstadoMusica.STOP;
                    musicaFondoJuego.stop();
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
                    musicaFondoJuego.play();
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
            btnMenu.setPosition(ANCHO/2 - btnMenu.getWidth() -20, btnMenu.getHeight()/5 - 40);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    musicaFondoJuego.stop();
                    musicaFondo.setLooping(true);
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)){
                        efectoBoton.play();
                        musicaFondo.play();}
                    // Regresa al menú
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU,  mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnReintentar = new Texture("btnReanudar.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(btnReintentar.getWidth()/4, ALTO - btnReintentar.getHeight() + 40);
            btnReintentar.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                        efectoBoton.play();
                    // Reintentar el juego
                    estado = mx.itesm.madmom.EstadoJuego.JUGANDO;
                    madMom.tiempoJuego=10;

                    madMom.vidasJugador = 3;
                    madMom.puntosJugador = 0;
                    if (num==1)
                        madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MATACUCARACHAS, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                    else if (num==2)
                        madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.INVADERS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                    else madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.ATRAPAPLATOS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
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
                if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                    efectoBoton.play();
                // Se pausa el juego
                estado = estado== mx.itesm.madmom.EstadoJuego.PAUSADO? mx.itesm.madmom.EstadoJuego.JUGANDO: mx.itesm.madmom.EstadoJuego.PAUSADO;
                if (estado== mx.itesm.madmom.EstadoJuego.PAUSADO) {
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
            Texture texturaFondoPausa = new Texture("fondoGanaste.jpg");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = new Texture("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO/2 + btnMenu.getWidth()/2, btnMenu.getHeight()/5);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    musicaFondoJuego.stop();
                    musicaFondo.setLooping(true);
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)){
                        efectoBoton.play();
                        musicaFondo.play();};
                    // Regresa al menú
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
                }
            });
            this.addActor(btnMenu);

            // Continuar
            Texture texturabtnReintentar = new Texture("btnReanudar.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(ANCHO/2 - btnReintentar.getWidth()/7, ALTO - btnReintentar.getHeight()+ 20);
            btnReintentar.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                        efectoBoton.play();
                    // Reintentar el juego
                    estado = mx.itesm.madmom.EstadoJuego.JUGANDO;
                    madMom.vidasJugador = 3;
                    madMom.puntosJugador = 0;
                    // Regresa el control a la pantalla
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.NIVEL, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));

                }
            });
            this.addActor(btnReintentar);

        }


    }
}
