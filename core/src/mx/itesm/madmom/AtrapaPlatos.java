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

    private Array<objetoAtrapa> arrBasura;
    private Array<objetoAtrapa> arrPlatos;

    private Platos platos1;

    private float posx;
    private int tipObjeto;
    private float tiempoMax;
    private float tiempoMin;

    //tiempo de espera entre objetos
    private float tiempoEsperaB;
    private int maxBasura;
    private int maxPlatos;

    //Escenas
    private Stage escenaAtrapaPlatos;
    private Stage escenaPausa;

    // Procesador de eventos
    private final Procesador procesadorEntrada = new Procesador();


    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;

    // Tiempo del minijuego
    private float tiempoMiniJuego;

    // Textos
    private Texto textoInstruccion;
    private Texto textoTiempo;





    public AtrapaPlatos(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
        this.tiempoMiniJuego=madMom.tiempoJuego;

        switch (madMom.nivel){
            case FACIL:
                tiempoMin=2.0f;
                tiempoMax=3.0f;
                maxPlatos=5;
                maxBasura=5;
                break;
            case DIFICIL:
                tiempoMin=0.5f;
                tiempoMax=2.5f;
                maxPlatos=(int)tiempoMiniJuego-1;
                maxBasura=(int)tiempoMiniJuego+1;
                if (madMom.countJuegos==4) {
                    madMom.tiempoJuego-= madMom.tiempoJuego>5?1:0;
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

    private void crearObjetos() {
        batch = new SpriteBatch();

        escenaAtrapaPlatos = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoCocina);
        escenaAtrapaPlatos.addActor(imgFondo);

        platos1= new Platos(texturaPlatos, ANCHO/2, 100);
        arrBasura = new Array <objetoAtrapa> ();
        arrPlatos= new Array <objetoAtrapa> ();

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
        textoInstruccion = new Texto("fuenteTextoInstruccion.fnt");
        textoTiempo = new Texto("fuenteTiempo.fnt");

    }

    @Override
    public void render(float delta) {

        borrarPantalla();
        escenaAtrapaPlatos.draw();




        if(estado==EstadoJuego.JUGANDO) {

            batch.begin();
            actualizarObjeto(delta);

            dibujarObjeto(platos1);
            dibujarObjetos(arrBasura);
            dibujarObjetos(arrPlatos);

            actualizarObjeto(delta);


            tiempoMiniJuego -= delta;
            textoTiempo.mostrarMensaje(batch, "TIEMPO: ", 140, 13*ALTO/14);
            textoTiempo.mostrarMensaje(batch, String.format("%.0f", tiempoMiniJuego), 255,13*ALTO/14);

            tiempoVisibleInstrucciones -= delta;
            if (tiempoVisibleInstrucciones > 0) {
                textoInstruccion.mostrarMensaje(batch, "Atrapa los platos", ANCHO / 2, 3 * ALTO / 4);
                textoTiempo.mostrarMensaje(batch, "(Toca izquierda/derecha de la pantalla) ", ANCHO/2, ALTO/2);
            }

            if(tiempoMiniJuego <= 0){
                madMom.vidasJugador--;
                madMom.setScreen(new PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }

            else if (platos1.getCountPlatos()>=maxPlatos-2){
                madMom.puntosJugador+=10;
                madMom.setScreen(new PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }


            batch.end();
        }



        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

    }

    private void dibujarObjeto(Objeto objeto) {
        objeto.dibujar(batch);
    }

    private void actualizarObjeto(float delta) {
        platos1.actualizar(delta);

        //Generar nuevo objeto
        tiempoEsperaB-=delta;
        tipObjeto=MathUtils.random(0,2);
        posx= MathUtils.random(ANCHO/2-100, ANCHO/2+100);

        if (tipObjeto==1) {

            if (tiempoEsperaB <= 0) {
                if (numBasura<=maxBasura){

                    tiempoEsperaB = MathUtils.random(tiempoMin, tiempoMax);
                    tiempoMax -= tiempoMax > tiempoMin ? 10 * delta : 0;
                    objetoAtrapa basura = new objetoAtrapa(texturaBasura, posx, ALTO - 200, objetoAtrapa.Tipo.BASURA);
                    arrBasura.add(basura);
                    numBasura++;
                }
            }
        }

        else if (tipObjeto==0){
            if (tiempoEsperaB <= 0) {
                if (numBasura<=maxBasura){
                    tiempoEsperaB = MathUtils.random(tiempoMin, tiempoMax);
                    tiempoMax -= tiempoMax > tiempoMin ? 10 * delta : 0;
                    objetoAtrapa basura = new objetoAtrapa(texturaBasura2, posx, ALTO - 200, objetoAtrapa.Tipo.BASURA);
                    arrBasura.add(basura);
                    numBasura++;
                }
            }

        }

        else{
            if (tiempoEsperaB <= 0) {
                if (numPlatos<=maxPlatos){
                    tiempoEsperaB = MathUtils.random(tiempoMin, tiempoMax);
                    tiempoMax -= tiempoMax > tiempoMin ? 10 * delta : 0;
                    objetoAtrapa plato = new objetoAtrapa(texturaPlato, posx, ALTO - 200, objetoAtrapa.Tipo.PLATO);
                    arrPlatos.add(plato);
                    numPlatos++;
                }

            }

        }
        for (objetoAtrapa basura: arrBasura){
            basura.actualizar();

        }


        for (objetoAtrapa plato : arrPlatos) {
            plato.actualizar();
        }


       for (int i=arrBasura.size-1; i>=0; i--){
            objetoAtrapa bas=arrBasura.get(i);
            if (bas.colisiona(platos1)){
                arrBasura.removeIndex(i);
                if (platos1.getCountPlatos()==0){
                    madMom.vidasJugador--;
                    madMom.setScreen(new PantallaCargando(madMom, mx.itesm.madmom.Pantallas.PROGRESO, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));}
                else
                platos1.setCountPlatos(false);


            }
        }

        for (int i=arrPlatos.size-1; i>=0; i--){
            objetoAtrapa pla=arrPlatos.get(i);
            if (pla.colisiona(platos1)){
                arrPlatos.removeIndex(i);
                madMom.puntosJugador+=50;
                platos1.setCountPlatos(true);
            }
        }



    }

    private void dibujarObjetos(Array<objetoAtrapa> arreglo) {

        btnPausa.dibujar(batch);

        for (objetoAtrapa objeto : arreglo) {
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
                platos1.setEstadoMovimiento(Platos.EstadoMovimiento.QUIETO);
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
            btnMusica.setPosition(ANCHO/2 - btnMusica.getWidth()- 50,  ALTO/2-110);
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
                    madMom.setScreen(new PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
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
                    Music musicaFondoJuego = manager.get("SpaceSong.mp3");
                    musicaFondoJuego.play();

                    btnSonidoOff.setVisible(false);
                    btnSonidoOn.setVisible(true);
                    btnSonidoOn.setDisabled(false);
                }});

        }

    }


}
