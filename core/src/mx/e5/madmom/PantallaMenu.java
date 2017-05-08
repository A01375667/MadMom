package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jorge Jiménez on 13/02/17.
 */
public class PantallaMenu extends Pantalla
{
    private final MadMom madMom;

    //Texturas
    private Texture texturaFondoMenu;
    private Texture texturaBtnPlayMenu;
    private Texture texturaBtnConfiguracionMenu;

    // Manager
    private AssetManager manager;

    //SpriteBatch
    private SpriteBatch batch;

    //Escenas
    private Stage escenaMenu;

    // Música
    public Music musicaFondo;
    private Preferences preferences;

    // CONSTRUCTOR
    public PantallaMenu(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
        madMom.puntosJugador = 0;
        madMom.vidasJugador =3;
    }

    @Override
    public void show() {
        cargarTexturas();
        switch (madMom.estadoMusica) {
            case PLAY:
                musicaFondo = manager.get("musicaMenu.mp3");
                musicaFondo.setLooping(true);
                musicaFondo.play();
                break;
            case STOP:
                musicaFondo = manager.get("musicaMenu.mp3");
                musicaFondo.stop();
                break;
        }
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoMenu = manager.get("fondoMenu.jpg");
        texturaBtnPlayMenu = manager.get("btnPlay1.png");
        texturaBtnConfiguracionMenu = manager.get("btnAjustes.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMenu = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoMenu);
        escenaMenu.addActor(imgFondo);

        //Botón play
        TextureRegionDrawable trdBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnPlayMenu));
        ImageButton btnPlay = new ImageButton(trdBtnPlay);
        btnPlay.setPosition(ANCHO/2 - 3*btnPlay.getWidth()/8, 8*btnPlay.getHeight()/8);
        escenaMenu.addActor(btnPlay);
        // Acción botón play
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //madMom.setScreen(new PantallaNivel(madMom));
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.NIVEL, Pantallas.TipoPantalla.MENU));

            }
        });

        //Botón de configuración
        TextureRegionDrawable trdBtnConfig = new TextureRegionDrawable(new TextureRegion(texturaBtnConfiguracionMenu));
        ImageButton btnConfig = new ImageButton(trdBtnConfig);
        btnConfig.setPosition(ANCHO - 8*btnConfig.getWidth()/4, 2*btnConfig.getHeight()/4);
        escenaMenu.addActor(btnConfig);
        // Acción botón configuración
        btnConfig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCargando( madMom,Pantallas.CONFIGURACION, Pantallas.TipoPantalla.MENU));
            }
        });

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaMenu.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("fondoMenu.jpg");
        manager.unload("btnPlay1.png");
        manager.unload("btnAjustes.png");
    }
}
