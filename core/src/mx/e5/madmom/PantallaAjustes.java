package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Jorge Jiménez on 28/03/17.
 */

public class PantallaAjustes extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoAjustes;
    private Texture texturaTextoSonido;
    private Texture texturaBtnSonidoOFF;
    private Texture textureBtnSonidoON;
    private Texture texturaBtnBack;
    private Texture texturaMama;
    private Objeto mama;
    private Objeto objetoTextoSonido;

    // Dibujar
    private SpriteBatch batch;

    // Manager
    private AssetManager manager;

    // Escenas
    private Stage escenaAjustes;

    // Música
    private Sound efectoBoton;

    // Constructor
    public PantallaAjustes(MadMom madMom){
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoAjustes = manager.get("fondoAjustes.jpg");
        texturaTextoSonido = manager.get("btnMusica.png");
        texturaBtnSonidoOFF = manager.get("cuadroVacio.png");
        textureBtnSonidoON = manager.get("cuadroPaloma.png");
        texturaBtnBack = manager.get("btnBack.png");
        texturaMama = manager.get("mamaDerecha.png");
        efectoBoton=manager.get("boton.mp3");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaAjustes = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoAjustes);
        escenaAjustes.addActor(imgFondo);

        mama = new Objeto(texturaMama, ANCHO/5, ALTO/2 - texturaMama.getHeight()/2);

        objetoTextoSonido = new Objeto(texturaTextoSonido, ANCHO/2 + texturaTextoSonido.getWidth()/8, ALTO/2 - texturaTextoSonido.getHeight()/2);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaAjustes.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.CONFIGURACION, Pantallas.TipoPantalla.MENU));
            }
        });

        ///Botón sonido ON
        final Texture textureBtnSonidoON=manager.get("cuadroPaloma.png");
        TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
        final ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
        btnSonidoOn.setPosition(ANCHO/2 + btnSonidoOn.getWidth()*3 - btnSonidoOn.getWidth()/2, ALTO/2 - btnSonidoOn.getHeight()/2);
        //agregar el actor a la pantalla
        escenaAjustes.addActor(btnSonidoOn);
        //no dejar visible en la pantalla
        btnSonidoOn.setVisible(false);


        //Botón sonido OFF
        Texture texturaBtnSonidoOFF= manager.get("cuadroVacio.png");
        final TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
        final ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
        btnSonidoOff.setPosition(ANCHO/2 + btnSonidoOff.getWidth()*3, ALTO/2 - btnSonidoOff.getHeight()/2);
        escenaAjustes.addActor(btnSonidoOff);
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
        escenaAjustes.addActor(btnSonidoOn);
        btnSonidoOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();

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


        Gdx.input.setInputProcessor(escenaAjustes);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaAjustes.draw();

        batch.begin();

        objetoTextoSonido.dibujar(batch);
        mama.dibujar(batch);

        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.CONFIGURACION, Pantallas.TipoPantalla.MENU));
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
        manager.unload("btnMusica.png");
        manager.unload("cuadroVacio.png");
        manager.unload("cuadroPaloma.png");
        manager.unload("btnBack.png");
        manager.unload("mamaDerecha.png");

    }
}
