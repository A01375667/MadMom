package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    public Music musicaFondo;

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
        texturaFondoAjustes = new Texture("fondoAjustes.jpg");
        texturaTextoSonido = new Texture("btnVolumen.png");
        texturaBtnSonidoOFF = new Texture("cuadroVacio.png");
        textureBtnSonidoON = new Texture("cuadroPaloma.png");
        texturaBtnBack = new Texture("btnBack.png");
        texturaMama = new Texture("mamaDerecha.png");
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
                madMom.setScreen(new PantallaConfiguracion(madMom));
            }
        });

        //Botón sonido ON
        TextureRegionDrawable trdBtnSonidoOn = new TextureRegionDrawable(new TextureRegion(textureBtnSonidoON));
        ImageButton btnSonidoOn = new ImageButton(trdBtnSonidoOn);
        btnSonidoOn.setPosition(ANCHO/2 + btnSonidoOn.getWidth()*3, ALTO/2 - btnSonidoOn.getHeight()/2);

        //Botón sonido OFF
        TextureRegionDrawable trdBtnSonidoOff = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoOFF));
        ImageButton btnSonidoOff = new ImageButton(trdBtnSonidoOff);
        btnSonidoOff.setPosition(ANCHO/2 + btnSonidoOff.getWidth()*3, ALTO/2 - btnSonidoOff.getHeight()/2);

        switch (madMom.estadoMusica){
            case PLAY:
                escenaAjustes.addActor(btnSonidoOn);
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
                escenaAjustes.addActor(btnSonidoOff);
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
        }

        Gdx.input.setInputProcessor(escenaAjustes);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaAjustes.draw();

        batch.begin();

        objetoTextoSonido.dibujar(batch);
        mama.dibujar(batch);

        batch.end();
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
}
