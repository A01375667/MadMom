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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Sandy on 5/3/2017.
 */

public class AtrapaPlatos extends Pantalla {

    private final MadMom madMom;
    private final AssetManager manager;

    private EstadoJuego estado = EstadoJuego.JUGANDO;

    //Texturas
    private Texture texturaFondoCocina;
    private Texture texturaBtnPausa;
    private Objeto btnPausa;


    //Objetos
    private Texture texturaPlatos;
    private Texture texturaBasura;
    private Texture texturaBasura2;
    private Texture texturaPlato;

    //Arreglo con objetos
    private int numPlatos=1;
    private int numBasura=1;

    private Array<Objeto> arrBasura;
    private Array<Objeto> arrPlatos;

    private objetoAtrapa basura;
    private objetoAtrapa plato;
    private Platos platos1;

    //Escenas
    private Stage escenaAtrapaPlatos;
    private Stage escenaPausa;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();

    public AtrapaPlatos(MadMom madMom) {
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

    private void crearObjetos() {
        batch = new SpriteBatch();

        escenaAtrapaPlatos = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoCocina);
        escenaAtrapaPlatos.addActor(imgFondo);

        platos1= new Platos(texturaPlatos, ANCHO/2, 100);
        arrBasura = new Array <Objeto> ();
        arrPlatos= new Array <Objeto> ();


        for (int i=0; i<numBasura; i++){

            float posx= MathUtils.random(ANCHO/2-100, ANCHO/2+100);
            float posy=ALTO-100;
            if (MathUtils.randomBoolean()) basura=new objetoAtrapa(texturaBasura, posx, posy, objetoAtrapa.Tipo.BASURA);
            else  basura=new objetoAtrapa(texturaBasura2, posx, posy, objetoAtrapa.Tipo.BASURA);

            arrBasura.add(basura);
        }


        for (int i=0; i<numPlatos; i++){

            float posx= MathUtils.random(ANCHO/2-100, ANCHO/2+100);
            float posy=ALTO-100;
            plato=new objetoAtrapa(texturaPlato,posx, posy, objetoAtrapa.Tipo.PLATO);

            arrPlatos.add(plato);
        }


        // Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);

    }

    private void cargarTexturas() {
        texturaFondoCocina= manager.get("fondoCocina.jpg");
        texturaPlatos=manager.get("PLATOS_sprite.png");
        texturaBtnPausa= manager.get("btnPausa.png");
        texturaBasura= manager.get("manzana.png");
        texturaBasura2=manager.get("pescado.png");
        texturaPlato= manager.get("Plato.png");

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaAtrapaPlatos.draw();

        //if(estado==EstadoJuego.JUGANDO){
            batch.begin();
            dibujarObjeto(platos1);
            dibujarObjetos(arrBasura);
            dibujarObjetos(arrPlatos);

            actualizarObjeto(delta);
            batch.end();
        //}

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

    }

    private void dibujarObjeto(Objeto objeto) {
        objeto.dibujar(batch);
    }

    private void actualizarObjeto(float delta) {
        for (Objeto basura : arrBasura) {
            objetoAtrapa b = (objetoAtrapa) basura;
            b.actualizar();

            if (((objetoAtrapa) basura).colisiona(platos1)){
                platos1.setCountPlatos(false);
                arrBasura.removeValue(basura, true);
            }
        }

        for (Objeto plato : arrPlatos) {
            objetoAtrapa p = (objetoAtrapa) plato;
            p.actualizar();
            if (((objetoAtrapa) plato).colisiona(platos1)){
                platos1.setCountPlatos(true);
                arrPlatos.removeValue(plato, true);
            }
        }

        platos1.actualizar(delta);

    }

    private void dibujarObjetos(Array<Objeto> arreglo) {

        btnPausa.dibujar(batch);

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
        manager.unload("fondoCocina.jpg");
        manager.unload("PLATOS_sprite.png");
        manager.unload("btnPausa.png");
        manager.unload("manzana.png");
        manager.unload("pescado.png");
        manager.unload("Plato.png");
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

            if (v.x < Pantalla.ANCHO/2)
                platos1.setEstadoMovimiento(Platos.EstadoMovimiento.MOV_IZQUIERDA);
            else
                platos1.setEstadoMovimiento(Platos.EstadoMovimiento.MOV_DERECHA);




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
            platos1.setEstadoMovimiento(Platos.EstadoMovimiento.QUIETO);
            return true;
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
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear fondo
            Texture texturaFondoPausa = manager.get("fondoPausa.png");
            Image imgFondo = new Image(texturaFondoPausa);
            this.addActor(imgFondo);

            // Menu
            Texture texturaBtnMenu = manager.get("btnMENUU.png");
            TextureRegionDrawable trdMenu = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMenu));
            ImageButton btnMenu = new ImageButton(trdMenu);
            btnMenu.setPosition(ANCHO / 2 - btnMenu.getWidth() / 2, 75);
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU));
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
            final Texture textureBtnSonidoON = manager.get("cuadroPaloma.png");
            TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
            final ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
            btnSonidoOn.setPosition(ANCHO / 2 + btnSonidoOn.getWidth() * 3 - btnSonidoOn.getWidth() / 2, ALTO / 2 - btnSonidoOn.getHeight() / 2);
            //agregar el actor a la pantalla
            this.addActor(btnSonidoOn);
            //no dejar visible en la pantalla
            btnSonidoOn.setVisible(false);


            //Botón sonido OFF
            Texture texturaBtnSonidoOFF = manager.get("cuadroVacio.png");
            final TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
            final ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
            btnSonidoOff.setPosition(ANCHO / 2 + btnSonidoOff.getWidth() * 3, ALTO / 2 - btnSonidoOff.getHeight() / 2);
            this.addActor(btnSonidoOff);
            btnSonidoOff.setVisible(false);

            if (madMom.estadoMusica == EstadoMusica.PLAY) { //Mostar el boton de sonido ON
                btnSonidoOn.setVisible(true);
                //Deshabilitar el litsener del boton sonido Off
                btnSonidoOff.setDisabled(true);
            } else {
                btnSonidoOn.setDisabled(true);
                btnSonidoOff.setVisible(true);
            }


            //Accion boton sonido ON
            this.addActor(btnSonidoOn);
            btnSonidoOn.addListener(new ClickListener() {
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
            btnSonidoOff.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    madMom.estadoMusica = EstadoMusica.PLAY;
                    Music musicaFondo = manager.get("musicaMenu.mp3");
                    musicaFondo.play();
                    btnSonidoOff.setVisible(false);
                    btnSonidoOn.setVisible(true);
                    btnSonidoOn.setDisabled(false);


                }
            });

        }

    }


}
