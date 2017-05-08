package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Sandy on 3/28/2017.
 */

public class PantallaNivel extends Pantalla {

    private final MadMom madMom;

    //Texturas
    private Texture texturaFondoNivel;
    private Texture texturaBtnNivel1;
    private Texture texturaBtnNivel2;
    private Texture texturaBtnBack;


    //Texto
    private Texto textoNivel1;   // Para poner mensajes en la pantalla
    private Texto textoNivel2;
    private Texto textoNivel;

    // Manager
    private AssetManager manager;

    // Dibujar
    private SpriteBatch batch;

    //Escenas
    private Stage escenaNivel;

    //Efecto
    private Sound efectoBoton;
    Music musicaFondoJuego;
    Music musicaFondo;

    public PantallaNivel(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }


    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }


    private void cargarTexturas() {
        texturaFondoNivel= manager.get("fondoAjustes.jpg");
        texturaBtnNivel1= manager.get("btnNIVELparque.png");
        texturaBtnNivel2= manager.get("btnNIVELdisco.png");
        texturaBtnBack= manager.get("btnBack.png");
        efectoBoton=manager.get("boton.mp3");
        musicaFondoJuego = manager.get("SpaceSong.mp3");
        musicaFondo=manager.get("musicaMenu.mp3");
        musicaFondoJuego.setLooping(true);


    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaNivel = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoNivel);
        escenaNivel.addActor(imgFondo);

        textoNivel1 = new Texto("fuenteTiempo.fnt");
        textoNivel2 = new Texto("fuenteTiempo.fnt");
        textoNivel = new Texto("fuenteTextoInstruccion.fnt");


        //Botón Nivel 1
        TextureRegionDrawable trdBtnNivel1 = new TextureRegionDrawable(new TextureRegion(texturaBtnNivel1));
        ImageButton btnNivel1 = new ImageButton(trdBtnNivel1);
        btnNivel1.setPosition(ANCHO/9, 2*btnNivel1.getHeight()/3);
        escenaNivel.addActor(btnNivel1);
        // Acción botón play
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicaFondo.stop();
                if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)){
                    efectoBoton.play();
                    musicaFondoJuego.play();
                }

                madMom.nivel= mx.itesm.madmom.Dificultades.FACIL;


                int num=MathUtils.random(0,3);
                if (num==1)
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MATACUCARACHAS, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                else if (num==2)
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.INVADERS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                else madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.ATRAPAPLATOS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));

            }
        });

        //Botón Nivel 2
        TextureRegionDrawable trdBtnNivel2 = new TextureRegionDrawable(new TextureRegion(texturaBtnNivel2));
        ImageButton btnNivel2 = new ImageButton(trdBtnNivel2);
        btnNivel2.setPosition(ANCHO - (btnNivel2.getWidth()+ANCHO/9), 2*btnNivel2.getHeight()/3);
        escenaNivel.addActor(btnNivel2);
        // Acción botón play
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicaFondo.stop();
                if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY)){
                    efectoBoton.play();
                    musicaFondoJuego.play();
                }

                madMom.nivel= mx.itesm.madmom.Dificultades.DIFICIL;

                int num=MathUtils.random(0,3);
                if (num==1)
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MATACUCARACHAS, mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                else if (num==2)
                    madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.INVADERS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                else madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.ATRAPAPLATOS,  mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));

            }
        });

        //Botón de Back
        TextureRegionDrawable trdBtnConfig = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnConfig);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaNivel.addActor(btnBack);
        // Acción botón cback
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(mx.itesm.madmom.EstadoMusica.PLAY))
                    efectoBoton.play();

                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));

            }
        });


        Gdx.input.setInputProcessor(escenaNivel);
        Gdx.input.setCatchBackKey(true);
    }



    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaNivel.draw();
        batch.begin();
        textoNivel1.mostrarMensaje(batch, "MODO HISTORIA \n (Junta 1500)",4*ANCHO/16+40, 7*ALTO/32 );
        textoNivel2.mostrarMensaje(batch, "MODO INFINITO",12*ANCHO/16, 7*ALTO/32);
        textoNivel.mostrarMensaje(batch,"MODALIDAD",ANCHO/2,8*ALTO/9);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
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
        manager.unload("fondoAjustes.jpg");
        manager.unload("btnNIVELparque.png");
        manager.unload("btnNIVELdisco.png");
        manager.unload("btnBack.png");

    }
}
