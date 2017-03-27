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
 * Created by Jorge Jiménez on 21/03/17.
 */

public class PantallaProgreso extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoProgreso;
    private Texture texturaBtnBackProgreso;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaProgreso;

    public PantallaProgreso(MadMom madMom) {
        this.madMom = madMom;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoProgreso = new Texture("fondoSplash.jpg");
        texturaBtnBackProgreso = new Texture("btnBack.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaProgreso = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoProgreso);
        escenaProgreso.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBackProgreso));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(ANCHO/2 - btnBack.getWidth()/2, ALTO/2 - btnBack.getHeight()/2);
        escenaProgreso.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaConfiguracion(madMom));
            }
        });

        Gdx.input.setInputProcessor(escenaProgreso);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaProgreso.draw();
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
