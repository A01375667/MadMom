package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
 * Created by Jorge Jiménez on 03/03/17.
 */
public class PantallaCreditos extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoCreditos;
    private Texture texturaBtnBackCreditos;

    // Dibujar
    private SpriteBatch batch;

    //Asset Manager
    private AssetManager manager;

    // Escenas
    private Stage escenaCreditos;

    public PantallaCreditos(MadMom madMom) {
        this.madMom = madMom;
        this.manager=madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoCreditos =manager.get("fondoCreditos.png");
        texturaBtnBackCreditos = manager.get("btnBack.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaCreditos = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoCreditos);
        escenaCreditos.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBackCreditos));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaCreditos.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaCargando(madMom, Pantallas.CONFIGURACION, Pantallas.TipoPantalla.MENU));
            }
        });

        Gdx.input.setInputProcessor(escenaCreditos);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaCreditos.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("fondoCreditos.png");
        manager.unload("btnBack.png");

    }
}
