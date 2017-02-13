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
 * Created by MrSpecter on 13/02/17.
 */
public class PantallaMenu implements Screen
{
    private final MadMom madMom;

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;

    //Camara, vista
    private OrthographicCamera camera;
    private Viewport vista;

    //Texturas
    private Texture texturaFondo;
    private Texture texturaBtnPlay;
    private Texture texturaBtnConfiguracion;

    //SpriteBatch
    private SpriteBatch batch;//hacer trazos en la pantalla

    //Escenas
    private Stage escenaMenu;



    // CONSTRUCTOR
    public PantallaMenu(MadMom madMom) {
        this.madMom = madMom;
    }



    @Override
    public void show() {
        crearCamara();
        cargarTexturas();
        crearObjetos();
    }

    private void crearCamara() {
        camera = new OrthographicCamera(ANCHO, ALTO);
        camera.position.set(ANCHO/2, ALTO/2, 0);
        camera.update();
        vista = new StretchViewport(ANCHO, ALTO, camera);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondoMenu.jpg");
        texturaBtnPlay = new Texture("btnPlayMenu.png");
        texturaBtnConfiguracion = new Texture("btnConfigMenu.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMenu = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondo);
        escenaMenu.addActor(imgFondo);

        //boton
        TextureRegionDrawable trdBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnPlay));
        ImageButton btnPlay = new ImageButton(trdBtnPlay);
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, 3*ALTO/4-btnPlay.getHeight()/2);
        escenaMenu.addActor(btnPlay);

        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Me dieron click");
                madMom.setScreen( new PantallaConfiguracion(madMom));
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

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
