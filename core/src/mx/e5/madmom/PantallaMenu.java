package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    //SpriteBatch
    private SpriteBatch batch;

    //Escenas
    private Stage escenaMenu;

    // CONSTRUCTOR
    public PantallaMenu(MadMom madMom) {
        this.madMom = madMom;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoMenu = new Texture("fondoMenu.jpg");
        texturaBtnPlayMenu = new Texture("btnPlay1.png");
        texturaBtnConfiguracionMenu = new Texture("btnAjustes.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMenu = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoMenu);
        escenaMenu.addActor(imgFondo);

        //Botón play
        TextureRegionDrawable trdBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnPlayMenu));
        ImageButton btnPlay = new ImageButton(trdBtnPlay);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, 3*btnPlay.getHeight()/4);
        escenaMenu.addActor(btnPlay);
        // Acción botón play
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Me hicieron CLICK");
                madMom.setScreen(new MataCucarachas(madMom));
            }
        });

        //Botón de configuración
        TextureRegionDrawable trdBtnConfig = new TextureRegionDrawable(new TextureRegion(texturaBtnConfiguracionMenu));
        ImageButton btnConfig = new ImageButton(trdBtnConfig);
        btnConfig.setPosition(ANCHO-btnConfig.getWidth()*2, 2*btnConfig.getHeight()/4);
        escenaMenu.addActor(btnConfig);
        // Acción botón configuración
        btnConfig.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Me hicieron CLICK");
                madMom.setScreen(new PantallaConfiguracion(madMom));
            }
        });

        Gdx.input.setInputProcessor(escenaMenu);
        Gdx.input.setCatchBackKey(false);
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

    }
}
