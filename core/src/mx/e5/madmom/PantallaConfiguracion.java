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
 * Created by Jorge Jiménez on 13/02/17.
 */
public class PantallaConfiguracion extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoConfig;
    private Texture texturaBtnBackConfig;
    private Texture texturaBtnCreditos;
    private Texture texturaBtnAjustes;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaConfig;

    //Asset Manager
    private AssetManager manager;

    // Constructor
    public PantallaConfiguracion(MadMom madMom){
        this.madMom = madMom;
        this.manager=madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoConfig = manager.get("fondoAjustes.jpg");
        texturaBtnBackConfig = manager.get("btnBack.png");
        texturaBtnCreditos = manager.get("btnCreditos.png");
        texturaBtnAjustes = manager.get("btnAjustesLetra.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaConfig = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoConfig);
        escenaConfig.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBackConfig));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaConfig.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU));
            }
        });

        //Botón créditos
        TextureRegionDrawable trdBtnCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCreditos));
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(ANCHO/2 - btnCreditos.getWidth()/2, ALTO - 7*btnCreditos.getHeight()/4);
        escenaConfig.addActor(btnCreditos);
        // Acción botón créditos
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.CREDITOS));
            }
        });

        //Botón Ajustes
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(texturaBtnAjustes));
        ImageButton btnSonido = new ImageButton(trdBtnSonido);
        btnSonido.setPosition(ANCHO/2 - btnSonido.getWidth()/2, ALTO - 14*btnSonido.getHeight()/4);
        escenaConfig.addActor(btnSonido);
        // Acción botón créditos
        btnSonido.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.AJUSTES));
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
        manager.unload("fondoAjustes.jpg");
        manager.unload("btnBack.png");
        manager.unload("btnCreditos.png");
        manager.unload("btnAjustesLetra.png");

    }
}
