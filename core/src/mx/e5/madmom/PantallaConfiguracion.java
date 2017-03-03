package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
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
 * Created by Jorge Jiménez on 13/02/17.
 */
public class PantallaConfiguracion extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoConfig;
    private Texture texturaBtnBackConfig;
    private Texture texturaBtnCreditos;
    private Texture texturaBtnSonido;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaConfig;

    // Constructor
    public PantallaConfiguracion(MadMom madMom){
        this.madMom = madMom;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoConfig = new Texture("fondoBaño.jpg");  // CAMBIAR
        texturaBtnBackConfig = new Texture("btnBack.png");
        texturaBtnCreditos = new Texture("btnPlay2.png"); // CAMBIAR
        texturaBtnSonido = new Texture("btnPlay2.png");  // CAMBIAR
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaConfig = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoConfig);
        escenaConfig.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBackConfig));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(3*btnBack.getWidth()/4, 2*btnBack.getHeight()/4);
        escenaConfig.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaMenu(madMom));
            }
        });

        //Botón créditos
        TextureRegionDrawable trdBtnCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCreditos));
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(ANCHO/2 - btnCreditos.getWidth()/2, ALTO/2);
        escenaConfig.addActor(btnCreditos);
        // Acción botón créditos
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCreditos(madMom));
            }
        });

        //Botón sonido
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(texturaBtnSonido));
        ImageButton btnSonido = new ImageButton(trdBtnSonido);
        btnSonido.setPosition(ANCHO/2 - btnSonido.getWidth()/2, ALTO/4);
        escenaConfig.addActor(btnSonido);
        // Acción botón créditos
        btnSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                //******** CONTINUAR O EMPEZAR MÚSICA *************************

            }
        });


        Gdx.input.setInputProcessor(escenaConfig);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaConfig.draw();
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
